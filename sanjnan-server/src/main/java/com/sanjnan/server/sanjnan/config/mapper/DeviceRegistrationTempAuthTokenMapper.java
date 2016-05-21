/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.config.mapper;

import com.sanjnan.server.sanjnan.dao.AccountDao;
import com.sanjnan.server.sanjnan.dao.DeviceDao;
import com.sanjnan.server.sanjnan.entity.AccountEntity;
import com.sanjnan.server.sanjnan.entity.DeviceEntity;
import com.sanjnan.server.sanjnan.entity.DeviceRegistrationTempAuthTokenEntity;
import com.sanjnan.server.sanjnan.pojos.Account;
import com.sanjnan.server.sanjnan.pojos.Device;
import com.sanjnan.server.sanjnan.pojos.DeviceRegistrationTempAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 1/8/16.
 */
@Service
public final class DeviceRegistrationTempAuthTokenMapper {

  @Autowired
  private AccountMapper accountMapper;
  @Autowired
  private DeviceMapper deviceMapper;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private DeviceDao deviceDao;

  public  List<DeviceRegistrationTempAuthToken>
  mapEntitiesIntoPojos(Iterable<DeviceRegistrationTempAuthTokenEntity> entities) {
    List<DeviceRegistrationTempAuthToken> pojos = new ArrayList<>();

    entities.forEach(e -> pojos.add(mapEntityIntoPojo(e)));

    return pojos;
  }

  public  List<DeviceRegistrationTempAuthTokenEntity> mapPojosIntoEntities(Iterable<DeviceRegistrationTempAuthToken> pojos) {

    List<DeviceRegistrationTempAuthTokenEntity> entities = new ArrayList<>();

    pojos.forEach(e -> entities.add(mapPojoIntoEntity(e)));

    return entities;
  }

  public  DeviceRegistrationTempAuthToken mapEntityIntoPojo(DeviceRegistrationTempAuthTokenEntity entity) {
    Account account = null;
    Device device = null;
    if (entity.getAccountEntity() != null) {
      account = accountMapper.mapEntityIntoPojo(entity.getAccountEntity());
    }
    if (entity.getDeviceEntity() != null) {
      device = deviceMapper.mapEntityIntoPojo(entity.getDeviceEntity());
    }
    DeviceRegistrationTempAuthToken pojo = new DeviceRegistrationTempAuthToken(
        entity.getId(),
        account,
        device,
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getCreatedBy(),
        entity.getUpdatedBy(),
        entity.getName(),
        entity.getTempAuthToken(),
        entity.getExpiry());
    return pojo;
  }

  public DeviceRegistrationTempAuthTokenEntity mapPojoIntoEntity(DeviceRegistrationTempAuthToken pojo) {
    AccountEntity accountEntity = null;
    DeviceEntity deviceEntity = null;
    if (pojo.getAccount() != null) {
      accountEntity = accountDao.findOne(pojo.getAccount().getId());
    }
    if (pojo.getDevice() != null) {

      deviceEntity = deviceDao.findOne(pojo.getDevice().getId());
    }
    DeviceRegistrationTempAuthTokenEntity te
        = new DeviceRegistrationTempAuthTokenEntity(accountEntity,
        deviceEntity,
        pojo.getTempAuthToken());
    return te;
  }

}
