/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.dao;

import com.sanjnan.server.sanjnan.entity.SessionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/6/16.
 */
public interface SessionDao extends CrudRepository<SessionEntity, UUID> {

  @Query("SELECT se from  com.hubbleconnected.server.identity.entity.SessionEntity se where se.authToken = :authToken")
  List<SessionEntity> findByAuthToken(@Param("authToken") String authToken);

  @Query("SELECT se from  com.hubbleconnected.server.identity.entity.SessionEntity se where se.remoteAddress = :remoteAddress")
  List<SessionEntity> findByRemoteAddr(@Param("remoteAddr") String remoteAddress);

}
