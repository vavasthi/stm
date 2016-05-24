/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.v6;

import com.google.common.base.Optional;
import com.sanjnan.server.exception.MismatchedCredentialHeaderAndAuthException;
import com.sanjnan.server.caching.AccountCacheService;
import com.sanjnan.server.caching.DeviceCacheService;
import com.sanjnan.server.caching.DeviceRegistrationTempAuthTokenCacheService;
import com.sanjnan.server.caching.TenantCacheService;
import com.sanjnan.server.security.token.SanjnanTokenPrincipal;
import com.sanjnan.server.service.H2OTokenService;
import com.sanjnan.server.util.H2OUtils;
import com.sanjnan.server.pojos.constants.H2OConstants;
import com.sanjnan.server.pojos.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Date;
import java.util.UUID;

/**
 * Created by vinay on 2/3/16.
 */
@RestController
public class AuthenticationEndpoint extends BaseEndpoint {

  @Autowired
  private HttpServletRequest request;
  @Autowired
  private HttpServletResponse response;
  @Autowired
  private H2OTokenService tokenService;
  @Autowired
  private DeviceRegistrationTempAuthTokenCacheService deviceRegistrationTempAuthTokenCacheService;
  @Autowired
  private TenantCacheService tenantCacheService;
  @Autowired
  private DeviceCacheService deviceCacheService;
  @Autowired
  private AccountCacheService accountCacheService;

  @Transactional
  @RequestMapping(value = H2OConstants.V6_AUTHENTICATE_URL, method = RequestMethod.POST)
  public String authenticate(@PathVariable("tenant") String tenant) {
    return "This is just for in-code-documentation purposes and Rest API reference documentation." +
        "Servlet will never get to this point as Http requests are processed by AuthenticationFilter." +
        "Nonetheless to authenticate Domain User POST request with X-Auth-Username and X-Auth-Password headers " +
        "is mandatory to this URL. If username and password are correct valid token will be returned (just json string in response) " +
        "This token must be present in X-Auth-Token header in all requests for all other URLs, including logout." +
        "Authentication can be issued multiple times and each call results in new ticket.";
  }

  @Transactional
  @RequestMapping(value = H2OConstants.V6_AUTHENTICATE_URL + "/refresh", method = RequestMethod.POST)
  public SanjnanTokenResponse refresh(@PathVariable("tenant") String tenant) {

    HttpServletRequest httpRequest = asHttp(request);
    Optional<String> tenantHeader = getOptionalHeader(httpRequest, H2OConstants.AUTH_TENANT_HEADER);
    Optional<String> token = getOptionalHeader(httpRequest, H2OConstants.AUTH_TOKEN_HEADER);
    Optional<String> tokenTypeStr = getOptionalHeader(httpRequest, H2OConstants.AUTH_TOKEN_TYPE_HEADER);
    Optional<String> remoteAddr = Optional.fromNullable(httpRequest.getRemoteAddr());
    Optional<String> applicationId = getOptionalHeader(httpRequest, H2OConstants.AUTH_APPLICATION_ID_HEADER);
    SanjnanTokenPrincipal.TOKEN_TYPE tokenType = H2OUtils.getTokenType(tokenTypeStr);
    try {

      SanjnanUsernameAndTokenResponse utResponse
          = tokenService.refresh(tenantHeader.get(), remoteAddr.get(), applicationId.get(), token.get(), tokenType);
      SanjnanTokenResponse tokenResponse
          = utResponse.getResponse();

      return tokenResponse;
    }
    catch(DatatypeConfigurationException ex) {
      throw new MismatchedCredentialHeaderAndAuthException("Datatype configuration error.");
    }
  }
  @Transactional
  @RequestMapping(value = H2OConstants.V6_AUTHENTICATE_URL + "/validate", method = RequestMethod.POST)
  public SanjnanTokenResponse validate(@PathVariable("tenant") String tenant) {

    HttpServletRequest httpRequest = asHttp(request);
    Optional<String> tenantHeader = getOptionalHeader(httpRequest, H2OConstants.AUTH_TENANT_HEADER);
    Optional<String> token = getOptionalHeader(httpRequest, H2OConstants.AUTH_TOKEN_HEADER);
    Optional<String> tokenTypeStr = getOptionalHeader(httpRequest, H2OConstants.AUTH_TOKEN_TYPE_HEADER);
    Optional<String> remoteAddr = Optional.fromNullable(httpRequest.getRemoteAddr());
    Optional<String> applicationId = getOptionalHeader(httpRequest, H2OConstants.AUTH_APPLICATION_ID_HEADER);
    SanjnanTokenPrincipal.TOKEN_TYPE tokenType = H2OUtils.getTokenType(tokenTypeStr);
    try {

      SanjnanUsernameAndTokenResponse utResponse
          = tokenService.contains(tenantHeader.get(), remoteAddr.get(), applicationId.get(), token.get(), tokenType);
      SanjnanTokenResponse tokenResponse
          = utResponse.getResponse();

      return tokenResponse;
    }
    catch(DatatypeConfigurationException ex) {
      throw new MismatchedCredentialHeaderAndAuthException("Datatype configuration error.");
    }
  }

  @Transactional
  @RequestMapping(value = H2OConstants.V6_AUTHENTICATE_URL + "/device", method = RequestMethod.POST)
  public SanjnanTokenResponse createDeviceAuthToken(@PathVariable("tenant") String tenant,
                                                    @RequestBody @Valid DeviceAuthentication deviceAuthentication) {

    HttpServletRequest httpRequest = asHttp(request);
    Optional<String> tenantHeader = getOptionalHeader(httpRequest, H2OConstants.AUTH_TENANT_HEADER);
    Optional<String> token = getOptionalHeader(httpRequest, H2OConstants.AUTH_TOKEN_HEADER);
    Optional<String> tokenTypeStr = getOptionalHeader(httpRequest, H2OConstants.AUTH_TOKEN_TYPE_HEADER);
    Optional<String> remoteAddr = Optional.fromNullable(httpRequest.getRemoteAddr());
    Optional<String> applicationId = getOptionalHeader(httpRequest, H2OConstants.AUTH_APPLICATION_ID_HEADER);
    SanjnanTokenPrincipal.TOKEN_TYPE tokenType = H2OUtils.getTokenType(tokenTypeStr);
    try {

      if (tokenType.equals(SanjnanTokenPrincipal.TOKEN_TYPE.TEMP_TOKEN)) {

        DeviceRegistrationTempAuthToken deviceRegistrationTempAuthToken
        = deviceRegistrationTempAuthTokenCacheService.findByTempAuthToken(token.get());
        deviceRegistrationTempAuthTokenCacheService.delete(deviceRegistrationTempAuthToken);
      }
      SanjnanUsernameAndTokenResponse utResponse
          = tokenService.assignAuthTokenToDevice(tenant,
          deviceAuthentication.getDeviceRegistrationId(),
          remoteAddr.get(),
          applicationId.get());
      SanjnanTokenResponse tokenResponse = utResponse.getResponse();
      return tokenResponse;
    }
    catch(DatatypeConfigurationException ex) {
      throw new MismatchedCredentialHeaderAndAuthException("Datatype configuration error.");
    }
  }

  @Transactional
  @RequestMapping(value = H2OConstants.V6_AUTHENTICATE_URL + "/tempAuth", method = RequestMethod.POST)
  public SanjnanTokenResponse createTempAuthToken(@PathVariable("tenant") String tenant,
                                                  @RequestBody @Valid DeviceAuthentication deviceAuthentication) {

    HttpServletRequest httpRequest = asHttp(request);
    Optional<String> tenantHeader = getOptionalHeader(httpRequest, H2OConstants.AUTH_TENANT_HEADER);
    Optional<String> token = getOptionalHeader(httpRequest, H2OConstants.AUTH_TOKEN_HEADER);
    Optional<String> tokenTypeStr = getOptionalHeader(httpRequest, H2OConstants.AUTH_TOKEN_TYPE_HEADER);
    Optional<String> remoteAddr = Optional.fromNullable(httpRequest.getRemoteAddr());
    Optional<String> applicationId = getOptionalHeader(httpRequest, H2OConstants.AUTH_APPLICATION_ID_HEADER);
    SanjnanTokenPrincipal.TOKEN_TYPE tokenType = H2OUtils.getTokenType(tokenTypeStr);
    try {

      Tenant t = tenantCacheService.findByDiscriminator(tenant);
      Account account = accountCacheService.findByTenantAndId(t, deviceAuthentication.getAccountId());
      Device device = deviceCacheService.findByDeviceRegistrationId(deviceAuthentication.getDeviceRegistrationId());
      DeviceRegistrationTempAuthToken tempAuthToken
          = deviceRegistrationTempAuthTokenCacheService.create(account, device);
      SanjnanUsernameAndTokenResponse utResponse
          = new SanjnanUsernameAndTokenResponse(t.getDiscriminator(),
          account.getName(),
          new SanjnanTokenResponse(tempAuthToken.getTempAuthToken(),
              SanjnanTokenPrincipal.TOKEN_TYPE.TEMP_TOKEN,
              account.getComputeRegion().getEndpointURL(),
                  new DateTime(new Date().getTime() + H2OConstants.HALF_HOUR * 1000), account.getH2ORoles()));
      SanjnanTokenResponse tokenResponse = utResponse.getResponse();
      return tokenResponse;
    }
    catch(DatatypeConfigurationException ex) {
      throw new MismatchedCredentialHeaderAndAuthException("Datatype configuration error.");
    }
  }

  @Transactional
  @RequestMapping(value = H2OConstants.V6_AUTHENTICATE_URL + "/{id}", method = RequestMethod.DELETE)
  public SanjnanTokenResponse deleteToken(@PathVariable("tenant") String tenant,
                                          @PathVariable("id") UUID id) {

    HttpServletRequest httpRequest = asHttp(request);
    Optional<String> tenantHeader = getOptionalHeader(httpRequest, H2OConstants.AUTH_TENANT_HEADER);
    Optional<String> token = getOptionalHeader(httpRequest, H2OConstants.AUTH_TOKEN_HEADER);
    Optional<String> tokenTypeStr = getOptionalHeader(httpRequest, H2OConstants.AUTH_TOKEN_TYPE_HEADER);
    Optional<String> remoteAddr = Optional.fromNullable(httpRequest.getRemoteAddr());
    Optional<String> applicationId = getOptionalHeader(httpRequest, H2OConstants.AUTH_APPLICATION_ID_HEADER);
    SanjnanTokenPrincipal.TOKEN_TYPE tokenType = H2OUtils.getTokenType(tokenTypeStr);
    try {

      SanjnanUsernameAndTokenResponse utResponse
          = tokenService.deleteToken(tenantHeader.get(), id, token.get(), tokenType);
      SanjnanTokenResponse tokenResponse
          = utResponse.getResponse();

      return tokenResponse;
    }
    catch(DatatypeConfigurationException ex) {
      throw new MismatchedCredentialHeaderAndAuthException("Datatype configuration error.");
    }
  }

  @Transactional
  @RequestMapping(value = H2OConstants.V6_AUTHENTICATE_URL + "/{id}/{token}", method = RequestMethod.DELETE)
  public SanjnanTokenResponse deleteToken(@PathVariable("tenant") String tenant,
                                          @PathVariable("id") UUID id,
                                          @PathVariable("token") String token) {

    HttpServletRequest httpRequest = asHttp(request);
    Optional<String> tenantHeader = getOptionalHeader(httpRequest, H2OConstants.AUTH_TENANT_HEADER);
    try {

      SanjnanUsernameAndTokenResponse utResponse
          = tokenService.deleteToken(tenantHeader.get(), token);
      SanjnanTokenResponse tokenResponse
          = utResponse.getResponse();

      return tokenResponse;
    }
    catch(DatatypeConfigurationException ex) {
      throw new MismatchedCredentialHeaderAndAuthException("Datatype configuration error.");
    }
  }
  private HttpServletRequest asHttp(ServletRequest request) {
    return (HttpServletRequest) request;
  }

  private HttpServletResponse asHttp(ServletResponse response) {
    return (HttpServletResponse) response;
  }

  private Optional<String>
  getOptionalHeader(HttpServletRequest httpRequest, String headerName) {
    return Optional.fromNullable(httpRequest.getHeader(headerName));
  }

}
