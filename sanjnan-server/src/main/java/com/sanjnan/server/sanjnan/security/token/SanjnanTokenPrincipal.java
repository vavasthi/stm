package com.sanjnan.server.sanjnan.security.token;


import com.google.common.base.Optional;

/**
 * Created by vinay on 2/3/16.
 */
public class SanjnanTokenPrincipal extends SanjnanPrincipal {


  public enum TOKEN_TYPE {
    UNKNOWN_TOKEN("unknown_token"),
    APP_TOKEN("app_token"),
    DEVICE_TOKEN("device_token"),
    TEMP_TOKEN("temp_token");

    private final String value;

    TOKEN_TYPE(String value) {
      this.value = value;
    }

    public static TOKEN_TYPE createFromString(String value) {
      for (TOKEN_TYPE t : values()) {
        if (t.value.equalsIgnoreCase(value)) {
          return t;
        }
      }
      return UNKNOWN_TOKEN;
    }
    public String getValue() {
      return value;
    }
    @Override
    public String toString() {
      return value;
    }
  }
  public SanjnanTokenPrincipal(Optional<String> remoteAddr,
                               Optional<String> applicationId,
                               Optional<String> tenant,
                               String name,
                               Optional<String> token,
                               TOKEN_TYPE tokenType) {
    super(remoteAddr, applicationId, tenant, Optional.fromNullable(name));
    this.token = token;
    this.tokenType = tokenType;
  }

  public Optional<String> getToken() {
    return token;
  }

  public void setToken(Optional<String> token) {
    this.token = token;
  }


  public boolean isValid() {
    return super.isValid() && validField(token);
  }

  public TOKEN_TYPE getTokenType() {
    return tokenType;
  }

  public void setTokenType(final TOKEN_TYPE tokenType) {
    this.tokenType = tokenType;
  }

  @Override
  public String toString() {
    return "SanjnanTokenPrincipal{" +
        "token=" + token +
        ", tokenType=" + tokenType +
        "} " + super.toString();
  }

  private Optional<String> token;
  private TOKEN_TYPE tokenType;
}
