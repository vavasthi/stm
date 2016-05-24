package com.sanjnan.server.entity;

import com.sanjnan.server.config.mapper.annotations.SkipPatching;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

/**
 * Created by vinay on 1/28/16.
 */
@Entity
@Table(name = "accounts",
    indexes = {
        @Index(name = "accounts_email_index", columnList = "email", unique = true),
        @Index(name = "accounts_name_index", columnList = "name", unique = true)
    }
)
public class AccountEntity extends SessionOwnerEntity {

  public AccountEntity(TenantEntity tenant,
                       String name,
                       String email,
                       String password,
                       Set<String> remoteAddresses,
                       Set<RoleEntity> roles,
                       ComputeRegionEntity computeRegionEntity) {

    super(tenant, name, remoteAddresses, roles);
    this.email = email;
    this.password = password;
    this.computeRegionEntity = computeRegionEntity;
  }

  public AccountEntity() {

  }

  @SkipPatching
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Map<String, SessionEntity> getSessionMap() {
    return sessionMap;
  }

  public void setSessionMap(Map<String, SessionEntity> sessionMap) {
    this.sessionMap = sessionMap;
  }

  public Set<DeviceEntity> getDevices() {
    return devices;
  }

  public void setDevices(final Set<DeviceEntity> devices) {
    this.devices = devices;
  }

  public ComputeRegionEntity getComputeRegionEntity() {
    return computeRegionEntity;
  }

  public void setComputeRegionEntity(ComputeRegionEntity computeRegionEntity) {
    this.computeRegionEntity = computeRegionEntity;
  }


  private String email;
  private String password;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @MapKey(name = "applicationId")
  private Map<String, SessionEntity> sessionMap;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<DeviceEntity> devices;

  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH,
          CascadeType.PERSIST,
          CascadeType.MERGE,
          CascadeType.REFRESH})
  private ComputeRegionEntity computeRegionEntity;

}
