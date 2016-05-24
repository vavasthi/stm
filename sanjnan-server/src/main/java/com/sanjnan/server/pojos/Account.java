package com.sanjnan.server.pojos;

import org.joda.time.DateTime;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by vinay on 1/28/16.
 */
public class Account extends Base {
  public Account() {
  }

  public Account(UUID id,
                 Tenant tenant,
                 DateTime createdAt,
                 DateTime updatedAt,
                 String createdBy,
                 String updatedBy,
                 String name,
                 String email,
                 String password,
                 Map<String, Session> sessionMap,
                 Set<String> remoteAddresses,
                 Set<SanjnanRole> h2ORoles,
                 Set<Device> devices,
                 ComputeRegion computeRegion) {
    super(id, createdAt, updatedAt, createdBy, updatedBy, name);
    this.tenant = tenant;
    this.email = email;
    this.password = password;
    this.sessionMap = sessionMap;
    this.remoteAddresses = remoteAddresses;
    this.h2ORoles = h2ORoles;
    this.devices = devices;
    this.computeRegion = computeRegion;
  }

  public Account(Tenant tenant,
                 String name,
                 String email,
                 String password,
                 Set<SanjnanRole> h2ORoles,
                 ComputeRegion computeRegion) {
    super(name);
    this.tenant = tenant;
    this.email = email;
    this.password = password;
    this.h2ORoles = h2ORoles;
    this.computeRegion = computeRegion;
  }

  public Account(Tenant tenant,
                 String name,
                 String email,
                 String password,
                 Set<SanjnanRole> h2ORoles) {
    super(name);
    this.tenant = tenant;
    this.email = email;
    this.password = password;
    this.h2ORoles = h2ORoles;
  }

  public Tenant getTenant() {
    return tenant;
  }

  public void setTenant(Tenant tenant) {
    this.tenant = tenant;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<SanjnanRole> getH2ORoles() {
    return h2ORoles;
  }

  public void setH2ORoles(Set<SanjnanRole> h2ORoles) {
    this.h2ORoles = h2ORoles;
  }

  public Set<String> getRemoteAddresses() {
    return remoteAddresses;
  }

  public void setRemoteAddresses(Set<String> remoteAddresses) {
    this.remoteAddresses = remoteAddresses;
  }

  public Map<String, Session> getSessionMap() {
    return sessionMap;
  }

  public void setSessionMap(Map<String, Session> sessionMap) {
    this.sessionMap = sessionMap;
  }

  public Set<Device> getDevices() {
    return devices;
  }

  public void setDevices(final Set<Device> devices) {
    this.devices = devices;
  }

  public ComputeRegion getComputeRegion() {
    return computeRegion;
  }

  public void setComputeRegion(ComputeRegion computeRegion) {
    this.computeRegion = computeRegion;
  }

  @Override
  public boolean equals(final Object o) {
    if (!super.equals(o)) {
      return false;
    }
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final Account account = (Account) o;

    if (tenant != null ? !tenant.equals(account.tenant) : account.tenant != null) return false;
    return email != null ? email.equals(account.email) : account.email == null;

  }

  @Override
  public int hashCode() {
    int result = tenant != null ? tenant.hashCode() : 0;
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Account{" +
        "tenant=" + tenant +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", h2ORoles=" + h2ORoles +
        ", remoteAddresses=" + remoteAddresses +
        ", sessionMap=" + sessionMap +
        ", devices=" + devices +
        ", computeRegion=" + computeRegion +
        "} " + super.toString();
  }

  private Tenant tenant;
  private String email;
  private String password;
  private Set<SanjnanRole> h2ORoles;
  private Set<String> remoteAddresses;
  private Map<String, Session> sessionMap;
  private Set<Device> devices;
  private ComputeRegion computeRegion;
}
