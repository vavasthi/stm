/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Date;

/**
 * Created by vinay on 1/28/16.
 */
@Entity
@Table(name = "sessions")
public class SessionEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.EAGER)
  private SessionOwnerEntity sessionOwnerEntity;

  private String authToken;
  private String applicationId;
  private String remoteAddress;
  @Column(columnDefinition = "DATETIME")
  @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
  private DateTime expiry;
  private int sessionType;

  public SessionEntity(String authToken,
                       String remoteAddress,
                       String applicationId,
                       SessionOwnerEntity sessionOwnerEntity,
                       int sessionType) throws DatatypeConfigurationException {
    super(authToken);
    this.authToken = authToken;
    this.remoteAddress = remoteAddress;
    this.applicationId = applicationId;
    this.sessionOwnerEntity = sessionOwnerEntity;
    expiry = new DateTime(new Date().getTime() + (7L * 24 * 60 * 60 * 1000));
    this.sessionType = sessionType;
  }

  public SessionEntity() {

  }

  public SessionOwnerEntity getSessionOwnerEntity() {
    return sessionOwnerEntity;
  }

  public void setSessionOwnerEntity(SessionOwnerEntity sessionOwnerEntity) {
    this.sessionOwnerEntity = sessionOwnerEntity;
  }

  public String getAuthToken() {
    return authToken;
  }

  /**
   * This method allows setting of a new authtoken into the session. The expiry is reset every time the auth token it set.
   * @param authToken
   */
  public void setAuthToken(String authToken) {
    this.authToken = authToken;
    expiry = new DateTime(new Date().getTime() + (7L * 24 * 60 * 60 * 1000));
  }

  public String getRemoteAddress() {
    return remoteAddress;
  }

  public void setRemoteAddress(String remoteAddress) {
    this.remoteAddress = remoteAddress;
  }

  public String getApplicationId() {
    return applicationId;
  }

  public void setApplicationId(String applicationId) {
    this.applicationId = applicationId;
  }

  public DateTime getExpiry() {
    return expiry;
  }

  public void setExpiry(final DateTime expiry) {
    this.expiry = expiry;
  }

  public int getSessionType() {
    return sessionType;
  }

  public void setSessionType(final int sessionType) {
    this.sessionType = sessionType;
  }
}
