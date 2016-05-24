/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.test;

import com.sanjnan.server.pojos.ComputeRegion;
import com.sanjnan.server.pojos.SanjnanTokenResponse;
import com.sanjnan.server.pojos.constants.H2OConstants;
import com.jayway.restassured.response.Response;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.B64Code;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by vinay on 2/10/16.
 */
public class TestCaseBase {

  protected static String authenticateUrl = "http://localhost:8080/v6/internal/authenticate";
  protected static String baseUrl = "http://localhost:8080/v6/";
  protected static String username = "Hubble";
  protected static String password = "Hobble";
  protected static String internalTenant = "internal";
  protected Logger logger = Logger.getLogger(TestCaseBase.class.getName());
  protected String authToken;
  protected String deviceToken;
  protected String tempAuthToken;
  protected String accountAuthToken;
  protected ComputeRegion[] computeRegions;
  protected void initialization() {

    {

      String envVal  = System.getenv("TEST_AUTH_URL");
      if (envVal != null) {
        authenticateUrl = envVal;
      }
    }
    {

      String envVal  = System.getenv("TEST_AUTH_USERNAME");
      if (envVal != null) {
        username = envVal;
      }
    }
    {

      String envVal  = System.getenv("TEST_AUTH_PASSWORD");
      if (envVal != null) {
        password = envVal;
      }
    }
    {

      String envVal  = System.getenv("TEST_AUTH_TENANT");
      if (envVal != null) {
        internalTenant = envVal;
      }
    }
    Response r = given().get(baseUrl + "setup").andReturn();
    logger.info(r.prettyPrint());
    SanjnanTokenResponse authResponse =  given().
            header(H2OConstants.AUTH_AUTHORIZATION_HEADER, "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_USERNAME_HEADER, username).
            header(H2OConstants.AUTH_PASSWORD_HEADER, password).
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().post(authenticateUrl).body().as(SanjnanTokenResponse.class);

    logger.log(Level.INFO, String.format("Authenticating %s, received response %s", username, authResponse));
    authToken = authResponse.getAuthToken();
    computeRegions =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, authToken).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().get(baseUrl + "internal/computeregions").body().as(ComputeRegion[].class);
  }
  protected void cleanup() {

/*    Response response =  given().
        header(H2OConstants.AUTH_AUTHORIZATION_HEADER, "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
        header(H2OConstants.AUTH_USERNAME_HEADER, username).
        header(H2OConstants.AUTH_PASSWORD_HEADER, password).
        header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
        header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
        header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().delete(H2OConstants.V6_SETUP_ENDPOINT).andReturn();*/
  }
}
