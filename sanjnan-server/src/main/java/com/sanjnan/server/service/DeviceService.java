/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.service;

import com.sanjnan.server.caching.AbstractCacheService;
import com.sanjnan.server.caching.AccountCacheService;
import com.sanjnan.server.caching.DeviceCacheService;
import com.sanjnan.server.mapper.DeviceMapper;
import com.sanjnan.server.dao.DeviceDao;
import com.sanjnan.server.dao.SessionDao;
import com.sanjnan.server.pojos.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by vinay on 2/22/16.
 */
@Service
public class DeviceService extends AbstractCacheService {

  @Autowired
  private DeviceCacheService deviceCacheService;
  @Autowired
  private AccountCacheService accountCacheService;
  @Autowired
  private DeviceDao deviceDao;
  @Autowired
  private SessionDao sessionDao;
  @Autowired
  private DeviceMapper deviceMapper;

  public Set<Device> list() {
    return deviceCacheService.list();
  }

  public Device findOne(UUID id) {
    return deviceCacheService.findOne(id);
  }

  /**
   * This method will return a @Ref Device pojo that maps to a given deviceRegistrationId.
   * @param deviceRegistrationId
   * @return
   */
  public Device findByDeviceRegistrationId(String deviceRegistrationId) {

    return deviceCacheService.findByDeviceRegistrationId(deviceRegistrationId);
  }

  public Device findByAuthToken(String authToken) {

    return findByAuthToken(authToken);
  }

  public Device deleteToken(UUID id) {
    return deviceCacheService.deleteToken(id);
  }

  public Device save(Device device) {
    return deviceCacheService.save(device);
  }

  public Device update(UUID id, Device device) throws InvocationTargetException, IllegalAccessException {
    return deviceCacheService.update(id, device);
  }

  /**
   * This method would delete the device. If the device was an adopted device, the association needs to be removed.
   * @param id
   * @return
   */
  public Device delete(UUID id) {
    Device device = deviceCacheService.findOne(id);
    if (device != null) {

      if (device.getAccountId() != null) {
        accountCacheService.unadoptDevice(device.getAccountId(), device);
      }
      return deviceCacheService.delete(id);
    }
    return null;
  }
}
