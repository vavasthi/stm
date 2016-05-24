package com.sanjnan.server.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by vinay on 1/28/16.
 */
@Entity
@Table(name = "tenants",
       indexes = {
               @Index(name = "tenants_discriminator_index",  columnList="discriminator", unique = true),
               @Index(name = "tenants_email_index",  columnList="email", unique = true)
       }
)
public class TenantEntity extends BaseEntity {

    public TenantEntity(String name,
                        String email,
                        String discriminator,
                        Set<ComputeRegionEntity> computeRegions) {
        super(name);
        this.email = email;
        this.discriminator = discriminator;
        this.computeRegions = computeRegions;
    }

    public TenantEntity() {
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

    public Set<ComputeRegionEntity> getComputeRegions() {
        return computeRegions;
    }

    public void setComputeRegions(Set<ComputeRegionEntity> computeRegions) {
        this.computeRegions = computeRegions;
    }

    public Set<SessionOwnerEntity> getSessionOwners() {
        return sessionOwners;
    }

    public void setSessionOwners(Set<SessionOwnerEntity> sessionOwners) {
        this.sessionOwners = sessionOwners;
    }

    public synchronized ComputeRegionEntity getNextComputeRegionEntity() {
        ComputeRegionEntity lowestCre = null;
        for (ComputeRegionEntity cre : computeRegions) {
            if (lowestCre == null || lowestCre.getUserCount() < cre.getUserCount()) {
                lowestCre = cre;
                break;
            }
        }
        if (lowestCre != null) {
            lowestCre.incrementUserCount();
        }
        return lowestCre;
    }

    private String email;
    private String discriminator;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ComputeRegionEntity> computeRegions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "tenant")
    private Set<SessionOwnerEntity> sessionOwners;
}
