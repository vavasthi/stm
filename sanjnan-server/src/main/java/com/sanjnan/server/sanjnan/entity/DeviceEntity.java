package com.sanjnan.server.sanjnan.entity;

import javax.persistence.*;
import java.util.HashSet;

/**
 * Created by vinay on 1/28/16.
 */
@Entity
@Table(name = "devices",
    indexes = {
    }
)
public class DeviceEntity extends SessionOwnerEntity {

  public DeviceEntity(String deviceRegistrationId,
                      AccountEntity accountEntity) {

    super(accountEntity.getTenant(),
            deviceRegistrationId,
            new HashSet<>(),
            new HashSet<>());

    this.deviceRegistrationId = deviceRegistrationId;
    this.accountEntity = accountEntity;
  }

  public DeviceEntity() {

  }

  public String getDeviceRegistrationId() {
    return deviceRegistrationId;
  }

  public void setDeviceRegistrationId(final String deviceRegistrationId) {
    this.deviceRegistrationId = deviceRegistrationId;
  }

  public SessionEntity getSessionEntity() {
    return sessionEntity;
  }

  public void setSessionEntity(final SessionEntity sessionEntity) {
    this.sessionEntity = sessionEntity;
  }

  public AccountEntity getAccountEntity() {
    return accountEntity;
  }

  public void setAccountEntity(final AccountEntity accountEntity) {
    this.accountEntity = accountEntity;
  }

  private String deviceRegistrationId;

  @OneToOne(orphanRemoval = true, fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "sessionOwnerEntity")
  private SessionEntity sessionEntity;

  @OneToOne(fetch=FetchType.EAGER)
  private AccountEntity accountEntity;
}
