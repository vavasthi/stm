/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.pojos;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Created by vinay on 1/28/16.
 */
public class SanjnanRole implements GrantedAuthority {
    public SanjnanRole(String authority) {
        this.authority = authority;
    }

    public SanjnanRole() {
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "SanjnanRole{" +
                "authority='" + authority + '\'' +
                '}';
    }

    private String authority;

    @Override
    @JsonGetter("role")
    public String getAuthority() {
        return authority;
    }
}
