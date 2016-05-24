/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.mapper;

import com.sanjnan.server.entity.ComputeRegionEntity;
import com.sanjnan.server.pojos.ComputeRegion;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 1/8/16.
 */
@Service
public final class ComputeRegionMapper {

  public static List<ComputeRegion> mapEntitiesIntoPojos(Iterable<ComputeRegionEntity> entities) {
    List<ComputeRegion> pojos = new ArrayList<>();

    entities.forEach(e -> pojos.add(mapEntityIntoPojo(e)));

    return pojos;
  }

  public static List<ComputeRegionEntity> mapPojosIntoEntities(Iterable<ComputeRegion> pojos) {

    List<ComputeRegionEntity> entities = new ArrayList<>();

    pojos.forEach(e -> entities.add(mapPojoIntoEntity(e)));

    return entities;
  }

  public static ComputeRegion mapEntityIntoPojo(ComputeRegionEntity entity) {
    ComputeRegion pojo = new ComputeRegion(
        entity.getId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt(),
        entity.getCreatedBy(),
        entity.getUpdatedBy(),
        entity.getName(),
        entity.getEndpointURL(),
        entity.getUserCount());
    return pojo;
  }

  public static ComputeRegionEntity mapPojoIntoEntity(ComputeRegion pojo) {
    ComputeRegionEntity te
        = new ComputeRegionEntity(pojo.getName(),
        pojo.getEndpointURL());
    return te;
  }

}
