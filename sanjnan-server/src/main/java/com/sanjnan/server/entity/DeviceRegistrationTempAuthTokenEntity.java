/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vinay on 1/28/16.
 */
@Entity
@Table(name = "device_reg_temp_auth_tokens",
    indexes = {
        @Index(name = "deviceRegTempAuthTokensIdx",  columnList="name", unique = true)
    }
)
public class DeviceRegistrationTempAuthTokenEntity extends BaseEntity {

  private final int TEMP_AUTH_TOKEN_EXPIRY_MILLISECONDS = 30 * 60 * 1000;

  public DeviceRegistrationTempAuthTokenEntity(AccountEntity accountEntity,
                                               DeviceEntity deviceEntity,
                                               String tempAuthToken) {

    super(deviceEntity.getDeviceRegistrationId());
    this.accountEntity = accountEntity;
    this.deviceEntity = deviceEntity;
    this.tempAuthToken = tempAuthToken;
    expiry = new DateTime(new Date().getTime() + (TEMP_AUTH_TOKEN_EXPIRY_MILLISECONDS));
  }

  public DeviceRegistrationTempAuthTokenEntity() {
  }

  public String getTempAuthToken() {
    return tempAuthToken;
  }

  public void setTempAuthToken(final String tempAuthToken) {
    this.tempAuthToken = tempAuthToken;
  }

  public DateTime getExpiry() {
    return expiry;
  }

  public void setExpiry(final DateTime expiry) {
    this.expiry = expiry;
  }

  public AccountEntity getAccountEntity() {
    return accountEntity;
  }

  public void setAccountEntity(final AccountEntity accountEntity) {
    this.accountEntity = accountEntity;
  }

  public DeviceEntity getDeviceEntity() {
    return deviceEntity;
  }

  public void setDeviceEntity(final DeviceEntity deviceEntity) {
    this.deviceEntity = deviceEntity;
  }

  @OneToOne(fetch=FetchType.EAGER)
  private AccountEntity accountEntity;
  @OneToOne(fetch=FetchType.EAGER)
  private DeviceEntity deviceEntity;
  private String tempAuthToken;
  @Column(columnDefinition = "DATETIME")
  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
  private DateTime expiry;
}
