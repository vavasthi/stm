/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.caching;

import com.sanjnan.server.config.annotations.ORMCache;
import com.sanjnan.server.sanjnan.config.mapper.DeviceRegistrationTempAuthTokenMapper;
import com.sanjnan.server.sanjnan.dao.AccountDao;
import com.sanjnan.server.sanjnan.dao.DeviceDao;
import com.sanjnan.server.sanjnan.dao.DeviceRegistrationTempAuthTokenDao;
import com.sanjnan.server.sanjnan.entity.DeviceRegistrationTempAuthTokenEntity;
import com.sanjnan.server.sanjnan.pojos.Account;
import com.sanjnan.server.sanjnan.pojos.Device;
import com.sanjnan.server.sanjnan.pojos.DeviceRegistrationTempAuthToken;
import com.sanjnan.server.pojos.constants.H2OConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vinay on 2/22/16.
 */
@Service
@ORMCache(name = H2OConstants.H2O_TEMP_AUTH_TOKEN_CACHE_NAME,
        expiry = H2OConstants.HALF_HOUR,
        prefix = H2OConstants.H2O_TEMP_AUTH_TOKEN_CACHE_PREFIX)
public class DeviceRegistrationTempAuthTokenCacheService extends AbstractCacheService {

  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private DeviceDao deviceDao;
  @Autowired
  private DeviceCacheService deviceCacheService;
  @Autowired
  private DeviceRegistrationTempAuthTokenDao deviceRegistrationTempAuthTokenDao;
  @Autowired
  private DeviceRegistrationTempAuthTokenMapper deviceRegistrationTempAuthTokenMapper;

  private Cache getCache() {

    String cacheName = this.getClass().getAnnotation(ORMCache.class).name();
    Cache cache = cacheManager.getCache(cacheName);
    return cache;
  }

  public DeviceRegistrationTempAuthToken findByTempAuthToken(String tempAuthToken) {
    DeviceRegistrationTempAuthToken deviceRegistrationTempAuthToken
        = getCache().get(tempAuthToken, DeviceRegistrationTempAuthToken.class);
    if (deviceRegistrationTempAuthToken == null) {

      List<DeviceRegistrationTempAuthTokenEntity> deviceRegistrationTempAuthTokenEntities
          = deviceRegistrationTempAuthTokenDao.findByTempAuthToken(tempAuthToken);
      DeviceRegistrationTempAuthTokenEntity deviceRegistrationTempAuthTokenEntity
          = getFirstElementFromList(deviceRegistrationTempAuthTokenEntities);
      deviceRegistrationTempAuthToken
          = deviceRegistrationTempAuthTokenMapper.mapEntityIntoPojo(deviceRegistrationTempAuthTokenEntity);
      storeToCache(deviceRegistrationTempAuthToken);
      return deviceRegistrationTempAuthToken;
    }
    else {
      return deviceRegistrationTempAuthToken;
    }
  }

  public DeviceRegistrationTempAuthToken save(DeviceRegistrationTempAuthToken pojo){

    DeviceRegistrationTempAuthTokenEntity entity = deviceRegistrationTempAuthTokenMapper.mapPojoIntoEntity(pojo);
    entity = deviceRegistrationTempAuthTokenDao.save(entity);
    return deviceRegistrationTempAuthTokenMapper.mapEntityIntoPojo(entity);
  }

  public DeviceRegistrationTempAuthToken create(Account account, Device device){

    String authToken = deviceCacheService.generateAuthToken(device.getDeviceRegistrationId());
    DeviceRegistrationTempAuthTokenEntity entity
        = new DeviceRegistrationTempAuthTokenEntity(accountDao.findOne(account.getId()),
        deviceDao.findOne(device.getId()),
        authToken);
    entity = deviceRegistrationTempAuthTokenDao.save(entity);
    return deviceRegistrationTempAuthTokenMapper.mapEntityIntoPojo(entity);
  }

  public DeviceRegistrationTempAuthToken delete(DeviceRegistrationTempAuthToken pojo){

    evictFromCache(pojo);
    DeviceRegistrationTempAuthTokenEntity entity
        = deviceRegistrationTempAuthTokenDao.findOne(pojo.getId());
    deviceRegistrationTempAuthTokenDao.delete(entity);
    return pojo;
  }
  private void evictFromCache(DeviceRegistrationTempAuthToken pojo) {
    getCache().evict(pojo.getTempAuthToken());
  }

  private void storeToCache(DeviceRegistrationTempAuthToken pojo) {
    getCache().put(pojo.getTempAuthToken(), pojo);
  }


}
