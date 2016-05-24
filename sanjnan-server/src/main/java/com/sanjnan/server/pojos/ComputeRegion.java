/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.pojos;

import com.sanjnan.server.config.mapper.annotations.H2OUrlString;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * Created by vinay on 1/28/16.
 */
public class ComputeRegion extends Base {
    public ComputeRegion() {
    }

    public ComputeRegion(UUID id,
                         DateTime createdAt,
                         DateTime updatedAt,
                         String createdBy,
                         String updatedBy,
                         String name,
                         String endpointURL,
                         Long userCount) {

        super(id, createdAt, updatedAt, createdBy, updatedBy, name);
        this.endpointURL = endpointURL;
        this.userCount = userCount;
    }

    public ComputeRegion(String name,
                         String endpointURL,
                         Long userCount) {

        super(name);
        this.endpointURL = endpointURL;
        this.userCount = userCount;
    }

    public ComputeRegion(String name,
                         String endpointURL) {

        super(name);
        this.endpointURL = endpointURL;
        this.userCount = 0L;
    }

    public String getEndpointURL() {
        return endpointURL;
    }

    public void setEndpointURL(String endpointURL) {
        this.endpointURL = endpointURL;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    private @H2OUrlString
    String endpointURL;
    private Long userCount;
}
