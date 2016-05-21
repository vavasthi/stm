/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.config.mapper;


import com.sanjnan.server.sanjnan.dao.AccountDao;
import com.sanjnan.server.sanjnan.dao.RoleDao;
import com.sanjnan.server.sanjnan.entity.AccountEntity;
import com.sanjnan.server.sanjnan.entity.DeviceEntity;
import com.sanjnan.server.sanjnan.entity.RoleEntity;
import com.sanjnan.server.sanjnan.pojos.Device;
import com.sanjnan.server.pojos.SanjnanRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by vinay on 1/8/16.
 */
@Service
public final class DeviceMapper {

  @Autowired
  private RoleDao roleDao;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private SessionMapper sessionMapper;

  public Set<Device> mapEntitiesIntoDTOs(Iterable<DeviceEntity> entities) {
    Set<Device> dtos = new HashSet<>();

    entities.forEach(e -> dtos.add(mapEntityIntoPojo(e)));

    return dtos;
  }

  public  Set<DeviceEntity> mapPojosIntoEntities(Iterable<Device> pojos) {
    Set<DeviceEntity> entities = new HashSet<>();

    pojos.forEach(e -> entities.add(mapPojoIntoEntity(e)));

    return entities;
  }

  public Device mapEntityIntoPojo(DeviceEntity entity) {
    UUID accountId = null;
    if (entity.getAccountEntity() != null) {
      accountId = entity.getAccountEntity().getId();
    }
    Device pojo = new Device(entity.getId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getCreatedBy(),
        entity.getUpdatedBy(),
        entity.getDeviceRegistrationId(),
        RoleMapper.mapEntitiesIntoDTOs(entity.getRoles()));
    pojo.setAccountId(accountId);
    if (entity.getSessionEntity() != null) {

      pojo.setSession(sessionMapper.mapEntityIntoPojo(entity.getSessionEntity()));
    }
    return pojo;
  }

  public  DeviceEntity mapPojoIntoEntity(Device pojo) {

    Set<RoleEntity> roleEntities = new HashSet<>();
    for (SanjnanRole r : pojo.getH2ORoles()) {
      Set<RoleEntity> roleEntityList = roleDao.findByRole(r.getAuthority());
      if (roleEntityList != null && roleEntityList.size() > 0) {
        roleEntities.add(roleEntityList.iterator().next());
      }
    }
    AccountEntity accountEntity = accountDao.findOne(pojo.getAccountId());
    DeviceEntity de  = new DeviceEntity(pojo.getDeviceRegistrationId(), accountEntity);
    de.setRoles(roleEntities);
    return de;
  }
}
