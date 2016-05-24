/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by vinay on 1/28/16.
 */
@Entity
@Table(name = "compute_regions")
public class ComputeRegionEntity extends BaseEntity {

  public ComputeRegionEntity(String name, String endpointURL) {
    super(name);
    this.endpointURL = endpointURL;
    this.userCount = 0L;
  }

  public ComputeRegionEntity() {
  }

  public String getEndpointURL() {
    return endpointURL;
  }

  public void setEndpointURL(String endpointURL) {
    this.endpointURL = endpointURL;
  }

  public Long getUserCount() {
    return userCount;
  }

  public void setUserCount(Long userCount) {
    this.userCount = userCount;
  }

  public synchronized void incrementUserCount() {
    ++(this.userCount);
  }

  public synchronized void decrementUserCount() {
    --(this.userCount);
  }

  public Set<TenantEntity> getTenants() {
    return tenants;
  }

  public void setTenants(Set<TenantEntity> tenants) {
    this.tenants = tenants;
  }

  private String endpointURL;
  private Long userCount;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "computeRegions")
  private Set<TenantEntity> tenants;
}
