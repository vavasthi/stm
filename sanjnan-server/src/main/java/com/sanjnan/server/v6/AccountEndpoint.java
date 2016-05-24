/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.v6;

import com.sanjnan.server.exception.PatchingException;
import com.sanjnan.server.pojos.Account;
import com.sanjnan.server.pojos.Device;
import com.sanjnan.server.service.AccountService;
import com.sanjnan.server.pojos.constants.H2OConstants;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/4/16.
 */
@RestController
@RequestMapping(H2OConstants.V6_ACCOUNTS_ENDPOINT)
public class AccountEndpoint extends BaseEndpoint {

  private final AccountService accountService;
  Logger logger = Logger.getLogger(AccountEndpoint.class);

  @Autowired
  public AccountEndpoint(AccountService accountService) {
    this.accountService = accountService;
  }

  @Transactional(readOnly = true)
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_ADMIN_AND_TENANT_ADMIN)
  public
  @ResponseBody
  List<Account> listAccounts(@PathVariable("tenant") String tenant) {
    return accountService.listAccounts(tenant);
  }

  @Transactional(readOnly = true)
  @RequestMapping(value = "/{id_or_name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_USER_ADMIN_AND_TENANT_ADMIN)
  public Account getAccount(@PathVariable("tenant") String tenant, @PathVariable("id_or_name") String id_or_name) throws IOException {

    try {

      UUID id = UUID.fromString(id_or_name);
      logger.log(Level.INFO, "Retrieving account info for UUID = " + id_or_name);
      return accountService.getAccount(tenant, id);
    }
    catch(IllegalArgumentException iae) {
      logger.log(Level.INFO, "Retrieving account info for username = " + id_or_name);
      return accountService.getAccount(tenant, id_or_name);
    }
  }

  @Transactional
  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_ADMIN_AND_TENANT_ADMIN)
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Account createAccount(@PathVariable("tenant") String tenant, @RequestBody @Valid Account account) {
    try {
      return accountService.createAccount(tenant, account);
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

  @Transactional
  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_USER_ADMIN_AND_TENANT_ADMIN)
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public Account updateAccount(@PathVariable("tenant") String tenant,
                               @PathVariable("id") UUID id,
                               @RequestBody @Valid Account account) throws IllegalAccessException, PatchingException, InvocationTargetException {
    try {
    return accountService.updateAccount(tenant, id, account);
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

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_ADMIN_AND_TENANT_ADMIN)
  public Account deleteAccount(@PathVariable("tenant") String tenant,
                               @PathVariable("id") UUID id)
      throws InvocationTargetException, IllegalAccessException {
    try {

      return accountService.deleteAccount(tenant, id);
    }  catch (NoSuchPaddingException
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

  @Transactional
  @RequestMapping(value = "/{id}/adopt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_ADMIN_AND_TENANT_ADMIN)
  public Account adoptDevice(@PathVariable("tenant") String tenant,
                             @PathVariable("id") UUID id,
                             @RequestBody @Valid Device device) {
      return accountService.adoptDevice(tenant, id, device);
  }

  @Transactional
  @RequestMapping(value = "/{id}/adopt", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize(H2OConstants.ANNOTATION_ROLE_ADMIN_AND_TENANT_ADMIN)
  public Account unadoptDevice(@PathVariable("tenant") String tenant,
                             @PathVariable("id") UUID id,
                             @RequestBody @Valid Device device) {
    return accountService.unadoptDevice(tenant, id, device);
  }
}
