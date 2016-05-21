/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.test;

import com.sanjnan.server.sanjnan.pojos.SanjnanTokenResponse;
import com.sanjnan.server.pojos.constants.H2OConstants;
import com.jayway.restassured.response.Response;
import org.apache.log4j.Level;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.B64Code;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

/**
 * Created by vinay on 2/8/16.
 */
public class ITTestIdentityServerAuthenticateWorkflow extends TestCaseBase {

  private SanjnanTokenResponse authResponse;

  @BeforeClass
  public void setup() {
    initialization();
    authResponse =  given().
            header(H2OConstants.AUTH_AUTHORIZATION_HEADER, "Basic " + B64Code.encode(username + ":" + password)).
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_USERNAME_HEADER, username).
            header(H2OConstants.AUTH_PASSWORD_HEADER, password).
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().post(authenticateUrl).body().as(SanjnanTokenResponse.class);
    logger.log(Level.INFO, String.format("Authenticating %s, received response %s", username, authResponse));
  }


  @Test(threadPoolSize = 100, invocationCount = 400, successPercentage = 98)
  public void testAuthenticate() {

    Response r  =  given().
        header("Content-Type", "application/json").
            header(H2OConstants.AUTH_TENANT_HEADER, internalTenant).
            header(H2OConstants.AUTH_TOKEN_HEADER, authResponse.getAuthToken()).
            header(H2OConstants.AUTH_TOKEN_TYPE_HEADER, "app_token").
            header(H2OConstants.AUTH_APPLICATION_ID_HEADER, "MyRestAssuredClient").
        when().post(authenticateUrl + "/validate").andReturn();
    logger.log(Level.INFO, "Authenticating using token.\n" + r.prettyPrint());
    assertEquals(r.getStatusCode(), HttpStatus.OK_200);
    SanjnanTokenResponse response = r.as(SanjnanTokenResponse.class);
    logger.log(Level.INFO, response.toString());
  }

}
