/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.test;

import com.sanjnan.server.sanjnan.pojos.ComputeRegion;
import com.sanjnan.server.sanjnan.pojos.SanjnanTokenResponse;
import com.sanjnan.server.pojos.constants.H2OConstants;
import com.jayway.restassured.response.Response;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.B64Code;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

/**
 * Created by vinay on 2/8/16.
 */
public class ITTestIdentityServer extends TestCaseBase{

  @BeforeClass
  public void setup() {
    initialization();
  }


  @Test
  public void testAuthToken() {

    initialization();
    Response r = given().
            header("Authorization", "Basic " + B64Code.encode(username + ":" + password)).
            header("Content-Type", "application/json").
            header("X-AUTH_USERNAME", username).
            header("X-AUTH-PASSWORD", password).
            header("X-AUTH-TENANT", internalTenant).
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
            when().post(authenticateUrl).thenReturn();
    assertEquals(r.getStatusCode(), HttpStatus.OK_200);
    SanjnanTokenResponse token = r.as(SanjnanTokenResponse.class);
    logger.info(r.prettyPrint());
    logger.info(token.getAuthToken());
    r = given().
        header("Authorization", "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, token.getAuthToken()).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().body(new ComputeRegion("aaaa", "http://api.hubble.in")).
        post(String.format("%s%s/computeregions",baseUrl, internalTenant)).thenReturn();
    assertEquals(r.getStatusCode(), HttpStatus.BAD_REQUEST_400);
    r = given().
        header("Authorization", "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, token.getAuthToken()).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().body(new ComputeRegion("aaaaaaa", "bbbbbbbbb")).
        post(String.format("%s%s/computeregions",baseUrl, internalTenant)).thenReturn();
    assertEquals(r.getStatusCode(), HttpStatus.BAD_REQUEST_400);
    r = given().
        header("Authorization", "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, token.getAuthToken()).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().body(new ComputeRegion("aaaaaa", "zscsdsds")).
        post(String.format("%s%s/computeregions",baseUrl, internalTenant)).thenReturn();
    assertEquals(r.getStatusCode(), HttpStatus.BAD_REQUEST_400);
    r = given().
        header("Authorization", "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, token.getAuthToken()).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().body(new ComputeRegion
        ("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456", "http://api.hubble.in")).
        post(String.format("%s%s/computeregions",baseUrl, internalTenant)).thenReturn();
    assertEquals(r.getStatusCode(), HttpStatus.BAD_REQUEST_400);
    r = given().
        header("Authorization", "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, token.getAuthToken()).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().body(new ComputeRegion("Name1", "http://api.hubble.in")).
        post(String.format("%s%s/computeregions",baseUrl, internalTenant)).thenReturn();
    assertEquals(r.getStatusCode(), HttpStatus.OK_200);
    ComputeRegion cr = r.as(ComputeRegion.class);
    cr.setName("ThisIsMyNewName");
    String id = cr.getId().toString();
    cr.setId(null);
    r = given().
        header("Authorization", "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, token.getAuthToken()).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().body(cr).
        put(String.format("%s%s/computeregions/%s",baseUrl, internalTenant, id)).thenReturn();
    assertEquals(r.getStatusCode(), HttpStatus.OK_200);
    ComputeRegion cr1 = r.as(ComputeRegion.class);
    assertEquals(cr.getName(), cr1.getName());
    r = given().
        header("Authorization", "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, token.getAuthToken()).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().
        delete(String.format("%s%s/computeregions/%s",baseUrl, internalTenant, cr1.getId().toString())).thenReturn();
    logger.info(r.prettyPrint());
    logger.info(token.getAuthToken());
  }
}
