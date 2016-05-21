/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.caching;

import com.sanjnan.server.config.annotations.ORMCache;
import com.sanjnan.server.config.mapper.ObjectPatcher;
import com.sanjnan.server.exception.EntityAlreadyExistsException;
import com.sanjnan.server.exception.EntityNotFoundException;
import com.sanjnan.server.sanjnan.config.mapper.TenantMapper;
import com.sanjnan.server.sanjnan.dao.TenantDao;
import com.sanjnan.server.sanjnan.entity.ComputeRegionEntity;
import com.sanjnan.server.sanjnan.entity.TenantEntity;
import com.sanjnan.server.sanjnan.pojos.Tenant;
import com.sanjnan.server.pojos.constants.H2OConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 2/22/16.
 */
@Service
@ORMCache(name = H2OConstants.H2O_TENANT_CACHE_NAME, expiry = H2OConstants.SIX_HOURS, prefix = H2OConstants.H2O_TENANT_CACHE_PREFIX)
public class TenantCacheService {

  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private TenantDao tenantDao;
  @Autowired
  private TenantMapper tenantMapper;
  
  public Tenant findByDiscriminator(String discriminator) {

    Tenant tenant = getCache().get(discriminator, Tenant.class);
    if (tenant == null) {

      tenant = tenantMapper.mapEntityIntoPojo(getFirstElementFromList(tenantDao.findByDiscriminator(discriminator)));
      storeToCache(tenant);
    }
    return tenant;
  }

  public Tenant findById(UUID id) {

    Tenant tenant = getCache().get(id, Tenant.class);
    if (tenant == null) {

      TenantEntity tenantEntity = tenantDao.findOne(id);
      if (tenantEntity != null) {

        tenant = tenantMapper.mapEntityIntoPojo(tenantEntity);
        storeToCache(tenant);
      }
      else {
        throw new EntityNotFoundException(String.format("%s tenant not found.", id.toString()));
      }
    }
    return tenant;
  }

  public Tenant deleteTenant(Tenant tenant) {
    TenantEntity tenantEntity = tenantDao.findOne(tenant.getId());
    if (tenantEntity != null) {
      for (ComputeRegionEntity cre : tenantEntity.getComputeRegions()) {
        cre.getTenants().remove(tenantEntity);
      }
      tenantDao.delete(tenantEntity);
      evictFromCache(tenant);
    }
    return tenant;
  }

  public Tenant save(Tenant tenant) {

    Tenant storedTenant = getCache().get(tenant.getDiscriminator(), Tenant.class);
    if (storedTenant == null) {
      List<TenantEntity> tenantEntities = tenantDao.findByDiscriminator(tenant.getDiscriminator());
      if (tenantEntities != null && tenantEntities.size() > 0) {
        throw new EntityAlreadyExistsException(String.format("%s tenant already exists.", tenant.getDiscriminator()));
      }
    }
    TenantEntity tenantEntity = tenantDao.save(tenantMapper.mapPojoIntoEntity(tenant));
    for (ComputeRegionEntity cre : tenantEntity.getComputeRegions()) {
      cre.getTenants().add(tenantEntity);
    }
    tenant = tenantMapper.mapEntityIntoPojo(tenantEntity);
    storeToCache(tenant);
    return tenant;
  }

  public Tenant update(UUID id, Tenant tenant) throws InvocationTargetException, IllegalAccessException {

    Tenant cachedTenant = findById(id);
    evictFromCache(cachedTenant);
    TenantEntity storedEntity = tenantDao.findOne(id);
    TenantEntity newEntity = tenantMapper.mapPojoIntoEntity(tenant);
    ObjectPatcher.diffAndPatch(storedEntity, newEntity);
    return tenant;
  }


  private void storeToCache(Tenant tenant) {
    getCache().put(tenant.getId(), tenant);
    getCache().put(tenant.getDiscriminator(), tenant);
  }
  private void evictFromCache(Tenant tenant) {
    getCache().evict(tenant.getId());
    getCache().evict(tenant.getDiscriminator());
  }

  private TenantEntity getFirstElementFromList(List<TenantEntity> tenantEntities) {

    if (tenantEntities != null && tenantEntities.size() != 0) {
      return tenantEntities.get(0);
    }
    else {
      throw new EntityNotFoundException("Entity not found.");
    }
  }
  private Cache getCache() {

    String cacheName = this.getClass().getAnnotation(ORMCache.class).name();
    Cache cache = cacheManager.getCache(cacheName);
    return cache;
  }
}
