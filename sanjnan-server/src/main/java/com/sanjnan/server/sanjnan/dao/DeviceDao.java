/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.dao;

import com.sanjnan.server.sanjnan.entity.DeviceEntity;
import com.sanjnan.server.sanjnan.entity.SessionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/6/16.
 */
public interface DeviceDao extends CrudRepository<DeviceEntity, UUID> {

  @Query("SELECT de from  com.hubbleconnected.server.identity.entity.DeviceEntity de where de.deviceRegistrationId = :deviceRegistrationId")
  List<DeviceEntity> findByDeviceRegistrationId(@Param("deviceRegistrationId") String deviceRegistrationId);

  @Query("SELECT de from  com.hubbleconnected.server.identity.entity.DeviceEntity de where de.sessionEntity = :sessionEntity")
  List<DeviceEntity> findBySessionEntity(@Param("sessionEntity") SessionEntity sessionEntity);

}
