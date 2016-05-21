/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.endpoints.v6;

import com.sanjnan.server.sanjnan.pojos.TestDate;
import com.sanjnan.server.sanjnan.service.SetupService;
import com.sanjnan.server.pojos.constants.H2OConstants;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

/**
 * Created by vinay on 1/4/16.
 */
@RestController
@RequestMapping(H2OConstants.V6_SETUP_ENDPOINT)
public class SetupEndpoint extends BaseEndpoint {
  Logger logger = Logger.getLogger(SetupEndpoint.class);

  @Autowired
  private SetupService setupService;


  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_ADMIN)
  public
  @ResponseBody
  String setup() {
    try {

      return setupService.setup();
    } catch (NoSuchPaddingException
        | InvalidKeySpecException
        | UnsupportedEncodingException
        | IllegalBlockSizeException
        | BadPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | InvalidParameterSpecException ex) {
      logger.log(Level.ERROR, "Authentication Exception", ex);
      throw new AuthenticationServiceException("Authentication Exception", ex);
    }
  }
  @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
//  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_ADMIN)
  public
  @ResponseBody
  String unsetup() {
    try {

      return setupService.unsetup();
    } catch (NoSuchPaddingException
        | InvalidKeySpecException
        | UnsupportedEncodingException
        | IllegalBlockSizeException
        | BadPaddingException
        | NoSuchAlgorithmException
        | InvalidKeyException
        | InvalidParameterSpecException ex) {
      logger.log(Level.ERROR, "Authentication Exception", ex);
      throw new AuthenticationServiceException("Authentication Exception", ex);
    }
  }
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_ADMIN)
  public
  @ResponseBody
  String postSetup(@RequestBody @Valid TestDate date) {


      return date.toString() + "Hello World";
  }
  @RequestMapping(method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
//  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_ADMIN)
  public
  @ResponseBody
  TestDate testSetup() {

      TestDate td = new TestDate();
      td.date = new DateTime();
      return td;

  }

}
