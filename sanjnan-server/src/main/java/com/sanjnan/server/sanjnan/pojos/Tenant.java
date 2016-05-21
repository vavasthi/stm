package com.sanjnan.server.sanjnan.pojos;

import org.joda.time.DateTime;

import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/28/16.
 */
public class Tenant extends Base {
  public Tenant() {
  }

  public Tenant(UUID id,
                DateTime createdAt,
                DateTime updatedAt,
                String createdBy,
                String updatedBy,
                String name,
                String email,
                String discriminator,
                List<UUID> computeRegions) {
    super(id, createdAt, updatedAt, createdBy, updatedBy, name);
    this.email = email;
    this.discriminator = discriminator;
    this.computeRegions = computeRegions;
  }

  public Tenant(String name,
                String email,
                String discriminator,
                List<UUID> computeRegions) {
    super(name);
    this.email = email;
    this.discriminator = discriminator;
    this.computeRegions = computeRegions;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDiscriminator() {
    return discriminator;
  }

  public void setDiscriminator(String discriminator) {
    this.discriminator = discriminator;
  }

  public List<UUID> getComputeRegions() {
    return computeRegions;
  }

  public void setComputeRegions(List<UUID> computeRegions) {
    this.computeRegions = computeRegions;
  }

  @Override
  public String toString() {
    return "Tenant{" +
        "email='" + email + '\'' +
        ", discriminator='" + discriminator + '\'' +
        ", computeRegions=" + computeRegions +
        "} " + super.toString();
  }

  private String email;
  private String discriminator;
  private List<UUID> computeRegions;
}
