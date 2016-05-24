/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.service;

import com.sanjnan.server.exception.EntityNotFoundException;
import com.sanjnan.server.exception.PatchingException;
import com.sanjnan.server.caching.AccountCacheService;
import com.sanjnan.server.caching.TenantCacheService;
import com.sanjnan.server.mapper.ComputeRegionMapper;
import com.sanjnan.server.mapper.TenantMapper;
import com.sanjnan.server.dao.TenantDao;
import com.sanjnan.server.entity.TenantEntity;
import com.sanjnan.server.pojos.ComputeRegion;
import com.sanjnan.server.pojos.Tenant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/8/16.
 */
@Service
public class TenantService {

  private static final Logger logger = Logger.getLogger(TenantService.class);
  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private TenantMapper tenantMapper;
  @Autowired
  private ComputeRegionMapper computeRegionMapper;
  @Autowired
  private TenantDao tenantDao;
  @Autowired
  private TenantCacheService tenantCacheService;
  @Autowired
  private AccountCacheService accountCacheService;

  @Transactional(readOnly = true)
  public Tenant getTenant(UUID id) throws EntityNotFoundException {

    return tenantCacheService.findById(id);
  }

  @Transactional(readOnly = true)
  public Tenant getTenant(String discriminator) throws EntityNotFoundException {

    return tenantCacheService.findByDiscriminator(discriminator);
  }

  @Transactional(readOnly = true)
  public ComputeRegion getNextComputeRegion(UUID id) {

    TenantEntity te = tenantDao.findOne(id);
    return ComputeRegionMapper.mapEntityIntoPojo(te.getNextComputeRegionEntity());
  }

  @Transactional(readOnly = true)
  public List<Tenant> listTenants() {

    List<Tenant> tenantList = new ArrayList<>();

    tenantDao.findAll().forEach(e -> tenantList.add(tenantMapper.mapEntityIntoPojo(e)));

    return tenantList;
  }

  @Transactional
  public Tenant createTenant(Tenant tenant) {
    return tenantCacheService.save(tenant);
  }

  @Transactional
  public Tenant updateTenant(UUID id, Tenant tenant) throws IllegalAccessException,
          PatchingException,
          InvocationTargetException {

    return tenantCacheService.update(id, tenant);
  }

  @Transactional
  public Tenant deleteTenant(UUID id) throws IllegalAccessException, PatchingException, InvocationTargetException {
    Tenant tenant = tenantCacheService.findById(id);
    accountCacheService.deleteAllAccounts(tenant);
    return tenantCacheService.deleteTenant(tenant);
  }
}
