package com.sanjnan.server.sanjnan.pojos;

import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by vinay on 3/4/16.
 */
public class DeviceRegistrationTempAuthToken extends Base {

  public DeviceRegistrationTempAuthToken(final String deviceRegistrationId, final String tempAuthToken, final DateTime expiry) {
    super(deviceRegistrationId);
    this.deviceRegistrationId = deviceRegistrationId;
    this.tempAuthToken = tempAuthToken;
    this.expiry = expiry;
  }
  public DeviceRegistrationTempAuthToken(UUID id,
                                         Account account,
                                         Device device,
                                         DateTime createdAt,
                                         DateTime updatedAt,
                                         String createdBy,
                                         String updatedBy,
                                         final String deviceRegistrationId,
                                         final String tempAuthToken,
                                         final DateTime expiry) {
    super(id, createdAt, updatedAt, createdBy, updatedBy, deviceRegistrationId);
    this.account = account;
    this.device = device;
    this.deviceRegistrationId = deviceRegistrationId;
    this.tempAuthToken = tempAuthToken;
    this.expiry = expiry;
  }

  public DeviceRegistrationTempAuthToken() {
  }

  public Account getAccount() {
    return account;
  }

  public void setAccount(final Account account) {
    this.account = account;
  }

  public Device getDevice() {
    return device;
  }

  public void setDevice(final Device device) {
    this.device = device;
  }

  public String getDeviceRegistrationId() {
    return deviceRegistrationId;
  }

  public void setDeviceRegistrationId(final String deviceRegistrationId) {
    this.deviceRegistrationId = deviceRegistrationId;
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

  private Account account;
  private Device device;
  private String deviceRegistrationId;
  private String tempAuthToken;
  private DateTime expiry;
}
