/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.caching;

import com.sanjnan.server.config.annotations.ORMCache;
import com.sanjnan.server.config.mapper.ObjectPatcher;
import com.sanjnan.server.enums.Role;
import com.sanjnan.server.exception.EntityNotFoundException;
import com.sanjnan.server.exception.H2oDatatypeConfigurationException;
import com.sanjnan.server.mapper.AccountMapper;
import com.sanjnan.server.mapper.ComputeRegionMapper;
import com.sanjnan.server.dao.*;
import com.sanjnan.server.pojos.Account;
import com.sanjnan.server.pojos.Device;
import com.sanjnan.server.pojos.Session;
import com.sanjnan.server.pojos.Tenant;
import com.sanjnan.server.pojos.constants.H2OConstants;
import com.sanjnan.server.utils.SanjnanPasswordEncryptionManager;
import com.sanjnan.server.utils.SanjnanUUIDPair;
import com.sanjnan.server.utils.SanjnanUUIDStringPair;
import com.sanjnan.server.entity.*;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

/**
 * Created by vinay on 2/22/16.
 */
@Service
@ORMCache(name = H2OConstants.H2O_ACCOUNT_CACHE_NAME, expiry = H2OConstants.SIX_HOURS, prefix = H2OConstants.H2O_ACCOUNT_CACHE_PREFIX)
public class AccountCacheService extends AbstractCacheService {

  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private SessionDao sessionDao;
  @Autowired
  private TenantDao tenantDao;
  @Autowired
  private DeviceDao deviceDao;
  @Autowired
  private RoleDao roleDao;
  @Autowired
  private AccountMapper accountMapper;
  @Autowired
  private ComputeRegionMapper computeRegionMapper;

  public Account findByIdOrEmail(UUID id, String email) {

    Account account = getCache().get(id, Account.class);
    if (account == null) {

      account = accountMapper.mapEntityIntoPojo(getFirstElementFromList(accountDao.findByIdOrEmail(id, email)));
      storeToCache(account);
    }
    return account;
  }

  public Account findByName(String name) {

    UUID id = getCache().get(name, UUID.class);
    Account account = null;
    if (id == null) {

      account = accountMapper.mapEntityIntoPojo(getFirstElementFromList(accountDao.findByName(name)));
      storeToCache(account);
    }
    else {

      account = getCache().get(id, Account.class);
    }
    return account;
  }

  public Account findByAuthToken(Tenant tenant, String applicationId, String authToken) {

    UUID id = getCache().get(authToken, UUID.class);
    Account account = null;
    if (id == null) {

      SessionEntity sessionEntity = getFirstElementFromList(sessionDao.findByAuthToken(authToken));
      TenantEntity tenantEntity = getFirstElementFromList(tenantDao.findByDiscriminator(tenant.getDiscriminator()));
      id
          = sessionEntity.getSessionOwnerEntity().getId();
      account = accountMapper.mapEntityIntoPojo(accountDao.findOne(id));
      storeToCache(account);
    }
    else {
      account = getCache().get(id, Account.class);
    }
    return account;
  }

  Account findByNameOrEmail(String name, String email) {

    Account account = null;
    UUID id = getCache().get(name, UUID.class);
    if (id == null) {

      account = accountMapper.mapEntityIntoPojo(getFirstElementFromList(accountDao.findByNameOrEmail(name, email)));
      storeToCache(account);
    }
    else {
      account = getCache().get(id, Account.class);
    }
    return account;
  }

  public Account deleteAccount(Tenant tenant, UUID id) {

    Account account = findByTenantAndId(tenant, id);
    TenantEntity tenantEntity = getTenantEntity(tenant.getDiscriminator());
    AccountEntity accountEntity = getFirstElementFromList(accountDao.findByTenantAndId(tenantEntity, id));
    accountDao.delete(accountEntity);
    evictFromCache(account);
    return account;
  }

  private TenantEntity getTenantEntity(String tenant) {

    List<TenantEntity> tenantEntityList = tenantDao.findByDiscriminator(tenant);
    if (tenantEntityList == null || tenantEntityList.size() == 0) {
      throw new EntityNotFoundException(tenant + " not found.");
    }
    TenantEntity te = tenantEntityList.get(0);
    return te;
  }

  public void deleteAllAccounts(Tenant tenant) {

    TenantEntity tenantEntity = getTenantEntity(tenant.getDiscriminator());
    List<AccountEntity> accountEntities = accountDao.findByTenant(tenantEntity);
    for (AccountEntity accountEntity : accountEntities) {

      Account account = accountMapper.mapEntityIntoPojo(accountEntity);
      evictFromCache(account);
      accountDao.delete(accountEntity);
    }
  }

  /**
   * This method would delete all the tokens for a particular user. It deletes all the entries from session map,
   * evicts all the cache entries for that user and then recache it.
   *
   * @param tenant
   * @param id
   * @return
     */
  public Account deleteToken(Tenant tenant, UUID id) {

    Account account = findByTenantAndId(tenant, id);
    TenantEntity tenantEntity = getTenantEntity(tenant.getDiscriminator());
    AccountEntity accountEntity = getFirstElementFromList(accountDao.findByTenantAndId(tenantEntity, id));
    evictFromCache(account);
    accountEntity.getSessionMap().clear();
    account = accountMapper.mapEntityIntoPojo(accountEntity);
    storeToCache(account);
    return account;
  }

  /**
   * This method would remove a single token for a particular user from the cache.
   * @param tenant
   * @param token
   * @return
   */

  public Account deleteToken(Tenant tenant, String token) {

    UUID id = getCache().get(token, UUID.class);
    if (id != null) {

      evictFromCache(getCache().get(id, Account.class));
    }
    else {

      SessionEntity sessionEntity = getFirstElementFromList(sessionDao.findByAuthToken(token));
      id = sessionEntity.getSessionOwnerEntity().getId();
    }
    TenantEntity tenantEntity = getTenantEntity(tenant.getDiscriminator());
    AccountEntity accountEntity = accountDao.findOne(id);
    for (Map.Entry<String, SessionEntity> entry : accountEntity.getSessionMap().entrySet()) {
      if (entry.getValue().getAuthToken().equals(token) || entry.getKey().equals(token)) {
        // Found a match. Delete it.
        accountEntity.getSessionMap().remove(entry.getKey());
        break;
      }
    }
    Account account = accountMapper.mapEntityIntoPojo(accountEntity);
    storeToCache(account);
    return account;
  }

  /**
   * This method is called to check if a particular user already exists in the system. This will check for
   * duplication of username and email address.
   * @param tenant
   * @param account
   * @return
   */
  public boolean accountExists(Tenant tenant, Account account) {

    TenantEntity tenantEntity = getTenantEntity(tenant.getDiscriminator());
    List<AccountEntity> accountEntities
        = accountDao.findByTenantAndName(tenantEntity, account.getName());
    if(accountEntities != null && accountEntities.size() > 0) {
      return true;
    }
    accountEntities
        = accountDao.findByTenantAndEmail(tenantEntity, account.getEmail());
    return accountEntities != null && accountEntities.size() > 0;
  }

  /**
   * This method is called to query all the users for a particular tenantDiscriminator. This query is directly performed on the
   * database.
   * @param tenantDiscriminator
   * @return
   */
  public List<Account> findByTenant(String tenantDiscriminator) {

    TenantEntity te = getTenantEntity(tenantDiscriminator);
    List<Account> accountList = new ArrayList<>();

    accountDao.findByTenant(te).forEach(e -> accountList.add(accountMapper.mapEntityIntoPojo(e)));
    return accountList;
  }

  /**
   * This method is called when a new authentication takes places. If we already have an account entry in cache,
   * we need to evict that, update the record with a new auth token and then return the new entity after caching that.
   *
   * @param tenant
   * @param name
   * @param remoteAddr
   * @return
   */
  public Account generateAuthToken(Tenant tenant, String name, String remoteAddr, String applicationId) {

    TenantEntity tenantEntity = getTenantEntity(tenant.getDiscriminator());
    UUID id = getCache().get(new SanjnanUUIDStringPair(tenantEntity.getId(), name), UUID.class);
    if (id != null) {
      evictFromCache(getCache().get(id, Account.class));
    }
    id = getCache().get(new SanjnanUUIDStringPair(tenantEntity.getId(), applicationId), UUID.class);
    if (id != null) {
      evictFromCache(getCache().get(id, Account.class));
    }
    String authToken = generateAuthToken(tenantEntity.getDiscriminator(), name);
    AccountEntity accountEntity = getFirstElementFromList(accountDao.findByTenantAndName(tenantEntity, name));
    if (accountEntity.getSessionMap() == null) {
      accountEntity.setSessionMap(new HashMap<>());
    }
    // Irrespective of whether the auth token is present or not, we overwrite it.
    // No session exists, we need to create a new one and return.
    SessionEntity sessionEntity = null;
    try {
      sessionEntity
          = new SessionEntity(authToken, remoteAddr, applicationId, accountEntity, Session.SESSION_TYPE.APPLICATION_SESSION.getIValue());
    } catch (DatatypeConfigurationException e) {
      e.printStackTrace();
      throw new H2oDatatypeConfigurationException(e);
    }
    sessionDao.save(sessionEntity);
    accountEntity.getSessionMap().put(applicationId, sessionEntity);
    if (!accountEntity.getRemoteAddresses().contains(remoteAddr)) {
      accountEntity.getRemoteAddresses().add(remoteAddr);
    }
    Account account = accountMapper.mapEntityIntoPojo(accountEntity);
    storeToCache(account);
    return account;
  }

  private String generateAuthToken(String tenant, String username) {
    UUID uuid1 = UUID.randomUUID();
    UUID uuid2 = UUID.randomUUID();

    String token = Long.toHexString(uuid1.getLeastSignificantBits()) +
        Long.toHexString(uuid1.getMostSignificantBits()) +
        Hex.encodeHexString(tenant.getBytes()) +
        Long.toHexString(uuid2.getLeastSignificantBits()) +
        Long.toHexString(uuid2.getMostSignificantBits()) +
        Hex.encodeHexString(username.getBytes());
    return token;
  }

  public Account save(Tenant tenant, Account account)
      throws NoSuchPaddingException,
      InvalidKeySpecException,
      UnsupportedEncodingException,
      IllegalBlockSizeException,
      BadPaddingException,
      NoSuchAlgorithmException,
      InvalidKeyException,
      InvalidParameterSpecException {

    String encryptedPassword
        = SanjnanPasswordEncryptionManager
        .INSTANCE.encrypt(tenant.getDiscriminator(), account.getName(), account.getPassword());
    account.setPassword(encryptedPassword);
    TenantEntity tenantEntity = getTenantEntity(tenant.getDiscriminator());
    account.setComputeRegion(ComputeRegionMapper.mapEntityIntoPojo(tenantEntity.getNextComputeRegionEntity()));
    AccountEntity accountEntity = accountMapper.mapPojoIntoEntity(account);
    accountDao.save(accountEntity);
    return accountMapper.mapEntityIntoPojo(accountEntity);
  }

  public Account update(Tenant tenant, UUID id, Account account)
      throws NoSuchPaddingException,
      InvalidKeySpecException,
      UnsupportedEncodingException,
      IllegalBlockSizeException,
      BadPaddingException,
      NoSuchAlgorithmException,
      InvalidKeyException,
      InvalidParameterSpecException,
      InvocationTargetException,
      IllegalAccessException {

    Account cachedAccount = findByTenantAndName(tenant, account.getName());
    evictFromCache(cachedAccount);
    if (account.getPassword() != null) {
      // If the incoming json packet also contains password and then it is not encrypted and we need to encrypt it here.
      String encryptedPassword
          = SanjnanPasswordEncryptionManager
          .INSTANCE.encrypt(tenant.getDiscriminator(), account.getName(), account.getPassword());
      account.setPassword(encryptedPassword);
    }
    AccountEntity storedEntity = accountDao.findOne(id);
    AccountEntity newEntity = accountMapper.mapPojoIntoEntity(account);
    ObjectPatcher.diffAndPatch(storedEntity, newEntity);
    return account;
  }

  public Account findByTenantAndName(Tenant tenant, String name) {

    UUID id = getCache().get(new SanjnanUUIDStringPair(tenant.getId(), name), UUID.class);
    Account account = null;
    if (id == null) {

      TenantEntity tenantEntity = getTenantEntity(tenant.getDiscriminator());
      AccountEntity accountEntity = getFirstElementFromList(accountDao.findByTenantAndName(tenantEntity, name));
      account = accountMapper.mapEntityIntoPojo(accountEntity);
      storeToCache(account);
    }
    else {
      account = getCache().get(id, Account.class);
    }
    return account;
  }

  public Account delete(Account account)
      throws NoSuchPaddingException,
      InvalidKeySpecException,
      UnsupportedEncodingException,
      IllegalBlockSizeException,
      BadPaddingException,
      NoSuchAlgorithmException,
      InvalidKeyException,
      InvalidParameterSpecException,
      InvocationTargetException,
      IllegalAccessException {

    evictFromCache(account);
    AccountEntity storedEntity = accountDao.findOne(account.getId());
    storedEntity.setComputeRegionEntity(null);
    accountDao.delete(storedEntity);
    return account;
  }

  public Account changePassword(Tenant tenant, UUID id, String oldPassword, String newPassword)
      throws NoSuchPaddingException,
      InvalidKeySpecException,
      UnsupportedEncodingException,
      IllegalBlockSizeException,
      BadPaddingException,
      NoSuchAlgorithmException,
      InvalidKeyException,
      InvalidParameterSpecException,
      InvocationTargetException,
      IllegalAccessException, InvalidAlgorithmParameterException {

    Account cachedAccount = findByTenantAndId(tenant, id);
    evictFromCache(cachedAccount);
    if (SanjnanPasswordEncryptionManager
        .INSTANCE.matches(tenant.getDiscriminator(),
            cachedAccount.getName(),
            cachedAccount.getPassword(),
            oldPassword)) {

      String encryptedPassword
          = SanjnanPasswordEncryptionManager
          .INSTANCE.encrypt(tenant.getDiscriminator(), cachedAccount.getName(), newPassword);
      cachedAccount.setPassword(encryptedPassword);
      AccountEntity storedEntity = accountDao.findOne(id);
      storedEntity.setPassword(encryptedPassword);
      cachedAccount.setPassword(encryptedPassword);
    }
    return cachedAccount;
  }

  public Account findByTenantAndId(Tenant tenant, UUID id) {

    Account account = getCache().get(id, Account.class);
    if (account == null) {

      TenantEntity tenantEntity = getTenantEntity(tenant.getDiscriminator());
      AccountEntity accountEntity = getFirstElementFromList(accountDao.findByTenantAndId(tenantEntity, id));
      account = accountMapper.mapEntityIntoPojo(accountEntity);
      storeToCache(account);
    }
    return account;
  }

  private Cache getCache() {

    String cacheName = this.getClass().getAnnotation(ORMCache.class).name();
    Cache cache = cacheManager.getCache(cacheName);
    return cache;
  }

  private void storeToCache(Account account) {
    getCache().put(account.getId(), account);
    getCache().put(account.getName(), account.getId());
    if (account.getSessionMap() != null && account.getSessionMap().size() > 0) {

      for (Map.Entry<String, Session> entry : account.getSessionMap().entrySet()) {

        getCache().put(entry.getValue().getAuthToken(), account.getId());
      }
    }
    getCache().put(new SanjnanUUIDPair(account.getTenant().getId(), account.getId()), account.getId());
    getCache().put(new SanjnanUUIDStringPair(account.getTenant().getId(), account.getName()), account.getId());
  }
  public void evictFromCache(Account account) {
    getCache().evict(account.getId());
    getCache().evict(account.getName());
    if (account.getSessionMap() != null && account.getSessionMap() != null) {

      for (Map.Entry<String, Session> entry : account.getSessionMap().entrySet()) {

        getCache().evict(entry.getValue().getAuthToken());
      }
    }
    getCache().evict(new SanjnanUUIDPair(account.getTenant().getId(), account.getId()));
    getCache().evict(new SanjnanUUIDStringPair(account.getTenant().getId(), account.getName()));
  }
  public Account adoptDevice(Account account, Device device) {

    AccountEntity accountEntity = accountDao.findOne(account.getId());

    DeviceEntity deviceEntity
            = new DeviceEntity(device.getDeviceRegistrationId(),
            accountEntity);
    Set<RoleEntity> roleEntities = new HashSet<>();
    roleEntities.add(new RoleEntity(Role.DEVICE.getValue()));
    roleDao.save(roleEntities);
    deviceEntity.setRoles(roleEntities);
    deviceEntity.setAccountEntity(accountEntity);
    if (accountEntity.getDevices() == null) {
      accountEntity.setDevices(new HashSet<>());
    }
    deviceEntity = deviceDao.save(deviceEntity);
    accountEntity.getDevices().add(deviceEntity);
    account = accountMapper.mapEntityIntoPojo(accountEntity);
    return account;
  }

  /**
   * Unadopt device would remove the associaton of a device with an account. The device still exists in the database, but as an unattached
   * device.
   *
   * @param accountId
   * @param device
   * @return
   */
  public Account unadoptDevice(UUID accountId, Device device) {

    AccountEntity accountEntity = accountDao.findOne(accountId);
    DeviceEntity deviceEntity = deviceDao.findOne(device.getId());
    deviceEntity.setAccountEntity(null);
    if (accountEntity.getDevices() != null) {
      Iterator<DeviceEntity> iterator = accountEntity.getDevices().iterator();
      while (iterator.hasNext()) {
        DeviceEntity de = iterator.next();
        if (de.getId().equals(deviceEntity.getId())) {
          iterator.remove();
        }
      }
    }
    Account account = accountMapper.mapEntityIntoPojo(accountEntity);
    return account;
  }
  public Account unadoptDevice(Account account, Device device) {

    return unadoptDevice(account.getId(), device);
  }
}
