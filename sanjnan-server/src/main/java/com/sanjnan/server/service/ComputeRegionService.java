/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.service;

import com.sanjnan.server.config.mapper.ObjectPatcher;
import com.sanjnan.server.exception.EntityNotFoundException;
import com.sanjnan.server.exception.PatchingException;
import com.sanjnan.server.mapper.ComputeRegionMapper;
import com.sanjnan.server.dao.ComputeRegionDao;
import com.sanjnan.server.entity.ComputeRegionEntity;
import com.sanjnan.server.pojos.ComputeRegion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/8/16.
 */
@Service
public class ComputeRegionService {

  private static final Logger logger = Logger.getLogger(ComputeRegionService.class);

  @Autowired
  private ComputeRegionDao computeRegionDao;
  @Autowired
  private ComputeRegionMapper computeRegionMapper;


  @Transactional(readOnly = true)
  public ComputeRegion getComputeRegion(UUID id) throws EntityNotFoundException {
    ComputeRegionEntity cre = computeRegionDao.findOne(id);
    if (cre == null) {
      throw new EntityNotFoundException(id.toString() + " compute region not found.", HttpStatus.NOT_FOUND.value());
    }
    return computeRegionMapper.mapEntityIntoPojo(cre);
  }

  @Transactional(readOnly = true)
  public List<ComputeRegion> listComputeRegions() {

    return computeRegionMapper.mapEntitiesIntoPojos(computeRegionDao.findAll());
  }

  @Transactional
  public ComputeRegion createComputeRegion(ComputeRegion computeRegion) {

    ComputeRegionEntity cre = computeRegionMapper.mapPojoIntoEntity(computeRegion);
    cre = computeRegionDao.save(cre);
    return computeRegionMapper.mapEntityIntoPojo(cre);
  }

  @Transactional
  public ComputeRegion updateRegion(UUID id,
                                    ComputeRegion computeRegion) throws IllegalAccessException,
          PatchingException,
          InvocationTargetException {

    ComputeRegionEntity cre = computeRegionDao.findOne(id);
    ComputeRegionEntity newCre = computeRegionMapper.mapPojoIntoEntity(computeRegion);
    ObjectPatcher.diffAndPatch(cre, newCre);
    return computeRegionMapper.mapEntityIntoPojo(cre);
  }

  @Transactional
  public ComputeRegion deleteComputeRegion(UUID id)
      throws IllegalAccessException, PatchingException, InvocationTargetException {

    ComputeRegionEntity cre = computeRegionDao.findOne(id);
    ComputeRegion pojo = computeRegionMapper.mapEntityIntoPojo(cre);
    computeRegionDao.delete(cre);
    return pojo;
  }

}
