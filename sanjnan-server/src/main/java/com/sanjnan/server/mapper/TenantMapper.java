/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.mapper;

import com.sanjnan.server.dao.ComputeRegionDao;
import com.sanjnan.server.entity.ComputeRegionEntity;
import com.sanjnan.server.entity.TenantEntity;
import com.sanjnan.server.pojos.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by vinay on 1/8/16.
 */
@Service
public final class TenantMapper {

  @Autowired
  private ComputeRegionDao computeRegionDao;

  public List<Tenant> mapEntitiesIntoDTOs(Iterable<TenantEntity> entities) {
    List<Tenant> dtos = new ArrayList<>();

    entities.forEach(e -> dtos.add(mapEntityIntoPojo(e)));

    return dtos;
  }

  public Tenant mapEntityIntoPojo(TenantEntity entity) {

    List<UUID> computeRegions = new ArrayList<>();
    for (ComputeRegionEntity computeRegionEntity : entity.getComputeRegions()) {
      computeRegions.add(computeRegionEntity.getId());
    }
    Tenant pojo = new Tenant(
        entity.getId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getCreatedBy(),
        entity.getUpdatedBy(),
        entity.getName(),
        entity.getEmail(),
        entity.getDiscriminator(),
        computeRegions);
    return pojo;
  }

  public TenantEntity mapPojoIntoEntity(Tenant pojo) {

    Set<ComputeRegionEntity> computeRegionEntities = new HashSet<>();
    for (UUID id : pojo.getComputeRegions()) {
      computeRegionEntities.add(computeRegionDao.findOne(id));
    }
    TenantEntity te = new TenantEntity(pojo.getName(),
        pojo.getEmail(),
        pojo.getDiscriminator(),
        computeRegionEntities);
    return te;
  }

}
