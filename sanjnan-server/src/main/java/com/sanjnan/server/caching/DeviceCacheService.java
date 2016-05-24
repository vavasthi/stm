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
import com.sanjnan.server.mapper.DeviceMapper;
import com.sanjnan.server.dao.DeviceDao;
import com.sanjnan.server.dao.SessionDao;
import com.sanjnan.server.entity.DeviceEntity;
import com.sanjnan.server.entity.SessionEntity;
import com.sanjnan.server.pojos.Device;
import com.sanjnan.server.pojos.constants.H2OConstants;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by vinay on 2/22/16.
 */
@Service
@ORMCache(name = H2OConstants.H2O_DEVICE_CACHE_NAME, expiry = H2OConstants.SIX_DAYS, prefix = H2OConstants.H2O_DEVICE_CACHE_PREFIX)
public class DeviceCacheService extends AbstractCacheService {

  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private DeviceDao deviceDao;
  @Autowired
  private SessionDao sessionDao;
  @Autowired
  private DeviceMapper deviceMapper;

  private Cache getCache() {

    String cacheName = this.getClass().getAnnotation(ORMCache.class).name();
    Cache cache = cacheManager.getCache(cacheName);
    return cache;
  }

  public Set<Device> list() {
    Set<Device> devices = deviceMapper.mapEntitiesIntoDTOs(deviceDao.findAll());
    return devices;
  }

  public Device findOne(UUID id) {
    return getCache().get(id, Device.class);
  }

  /**
   * This method will return a @Ref Device pojo that maps to a given deviceRegistrationId.
   * @param deviceRegistrationId
   * @return
   */
  public Device findByDeviceRegistrationId(String deviceRegistrationId) {

    UUID id = getCache().get(deviceRegistrationId, UUID.class);
    if (id != null) {

      Device device = getCache().get(id, Device.class);
      if (device != null) {
        return device;
      }
    }
    DeviceEntity deviceEntity = getFirstElementFromList(deviceDao.findByDeviceRegistrationId(deviceRegistrationId));
    if (deviceEntity != null) {
      Device device = deviceMapper.mapEntityIntoPojo(deviceEntity);
      storeToCache(device);
      return device;
    }
    throw new BadCredentialsException(String.format("%s is an invalid authToken"));
  }

  public Device findByAuthToken(String authToken) {

    UUID id = getCache().get(authToken, UUID.class);
    if (id != null) {

      Device device = getCache().get(id, Device.class);
      if (device != null) {
        return device;
      }
    }
    SessionEntity sessionEntity = getFirstElementFromList(sessionDao.findByAuthToken(authToken));
    if (sessionEntity != null) {
      DeviceEntity deviceEntity = deviceDao.findOne(sessionEntity.getSessionOwnerEntity().getId());
      Device device = deviceMapper.mapEntityIntoPojo(deviceEntity);
      storeToCache(device);
      return device;
    }
    throw new BadCredentialsException(String.format("%s is an invalid authToken"));
  }

  public Device deleteToken(UUID id) {
    DeviceEntity deviceEntity = deviceDao.findOne(id);
    Device device = deviceMapper.mapEntityIntoPojo(deviceEntity);
    evictFromCache(device);
    deviceEntity.setSessionEntity(null);
    return device;

  }

  public Device save(Device device) {
    DeviceEntity deviceEntity = deviceMapper.mapPojoIntoEntity(device);
    deviceEntity = deviceDao.save(deviceEntity);
    device = deviceMapper.mapEntityIntoPojo(deviceEntity);
    storeToCache(device);
    return device;
  }

  public Device update(UUID id, Device device) throws InvocationTargetException, IllegalAccessException {
    evictFromCache(device);
    DeviceEntity storedEntity = deviceDao.findOne(id);
    DeviceEntity newEntity = deviceMapper.mapPojoIntoEntity(device);
    ObjectPatcher.diffAndPatch(storedEntity, newEntity);
    storeToCache(device);
    return device;
  }

  public Device delete(UUID id) {
    Device device = findOne(id);
    if (device != null) {

      evictFromCache(device);
      deviceDao.delete(id);
      return device;
    }
    return null;
  }

  public void evictFromCache(Device pojo) {
    getCache().evict(pojo.getId());
    if (pojo.getSession() != null) {

      getCache().evict(pojo.getSession().getAuthToken());
    }
    getCache().evict(pojo.getDeviceRegistrationId());
  }

  private void storeToCache(Device pojo) {
    getCache().put(pojo.getId(), pojo);
    if (pojo.getSession() != null) {

      getCache().put(pojo.getSession().getAuthToken(), pojo.getId());
    }
    getCache().put(pojo.getDeviceRegistrationId(), pojo.getId());
  }

  public String generateAuthToken(String deviceRegistrationId) {

    UUID uuid = UUID.randomUUID();

    String token = Long.toHexString(uuid.getLeastSignificantBits()) +
        Long.toHexString(uuid.getMostSignificantBits()) +
        Hex.encodeHexString(deviceRegistrationId.getBytes());
    return token;
  }


}
