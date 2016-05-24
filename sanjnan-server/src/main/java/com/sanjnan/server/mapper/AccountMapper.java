/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.mapper;

import com.sanjnan.server.exception.EncryptionException;
import com.sanjnan.server.dao.ComputeRegionDao;
import com.sanjnan.server.dao.DeviceDao;
import com.sanjnan.server.dao.RoleDao;
import com.sanjnan.server.dao.TenantDao;
import com.sanjnan.server.entity.RoleEntity;
import com.sanjnan.server.entity.TenantEntity;
import com.sanjnan.server.pojos.SanjnanRole;
import com.sanjnan.server.entity.AccountEntity;
import com.sanjnan.server.entity.ComputeRegionEntity;
import com.sanjnan.server.pojos.Account;
import com.sanjnan.server.pojos.Device;
import com.sanjnan.server.pojos.Session;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by vinay on 1/8/16.
 */
@Service
public final class AccountMapper {

  Logger logger = Logger.getLogger(AccountMapper.class);

  private final TenantDao tenantDao;
  private final ComputeRegionDao computeRegionDao;
  private final RoleDao roleDao;
  private final DeviceDao deviceDao;
  private final TenantMapper tenantMapper;
  private final SessionMapper sessionMapper;
  private final DeviceMapper deviceMapper;

  @Autowired
  public AccountMapper(TenantDao tenantDao,
                       ComputeRegionDao computeRegionDao,
                       RoleDao roleDao,
                       DeviceDao deviceDao,
                       TenantMapper tenantMapper,
                       SessionMapper sessionMapper,
                       DeviceMapper deviceMapper) {

    this.tenantDao = tenantDao;
    this.computeRegionDao = computeRegionDao;
    this.roleDao = roleDao;
    this.deviceDao = deviceDao;
    this.tenantMapper = tenantMapper;
    this.sessionMapper = sessionMapper;
    this.deviceMapper = deviceMapper;
  }
  public List<Account> mapEntitiesIntoPojos(Iterable<AccountEntity> entities) {
    List<Account> pojos = new ArrayList<>();

    entities.forEach(e -> pojos.add(mapEntityIntoPojo(e)));

    return pojos;
  }

  public List<AccountEntity> mapPojosIntoEntities(Iterable<Account> pojos) {
    List<AccountEntity> entities = new ArrayList<>();

    pojos.forEach(e -> {

          try {
            entities.add(mapPojoIntoEntity(e));

          } catch (NoSuchPaddingException
              | UnsupportedEncodingException
              | IllegalBlockSizeException
              | BadPaddingException
              | NoSuchAlgorithmException
              | InvalidParameterSpecException
              | InvalidKeyException
              | InvalidKeySpecException ex) {
            logger.log(Level.ERROR, "Could not convert POJOs to entity.", ex);
            throw new EncryptionException("Could not convert POJOs to entity");
          }
        }
      );

    return entities;
  }

  public Account mapEntityIntoPojo(AccountEntity entity) {
    if (entity.getRemoteAddresses() != null) {

      entity.getRemoteAddresses().size();
    }
    if (entity.getRoles() != null) {

      entity.getRoles().size();
    }
    Map<String, Session> sessionMap = null;
    if (entity.getSessionMap() != null) {
      sessionMap = entity
          .getSessionMap()
          .entrySet()
          .stream()
          .collect(Collectors.toMap(e -> e.getKey(), e -> sessionMapper.mapEntityIntoPojo(e.getValue())));
    }
    Set<SanjnanRole> h2ORoles = null;
    if (entity.getRoles() != null && entity.getRoles().size() > 0) {
      h2ORoles = RoleMapper.mapEntitiesIntoDTOs(entity.getRoles());
    }
    Set<Device> devices = null;
    if (entity.getDevices() != null && entity.getDevices().size() > 0) {
      devices = deviceMapper.mapEntitiesIntoDTOs(entity.getDevices());
    }
    Account pojo = new Account(
        entity.getId(),
        tenantMapper.mapEntityIntoPojo(entity.getTenant()),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getCreatedBy(),
        entity.getUpdatedBy(),
        entity.getName(),
        entity.getEmail(),
        entity.getPassword(),
        sessionMap,
        entity.getRemoteAddresses(),
            h2ORoles,
        devices,
        ComputeRegionMapper.mapEntityIntoPojo(entity.getComputeRegionEntity()));
    return pojo;
  }

  public AccountEntity mapPojoIntoEntity(Account pojo) throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidParameterSpecException, InvalidKeyException, InvalidKeySpecException {
    TenantEntity tenantEntity = tenantDao.findOne(pojo.getTenant().getId());
    ComputeRegionEntity computeRegionEntity = computeRegionDao.findOne(pojo.getComputeRegion().getId());
    Set<RoleEntity> roleEntityList = new HashSet<>();
    for (SanjnanRole h2ORole : pojo.getH2ORoles()) {
      Set<RoleEntity> rel = roleDao.findByRole(h2ORole.getAuthority());
      if (rel != null && rel.size() > 0) {

        roleEntityList.add(rel.iterator().next());
      }
    }
    AccountEntity ae = new AccountEntity(tenantEntity, pojo.getName(), pojo.getEmail(),
        pojo.getPassword(),
        pojo.getRemoteAddresses(),
        roleEntityList,
        computeRegionEntity);
    return ae;
  }

}
