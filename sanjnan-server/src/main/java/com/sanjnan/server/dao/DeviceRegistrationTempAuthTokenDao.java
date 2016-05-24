/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.dao;

import com.sanjnan.server.entity.DeviceEntity;
import com.sanjnan.server.entity.DeviceRegistrationTempAuthTokenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/6/16.
 */
public interface DeviceRegistrationTempAuthTokenDao extends CrudRepository<DeviceRegistrationTempAuthTokenEntity, UUID> {

  @Query("SELECT re from  com.sanjnan.server.entity.DeviceRegistrationTempAuthTokenEntity re where re.name = :deviceRegistrationId ")
  List<DeviceRegistrationTempAuthTokenEntity> findByDeviceRegistrationId(@Param("deviceRegistrationId") String deviceRegistrationId);

  @Query("SELECT re from  com.sanjnan.server.entity.DeviceRegistrationTempAuthTokenEntity re where re.tempAuthToken = :tempAuthToken ")
  List<DeviceRegistrationTempAuthTokenEntity> findByTempAuthToken(@Param("tempAuthToken") String tempAuthToken);

  @Query("SELECT re from  com.sanjnan.server.entity.DeviceRegistrationTempAuthTokenEntity re where re.deviceEntity = :deviceEntity ")
  List<DeviceRegistrationTempAuthTokenEntity> findByDeviceEntity(@Param("deviceEntity") DeviceEntity deviceEntity);
}
