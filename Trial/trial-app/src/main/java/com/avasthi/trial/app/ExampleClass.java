package com.avasthi.trial.app;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by vavasthi on 18/6/16.
 */
public class ExampleClass implements Serializable{
  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public Integer getAlert() {
    return alert;
  }

  public void setAlert(Integer alert) {
    this.alert = alert;
  }

  public UUID getParentId() {
    return parentId;
  }

  public void setParentId(UUID parentId) {
    this.parentId = parentId;
  }

  public UUID getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(UUID deviceId) {
    this.deviceId = deviceId;
  }

  public Date getEventTime() {
    return eventTime;
  }

  public void setEventTime(Date eventTime) {
    this.eventTime = eventTime;
  }

  public Long getEpochValue() {
    return epochValue;
  }

  public void setEpochValue(Long epochValue) {
    this.epochValue = epochValue;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public String getEventCode() {
    return eventCode;
  }

  public void setEventCode(String eventCode) {
    this.eventCode = eventCode;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public int getStorageMode() {
    return storageMode;
  }

  public void setStorageMode(int storageMode) {
    this.storageMode = storageMode;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Integer getEventCounter() {
    return eventCounter;
  }

  public void setEventCounter(Integer eventCounter) {
    this.eventCounter = eventCounter;
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }

  public String getEventClass() {
    return eventClass;
  }

  public void setEventClass(String eventClass) {
    this.eventClass = eventClass;
  }

  public UUID getProfileId() {
    return profileId;
  }

  public void setProfileId(UUID profileId) {
    this.profileId = profileId;
  }

  public Integer getUnit() {
    return unit;
  }

  public void setUnit(Integer unit) {
    this.unit = unit;
  }

  private UUID userId;

  private Integer alert;

  private UUID parentId;

  private UUID deviceId;

  private Date eventTime;

  private Long epochValue;

  private String data;

  private String eventCode;

  private UUID id;

  private int storageMode;

  private String value;

  private Integer eventCounter;

  private boolean isDeleted;

  private String eventClass;

  private UUID profileId;

  private Integer unit;

}
