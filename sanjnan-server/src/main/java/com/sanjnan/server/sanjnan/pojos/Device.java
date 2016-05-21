package com.sanjnan.server.sanjnan.pojos;

import com.sanjnan.server.pojos.SanjnanRole;
import org.joda.time.DateTime;

import java.util.Set;
import java.util.UUID;

/**
 * Created by vinay on 3/7/16.
 */
public class Device extends Base {

  public Device(UUID id,
                DateTime createdAt,
                DateTime updatedAt,
                String createdBy,
                String updatedBy,
                String deviceRegistrationId,
                Set<SanjnanRole> h2ORoles) {
    super(id, createdAt,  updatedAt,  createdBy,  updatedBy,  deviceRegistrationId);
    this.deviceRegistrationId = deviceRegistrationId;
    this.h2ORoles = h2ORoles;
  }
  public Device(String deviceRegistrationId,
                Set<SanjnanRole> h2ORoles) {
    super(null, null,  null,  null,  null,  deviceRegistrationId);
    this.deviceRegistrationId = deviceRegistrationId;
    this.h2ORoles = h2ORoles;
  }

  public Device() {

  }
  public String getDeviceRegistrationId() {
    return deviceRegistrationId;
  }

  public void setDeviceRegistrationId(final String deviceRegistrationId) {
    this.deviceRegistrationId = deviceRegistrationId;
  }

  public Set<SanjnanRole> getH2ORoles() {
    return h2ORoles;
  }

  public void setH2ORoles(final Set<SanjnanRole> h2ORoles) {
    this.h2ORoles = h2ORoles;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(final Session session) {
    this.session = session;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public void setAccountId(final UUID accountId) {
    this.accountId = accountId;
  }

  @Override
  public boolean equals(final Object o) {
    if (!super.equals(o)) {
      return false;
    }
    if (this == o) return true;
    if (!(o instanceof Device)) return false;
    if (!super.equals(o)) return false;

    final Device device = (Device) o;

    if (deviceRegistrationId != null ? !deviceRegistrationId.equals(device.deviceRegistrationId) : device.deviceRegistrationId != null)
      return false;
    return accountId != null ? accountId.equals(device.accountId) : device.accountId == null;

  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (deviceRegistrationId != null ? deviceRegistrationId.hashCode() : 0);
    result = 31 * result + (accountId != null ? accountId.hashCode() : 0);
    return result;
  }

  private String deviceRegistrationId;
  private Set<SanjnanRole> h2ORoles;
  private Session session;
  private UUID accountId;
}
