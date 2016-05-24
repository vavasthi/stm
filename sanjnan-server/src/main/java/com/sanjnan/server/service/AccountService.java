/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.service;

import com.sanjnan.server.caching.AccountCacheService;
import com.sanjnan.server.caching.DeviceCacheService;
import com.sanjnan.server.caching.TenantCacheService;
import com.sanjnan.server.pojos.Account;
import com.sanjnan.server.pojos.Device;
import com.sanjnan.server.pojos.Tenant;
import com.sanjnan.server.utils.SanjnanPasswordEncryptionManager;
import com.sanjnan.server.exception.EntityAlreadyExistsException;
import com.sanjnan.server.exception.EntityNotFoundException;
import com.sanjnan.server.exception.PatchingException;
import com.sanjnan.server.exception.TenantMismatchException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/8/16.
 */
@Service
public class AccountService {

  private static final Logger logger = Logger.getLogger(AccountService.class);

  private final TenantCacheService tenantCacheService;
  private final AccountCacheService accountCacheService;
  private final DeviceCacheService deviceCacheService;

  @Autowired
  public AccountService(TenantCacheService tenantCacheService,
                        AccountCacheService accountCacheService,
                        DeviceCacheService deviceCacheService) {
    this.tenantCacheService = tenantCacheService;
    this.accountCacheService = accountCacheService;
    this.deviceCacheService = deviceCacheService;
  }

  @Transactional(readOnly = true)
  public Account getAccount(String tenantDiscriminator, UUID id) throws EntityNotFoundException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account = accountCacheService.findByTenantAndId(tenant, id);

    if (account == null || !account.getTenant().getDiscriminator().equals(tenantDiscriminator)) {
      throw new EntityNotFoundException(id.toString() + " tenantDiscriminator not found.", HttpStatus.NOT_FOUND.value());
    }
    return account;
  }

  @Transactional(readOnly = true)
  public Account getAccount(String tenantDiscriminator, String name) throws EntityNotFoundException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account = accountCacheService.findByTenantAndName(tenant, name);
    return account;
  }

  @Transactional(readOnly = true)
  public List<Account> listAccounts(String tenant) {

    return accountCacheService.findByTenant(tenant);
  }

  @Transactional(readOnly = true)
  public boolean validateCredentials(String tenantDiscriminator, String username, String password)
      throws NoSuchAlgorithmException,
      InvalidKeyException,
      InvalidAlgorithmParameterException,
      NoSuchPaddingException,
      BadPaddingException,
      UnsupportedEncodingException,
      InvalidParameterSpecException,
      InvalidKeySpecException,
      IllegalBlockSizeException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account = accountCacheService.findByTenantAndName(tenant, username);
    return SanjnanPasswordEncryptionManager.INSTANCE.matches(tenant.getDiscriminator(),
        username,
        account.getPassword(),
        password);
  }

  @Transactional
  public Account createAccount(String tenantDiscriminator, Account account)
      throws NoSuchPaddingException,
      InvalidKeySpecException,
      UnsupportedEncodingException,
      IllegalBlockSizeException,
      BadPaddingException,
      NoSuchAlgorithmException,
      InvalidKeyException,
      InvalidParameterSpecException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    if (!account.getTenant().getId().equals(tenant.getId())) {

      // Tenant in the URL doesn't match the tenant in the request.
      throw new TenantMismatchException(String.format("%s is not a valid tenant for account.", tenantDiscriminator));
    }
    if (accountCacheService.accountExists(tenant, account)) {
      throw new EntityAlreadyExistsException(String.format("Account %s with email address %s already exists",
          account.getName(),
          account.getEmail()));
    }
    return accountCacheService.save(tenant, account);
  }

  @Transactional
  public Account updateAccount(String tenantDiscriminator, UUID id, Account account)
      throws IllegalAccessException,
          PatchingException,
      InvocationTargetException,
      NoSuchPaddingException,
      InvalidKeySpecException,
      UnsupportedEncodingException,
      IllegalBlockSizeException,
      BadPaddingException,
      NoSuchAlgorithmException,
      InvalidKeyException,
      InvalidParameterSpecException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    return accountCacheService.update(tenant, id, account);
  }

  @Transactional
  public Account deleteAccount(String tenantDiscriminator, UUID id)
      throws IllegalAccessException,
      PatchingException,
      InvocationTargetException,
      NoSuchPaddingException,
      UnsupportedEncodingException,
      IllegalBlockSizeException,
      BadPaddingException,
      NoSuchAlgorithmException,
      InvalidParameterSpecException,
      InvalidKeyException,
      InvalidKeySpecException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account = accountCacheService.findByTenantAndId(tenant, id);
    return accountCacheService.delete(account);
  }

  @Transactional
  public Account changePassword(String tenantDiscriminator, UUID id, String oldPassword, String newPassword)
      throws IllegalAccessException,
      PatchingException,
      InvocationTargetException,
      NoSuchAlgorithmException,
      InvalidKeyException,
      InvalidAlgorithmParameterException,
      NoSuchPaddingException,
      BadPaddingException,
      UnsupportedEncodingException,
      InvalidParameterSpecException,
      InvalidKeySpecException,
      IllegalBlockSizeException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    return accountCacheService.changePassword(tenant, id, oldPassword, newPassword);
  }
  @Transactional
  public Account adoptDevice(String tenantDiscriminator, UUID id, Device device) {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account =  accountCacheService.findByTenantAndId(tenant, id);
    accountCacheService.evictFromCache(account);
    return accountCacheService.adoptDevice(account, device);
  }
  @Transactional
  public Account unadoptDevice(String tenantDiscriminator, UUID id, Device device) {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account =  accountCacheService.findByTenantAndId(tenant, id);
    accountCacheService.evictFromCache(account);
    deviceCacheService.evictFromCache(device);
    return accountCacheService.unadoptDevice(account, device);
  }
}
