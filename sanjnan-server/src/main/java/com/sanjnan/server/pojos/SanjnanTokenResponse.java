package com.sanjnan.server.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sanjnan.server.security.token.SanjnanTokenPrincipal;
import com.sanjnan.server.serializers.H2ODateTimeDeserializer;
import com.sanjnan.server.serializers.H2ODateTimeSerializer;
import org.joda.time.DateTime;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by vinay on 2/3/16.
 */
public class SanjnanTokenResponse implements Serializable {

  @JsonProperty
  private String authToken;
  private SanjnanTokenPrincipal.TOKEN_TYPE tokenType;
  private String endpointURL;
  @JsonSerialize(using = H2ODateTimeSerializer.class)
  @JsonDeserialize(using = H2ODateTimeDeserializer.class)
  private DateTime expiry;
  private Collection<SanjnanRole> h2ORoles;

  public SanjnanTokenResponse() {
  }

  public SanjnanTokenResponse(String authToken,
                              SanjnanTokenPrincipal.TOKEN_TYPE tokenType,
                              String endpointURL,
                              DateTime expiry,
                              Collection<SanjnanRole> h2ORoles) throws DatatypeConfigurationException {
    this.authToken = authToken;
    this.tokenType = tokenType;
    this.endpointURL = endpointURL;
    this.h2ORoles = h2ORoles;
    this.expiry = expiry;
  }

  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }

  public SanjnanTokenPrincipal.TOKEN_TYPE getTokenType() {
    return tokenType;
  }

  public void setTokenType(final SanjnanTokenPrincipal.TOKEN_TYPE tokenType) {
    this.tokenType = tokenType;
  }

  public DateTime getExpiry() {
    return expiry;
  }

  public void setExpiry(DateTime expiry) {
    this.expiry = expiry;
  }

  public Collection<SanjnanRole> getH2ORoles() {
    return h2ORoles;
  }

  public void setH2ORoles(Collection<SanjnanRole> h2ORoles) {
    this.h2ORoles = h2ORoles;
  }

  public String getEndpointURL() {
    return endpointURL;
  }

  public void setEndpointURL(String endpointURL) {
    this.endpointURL = endpointURL;
  }
}