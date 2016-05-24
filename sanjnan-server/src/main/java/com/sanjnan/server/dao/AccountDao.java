/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.dao;

import com.sanjnan.server.entity.AccountEntity;
import com.sanjnan.server.entity.TenantEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/6/16.
 */
public interface AccountDao extends CrudRepository<AccountEntity, UUID> {

  @Query("SELECT ae from  com.sanjnan.server.entity.AccountEntity ae where ae.id = :id or ae.email = :email")
  List<AccountEntity> findByIdOrEmail(@Param("id") UUID id,
                                      @Param("email") String email);

  @Query("SELECT ae from  com.sanjnan.server.entity.AccountEntity ae where ae.name = :name")
  List<AccountEntity> findByName(@Param("name") String name);

  @Query("SELECT ae from  com.sanjnan.server.entity.AccountEntity ae where ae.name = :name or ae.email = " +
      ":email")
  List<AccountEntity> findByNameOrEmail(@Param("name") String name,
                                       @Param("email") String email);

    @Query("SELECT ae from  com.sanjnan.server.entity.AccountEntity ae where ae.tenant = :tenant")
    List<AccountEntity> findByTenant(@Param("tenant") TenantEntity tenantEntity);

    @Query("SELECT ae from  com.sanjnan.server.entity.AccountEntity ae where ae.tenant = :tenant and ae.id = :id")
    List<AccountEntity> findByTenantAndId(@Param("tenant") TenantEntity tenantEntity,
                                          @Param("id") UUID id);

  @Query("SELECT ae from  com.sanjnan.server.entity.AccountEntity ae where ae.tenant = :tenant and ae.name = :name")
  List<AccountEntity> findByTenantAndName(@Param("tenant") TenantEntity tenantEntity,
                                          @Param("name") String name);

  @Query("SELECT ae from  com.sanjnan.server.entity.AccountEntity ae where ae.email = :email and ae.tenant = :tenant")
  List<AccountEntity> findByTenantAndEmail(@Param("tenant") TenantEntity tenant,
                                           @Param("email") String email);

}
