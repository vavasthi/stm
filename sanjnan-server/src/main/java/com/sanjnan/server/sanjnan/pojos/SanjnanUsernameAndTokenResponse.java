/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.pojos;

import java.io.Serializable;

/**
 * Created by vinay on 2/11/16.
 */
public class SanjnanUsernameAndTokenResponse implements Serializable {
  public SanjnanUsernameAndTokenResponse(String tenant, String username, SanjnanTokenResponse response) {
    this.username = username;
    this.tenant = tenant;
    this.response = response;
  }

  public SanjnanUsernameAndTokenResponse() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getTenant() {
    return tenant;
  }

  public void setTenant(String tenant) {
    this.tenant = tenant;
  }

  public SanjnanTokenResponse getResponse() {
    return response;
  }

  public void setResponse(SanjnanTokenResponse response) {
    this.response = response;
  }

  private String username;
  private String tenant;
  private SanjnanTokenResponse response;

}
