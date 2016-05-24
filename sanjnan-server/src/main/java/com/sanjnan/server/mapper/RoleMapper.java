/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.mapper;


import com.sanjnan.server.entity.RoleEntity;
import com.sanjnan.server.pojos.SanjnanRole;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by vinay on 1/8/16.
 */
public final class RoleMapper {

  public  static Set<SanjnanRole> mapEntitiesIntoDTOs(Iterable<RoleEntity> entities) {
    Set<SanjnanRole> dtos = new HashSet<>();

    entities.forEach(e -> dtos.add(mapEntityIntoPojo(e)));

    return dtos;
  }

  public static Set<RoleEntity> mapPojosIntoEntitiess(Iterable<SanjnanRole> pojos) {
    Set<RoleEntity> entities = new HashSet<>();

    pojos.forEach(e -> entities.add(mapPojoIntoEntity(e)));

    return entities;
  }

  public static SanjnanRole mapEntityIntoPojo(RoleEntity role) {
    SanjnanRole pojo = new SanjnanRole(role.getName());
    return pojo;
  }

  public static RoleEntity mapPojoIntoEntity(SanjnanRole pojo) {

    RoleEntity re  = new RoleEntity(pojo.getAuthority());
    return re;
  }

}
