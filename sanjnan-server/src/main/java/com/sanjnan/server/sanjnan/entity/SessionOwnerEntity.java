/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.entity;

import com.sanjnan.server.config.mapper.annotations.SkipPatching;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by vinay on 1/28/16.
 */
@Entity
@Table(name = "session_owners")

public class SessionOwnerEntity extends BaseEntity {

  public SessionOwnerEntity(TenantEntity tenant,
                            String name,
                            Set<String> remoteAddresses,
                            Set<RoleEntity> roles) {
    super(name);
    this.tenant = tenant;
    this.remoteAddresses = remoteAddresses;
    this.roles = roles;
  }

  public SessionOwnerEntity() {

  }

  public Set<RoleEntity> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleEntity> roles) {
    this.roles = roles;
  }

  @SkipPatching
  public TenantEntity getTenant() {
    return tenant;
  }

  public void setTenant(TenantEntity tenant) {
    this.tenant = tenant;
  }

  public Set<String> getRemoteAddresses() {
    return remoteAddresses;
  }

  public void setRemoteAddresses(Set<String> remoteAddresses) {
    this.remoteAddresses = remoteAddresses;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<RoleEntity> roles;

  @ElementCollection(fetch = FetchType.EAGER)
  private Set<String> remoteAddresses;

  @ManyToOne(fetch = FetchType.EAGER)
  private TenantEntity tenant;

}
