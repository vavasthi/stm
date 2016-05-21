/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.dao;

import com.sanjnan.server.sanjnan.entity.ComputeRegionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Created by vinay on 1/6/16.
 */
public interface ComputeRegionDao extends CrudRepository<ComputeRegionEntity, UUID> {

}
