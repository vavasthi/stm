/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.dao;

import com.sanjnan.server.sanjnan.entity.TenantEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/6/16.
 */
public interface TenantDao extends CrudRepository<TenantEntity, UUID> {

    @Query("SELECT te from  com.hubbleconnected.server.identity.entity.TenantEntity te where te.discriminator = :discriminator")
    List<TenantEntity> findByDiscriminator(@Param("discriminator") String discriminator);
}
