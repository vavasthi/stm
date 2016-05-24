/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.test;

import com.sanjnan.server.pojos.*;
import com.sanjnan.server.pojos.SanjnanRole;
import com.sanjnan.server.pojos.constants.H2OConstants;
import com.jayway.restassured.response.Response;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.B64Code;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.*;

import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Created by vinay on 2/8/16.
 */
public class ITTestIdentityServerRESTWorkflow extends TestCaseBase {

  Logger logger = Logger.getLogger(ITTestIdentityServerRESTWorkflow.class);

  private Tenant tenant1;
  private Tenant tenant2;
  private Account account;
  private Device device;
  private String defaultAppName = "MyRestAssuredClient";
  @BeforeClass
  public void setup() {
    initialization();
  }

  @Test(priority = 1)
  public void testCreateAndUpdateTenant() {

    logger.info("Testing tenant creation..");

    List<ComputeRegion> computeRegionList = Arrays.asList(computeRegions);
    List<UUID> uuidList = new ArrayList<>();
    computeRegionList.forEach(e -> uuidList.add(e.getId()));

    String newTenant = "tenant1";
    tenant1 = getTenant(newTenant);
    if (tenant1 != null) {
      deleteTenant(tenant1);
    }
    tenant1 = new Tenant(newTenant, "tenant@tenant1.com", newTenant, uuidList);

    Tenant responseTenant =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().body(tenant1).post(baseUrl + "internal/tenants").body().as(Tenant.class);

    assertTrue(tenant1.getName().equals(responseTenant.getName()) && tenant1.getEmail().equals(responseTenant.getEmail()));
    tenant1 = responseTenant;

    tenant1.setEmail("newEmail@newDomain.com");
    responseTenant =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
        when().body(tenant1).put(baseUrl + "internal/tenants/"+ tenant1.getId().toString()).body().as(Tenant.class);

    assertTrue(responseTenant.getEmail().equals(tenant1.getEmail()) && responseTenant.getId().equals(tenant1.getId()));
    tenant1 = responseTenant;
  }

  @Test(priority = 2)
  public void queryNonExistentTenent() {

    logger.info("Testing non existent tenant query.");

    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().get(baseUrl + "internal/tenants/" + UUID.randomUUID().toString()).thenReturn();
    logger.info(response.prettyPrint());
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND_404);

  }
  @Test(priority = 3)
  public void testAccountLifecycle() {

    Set<SanjnanRole> h2ORoleList = new HashSet<>();
    h2ORoleList.add(new SanjnanRole("admin"));
    String username = "user1";
    deleteAccount(tenant1, username);

    account = createAccount(tenant1, username, "user1@tenant1.com", "user123", h2ORoleList);
    assertTrue(verifyAccount(account, username));
    Account[] accounts = getListOfAccounts(tenant1);
    logger.info(String.format("Found %d accounts", accounts.length));
    String newEmail = "newEmail@tenant1.com";
    account.setEmail(newEmail);
    account = updateAccount(account);
    account.setPassword("user123");
    accountAuthToken = createAuthToken(account).getAuthToken();
  }
  @Test(priority = 4)
  public void testCreateDeviceLifecycle() {


    Set<SanjnanRole> h2ORoleList = new HashSet<>();
    h2ORoleList.add(new SanjnanRole("admin"));
    device = new Device("ABCDEFDEADBEEF", h2ORoleList);
    account = adoptDevice(account, device);
    device = account.getDevices().iterator().next();
  }

  @Test(priority = 5)
  public void tokenLifeCycleTests() {

    logger.info("Create a new auth token..");

    SanjnanTokenResponse tokenResponse = createTempAuthToken(account, device);
    tempAuthToken = tokenResponse.getAuthToken();
    assertTrue(validateToken(tenant1.getDiscriminator(), tempAuthToken, "temp_token"));
    tokenResponse = createDeviceAuthToken(account, device, tempAuthToken);
    deviceToken = tokenResponse.getAuthToken();
    assertTrue(validateToken(tenant1.getDiscriminator(), accountAuthToken, "app_token"));
    assertTrue(validateToken(tenant1.getDiscriminator(), deviceToken, "device_token"));
    accountAuthToken = refreshToken(tenant1.getDiscriminator(), accountAuthToken, "app_token");

    deviceToken = refreshToken(tenant1.getDiscriminator(), deviceToken, "device_token");
    assertTrue(validateToken(tenant1.getDiscriminator(), authToken, "app_token"));
    assertTrue(validateToken(tenant1.getDiscriminator(), deviceToken, "device_token"));
  }

  @Test(priority = 6)
  public void unadoptDevice() {

    account = unadoptDevice(account, device);
  }
  @AfterClass
  public void cleanup() {

    deleteAccounts();
//    super.cleanup();
  }

  private Tenant getTenant(String discriminator) {

    Tenant[] tenantList =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().get(baseUrl + "internal/tenants").body().as(Tenant[].class);

    for (Tenant t : tenantList) {
      if (t.getDiscriminator().equals(discriminator)) {
        logger.info(String.format("%s tenant already exists. Deleting.", t.getDiscriminator()));
        return t;
      }
    }
    return null;
  }
  private void deleteAccounts() {

    Account[] accounts = getListOfAccounts(tenant1);
    for (Account account : accounts) {
      deleteAccount(account);
    }
  }
  private Tenant deleteTenant(Tenant tenant) {

    Response response =  given().
            header(H2OConstants.AUTH_AUTHORIZATION_HEADER, "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_USERNAME_HEADER, username).
            header(H2OConstants.AUTH_PASSWORD_HEADER, password).
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().post(authenticateUrl).thenReturn();
    assertEquals(response.getStatusCode(), HttpStatus.OK_200);
    SanjnanTokenResponse authResponse = response.body().as(SanjnanTokenResponse.class);

    return given().header("Content-Type", "application/json")
            .header(H2OConstants.AUTH_TENANT_HEADER, internalTenant)
            .header(H2OConstants.AUTH_TOKEN_HEADER, authResponse.getAuthToken())
            .header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token")
            .header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName)
        .delete(baseUrl + "internal/tenants/" + tenant.getId().toString()).body().as(Tenant.class);
  }

  private SanjnanTokenResponse createAuthToken(Account account) {

    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_USERNAME_HEADER, account.getName()).
            header(H2OConstants.AUTH_PASSWORD_HEADER, account.getPassword()).
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().post(baseUrl + tenant1.getDiscriminator() + "/authenticate").thenReturn();
    SanjnanTokenResponse tempAuthTokenResponse = response.as(SanjnanTokenResponse.class);
    return tempAuthTokenResponse;
  }
  private SanjnanTokenResponse createTempAuthToken(Account account, Device device) {

    DeviceAuthentication deviceAuthentication = new DeviceAuthentication(account.getId(), device.getDeviceRegistrationId());
    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().body(deviceAuthentication).post(baseUrl + tenant1.getDiscriminator() + "/authenticate/tempAuth").thenReturn();
    SanjnanTokenResponse tempAuthTokenResponse = response.as(SanjnanTokenResponse.class);
    return tempAuthTokenResponse;
  }
  private SanjnanTokenResponse createDeviceAuthToken(Account account, Device device, String authToken) {

    DeviceAuthentication deviceAuthentication = new DeviceAuthentication(account.getId(), device.getDeviceRegistrationId());
    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, account.getTenant().getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, tempAuthToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "temp_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().body(deviceAuthentication).post(baseUrl + account.getTenant().getDiscriminator() + "/authenticate/device").thenReturn();
    SanjnanTokenResponse deviceAuthTokenResponse = response.as(SanjnanTokenResponse.class);
    return deviceAuthTokenResponse;
  }
  private Account createAccount(Account account) {

    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().body(account).post(baseUrl + tenant1.getDiscriminator() + "/accounts").thenReturn();
    assertEquals(response.getStatusCode(), HttpStatus.OK_200);
    account = response.as(Account.class);
    return account;
  }
  private Account createAccount(Tenant tenant, String username, String email, String password, Set<SanjnanRole> h2ORoleList) {

    Account account = new Account(tenant, username, email, password, h2ORoleList);
    return createAccount(account);
  }
  private Account getAccount(Account account) {

    return getAccount(account.getTenant().getDiscriminator(), account.getName());
  }
  private Account getAccount(String tenant, UUID id) {
    return getAccount(tenant, id.toString());
  }
  private Account getAccount(String tenant, String username) {

    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().get(baseUrl + tenant + "/accounts/" + username).thenReturn();

    if (response.statusCode() == HttpStatus.OK_200) {
      return response.as(Account.class);
    }
    else {
      return null;
    }
  }

  private boolean verifyAccount(Account account, String username) {
    Account storedAccount = getAccount(account);
    assertEquals(account, storedAccount);
    return true;
  }
  private Account[] getListOfAccounts(Tenant tenant) {

    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().get(baseUrl + tenant.getDiscriminator() + "/accounts").thenReturn();
    assertEquals(response.getStatusCode(), HttpStatus.OK_200);
    Account[] accountList = response.as(Account[].class);
    return accountList;
  }
  private Device[] getListOfDevices(Tenant tenant) {

    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().get(baseUrl + tenant.getDiscriminator() + "/devices").thenReturn();
    assertEquals(response.getStatusCode(), HttpStatus.OK_200);
    Device[] devices = response.as(Device[].class);
    return devices;
  }
  private void deleteDevice(Device device) {

    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().delete(baseUrl + tenant1.getDiscriminator() + "/devices/" + device.getId()).thenReturn();
    assertEquals(response.getStatusCode(), HttpStatus.OK_200);
  }
  private Account updateAccount(Account account) {

    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().body(account).put(baseUrl + account.getTenant().getDiscriminator() + "/accounts/" + account.getId().toString()).
        thenReturn();
    assertEquals(response.getStatusCode(), HttpStatus.OK_200);
    response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().get(baseUrl + tenant1.getDiscriminator() + "/accounts/" + account.getName()).thenReturn();
    assertEquals(response.getStatusCode(), HttpStatus.OK_200);
    Account newAccount = response.as(Account.class);
    assertTrue(account.equals(newAccount));
    return newAccount;
  }
  private void deleteAccount(Tenant tenant, String username) {

    // Delete account if it exists. Ignore the error.
    Account account = getAccount(tenant.getDiscriminator(), username);
    if (account != null) {

      deleteAccount(account);
    }
  }
  private void deleteAccount(Account account) {

    // Delete account if it exists. Ignore the error.
    given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().delete(baseUrl + account.getTenant().getDiscriminator() + "/accounts/" + account.getId().toString()).thenReturn();
  }
  private String refreshToken(String tenantDiscriminator, String token, String tokenType) {

    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenantDiscriminator).
            header(H2OConstants.AUTH_TOKEN_HEADER, token).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, tokenType).
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().post(String.format("%s%s/authenticate/refresh", baseUrl, tenantDiscriminator)).thenReturn();
    assertEquals(response.getStatusCode(), HttpStatus.OK_200);
    SanjnanTokenResponse authResponse = response.body().as(SanjnanTokenResponse.class);
    return authResponse.getAuthToken();
  }
  private boolean validateToken(String tenantDiscriminator, String token, String tokenType) {

    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenantDiscriminator).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, tokenType).
            header(H2OConstants.AUTH_TOKEN_HEADER, token).
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().post(String.format("%s%s/authenticate/validate", baseUrl, tenantDiscriminator)).thenReturn();
    assertEquals(response.getStatusCode(), HttpStatus.OK_200);
    SanjnanTokenResponse authResponse = response.body().as(SanjnanTokenResponse.class);
    return true;
  }
  private Account adoptDevice(Account account, Device device) {

    String adoptUrl
        = baseUrl
        + tenant1.getDiscriminator()
        + "/accounts/" + account.getId().toString() + "/adopt";
    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, accountAuthToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().body(device).post(adoptUrl).
        thenReturn();
    logger.info(response.prettyPrint());
    return response.as(Account.class);
  }
  private Account unadoptDevice(Account account, Device device) {

    String adoptUrl
        = baseUrl
        + tenant1.getDiscriminator()
        + "/accounts/" + account.getId().toString() + "/adopt";
    Response response =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, tenant1.getDiscriminator()).
            header(H2OConstants.AUTH_TOKEN_HEADER, accountAuthToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, defaultAppName).
        when().body(device).delete(adoptUrl).
        thenReturn();
    logger.info(response.prettyPrint());
    return response.as(Account.class);
  }
}
