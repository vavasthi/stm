package com.sanjnan.server.sanjnan.service;

import com.sanjnan.server.exception.TokenExpiredException;
import com.sanjnan.server.exception.UnauthorizedException;
import com.sanjnan.server.sanjnan.caching.AccountCacheService;
import com.sanjnan.server.sanjnan.caching.DeviceCacheService;
import com.sanjnan.server.sanjnan.caching.DeviceRegistrationTempAuthTokenCacheService;
import com.sanjnan.server.sanjnan.caching.TenantCacheService;
import com.sanjnan.server.sanjnan.config.mapper.AccountMapper;
import com.sanjnan.server.sanjnan.config.mapper.DeviceMapper;
import com.sanjnan.server.sanjnan.config.mapper.RoleMapper;
import com.sanjnan.server.sanjnan.config.mapper.TenantMapper;
import com.sanjnan.server.sanjnan.dao.AccountDao;
import com.sanjnan.server.sanjnan.dao.DeviceDao;
import com.sanjnan.server.sanjnan.dao.SessionDao;
import com.sanjnan.server.sanjnan.dao.TenantDao;
import com.sanjnan.server.sanjnan.entity.DeviceEntity;
import com.sanjnan.server.sanjnan.entity.SessionEntity;
import com.sanjnan.server.sanjnan.pojos.*;
import com.sanjnan.server.sanjnan.security.token.SanjnanTokenPrincipal;
import com.sanjnan.server.pojos.SanjnanRole;
import org.eclipse.jetty.http.HttpStatus;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by vinay on 2/3/16.
 */
@Service
public class H2OTokenService  {

  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private AccountDao accountDao;
  @Autowired
  private TenantDao tenantDao;
  @Autowired
  private SessionDao sessionDao;
  @Autowired
  private DeviceDao deviceDao;
  @Autowired
  private TenantMapper tenantMapper;
  @Autowired
  private AccountMapper accountMapper;
  @Autowired
  private DeviceMapper deviceMapper;
  @Autowired
  private AccountCacheService accountCacheService;
  @Autowired
  private TenantCacheService tenantCacheService;
  @Autowired
  private DeviceRegistrationTempAuthTokenCacheService deviceRegistrationTempAuthTokenCacheService;
  @Autowired
  private DeviceCacheService deviceCacheService;
  /**
   * This method is called by the authentication filter when token based authentication is performed. This method
   * checks the token cache to find the appropriate token and validate the ip address from which the request had come
   * from. If the ip address could not be validated, the the user is asked to authenticate using username and password.
   *
   * If the token doesn't exist in the cache but is present in the database, then it is populated in the cache.
   *
   * @param tenantDiscriminator discriminator for the tenant
   * @param remoteAddr the ip address from which the incoming request came
   * @param authToken the auth token that needs to be verified.
   * @return token response object.
   * @throws DatatypeConfigurationException
   */
  public SanjnanUsernameAndTokenResponse contains(String tenantDiscriminator,
                                                  String remoteAddr,
                                                  String applicationId,
                                                  String authToken,
                                                  SanjnanTokenPrincipal.TOKEN_TYPE tokenType)
      throws DatatypeConfigurationException {

    if (tokenType == SanjnanTokenPrincipal.TOKEN_TYPE.APP_TOKEN) {
      return validateAppToken(tenantDiscriminator, remoteAddr, applicationId, authToken);
    }
    else if (tokenType == SanjnanTokenPrincipal.TOKEN_TYPE.TEMP_TOKEN) {
      return validateTempToken(tenantDiscriminator, remoteAddr, applicationId, authToken);
    }
    else if (tokenType == SanjnanTokenPrincipal.TOKEN_TYPE.DEVICE_TOKEN) {
      return validateDeviceToken(tenantDiscriminator, remoteAddr, applicationId, authToken);
    }
    throw new BadClientCredentialsException();
  }

  /**
   * This method is called when a token needs to be refreshed. The request is validated against the token that
   * is sent as part of the request, a new token is generated and returned.
   *
   *
   * @param tenantDiscriminator discriminator for the tenant
   * @param remoteAddr the ip address from which the incoming request came
   * @param authToken the auth token that needs to be verified.
   * @return token response object.
   * @throws DatatypeConfigurationException
   */
  public SanjnanUsernameAndTokenResponse refresh(String tenantDiscriminator,
                                                 String remoteAddr,
                                                 String applicationId,
                                                 String authToken,
                                                 SanjnanTokenPrincipal.TOKEN_TYPE tokenType)
      throws DatatypeConfigurationException {

    if (tokenType == SanjnanTokenPrincipal.TOKEN_TYPE.APP_TOKEN) {
      return refreshAppToken(tenantDiscriminator, remoteAddr, applicationId, authToken);
    }
    else if (tokenType == SanjnanTokenPrincipal.TOKEN_TYPE.DEVICE_TOKEN) {
      return refreshDeviceToken(tenantDiscriminator, remoteAddr, applicationId, authToken);
    }
    throw new BadClientCredentialsException();
  }

  private SanjnanUsernameAndTokenResponse validateAppToken(String tenantDiscriminator,
                                                           String remoteAddr,
                                                           String applicationId,
                                                           String authToken) throws DatatypeConfigurationException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account = accountCacheService.findByAuthToken(tenant, applicationId, authToken);
    if (account != null) {

      Session session = account.getSessionMap().get(applicationId);
      if (session == null) {
        throw new UnauthorizedException(String.format("Token %s doesn't belong to application %s", authToken, applicationId));
      }
      if (account.getSessionMap().get(applicationId).getExpiry().isBefore(new DateTime())) {
        throw new TokenExpiredException(HttpStatus.UNAUTHORIZED_401, authToken + " is expired.");
      }
      Set<String> remoteAddresses = account.getRemoteAddresses().stream().collect(Collectors.toSet());
      if (!remoteAddresses.contains(remoteAddr)) {

        throw new UnauthorizedException("Unknown IP address, please reauthenticate." + remoteAddr);
      }
      return new SanjnanUsernameAndTokenResponse(tenantDiscriminator,
          account.getName(),
          new SanjnanTokenResponse(authToken,
              SanjnanTokenPrincipal.TOKEN_TYPE.APP_TOKEN,
              account.getComputeRegion().getEndpointURL(),
              account.getSessionMap().get(applicationId).getExpiry(),
              account.getH2ORoles()));
    }
    throw new BadClientCredentialsException();
  }
  private SanjnanUsernameAndTokenResponse validateTempToken(String tenantDiscriminator,
                                                            String remoteAddr,
                                                            String applicationId,
                                                            String authToken) throws DatatypeConfigurationException {

    DeviceRegistrationTempAuthToken tempAuthToken
        = deviceRegistrationTempAuthTokenCacheService.findByTempAuthToken(authToken);
    if (tempAuthToken != null) {

      if (tempAuthToken.getExpiry().isBefore(new DateTime())) {
        throw new TokenExpiredException(HttpStatus.UNAUTHORIZED_401, authToken + " is expired.");
      }

      return new SanjnanUsernameAndTokenResponse(tenantDiscriminator,
          tempAuthToken.getAccount().getName(),
          new SanjnanTokenResponse(authToken,
              SanjnanTokenPrincipal.TOKEN_TYPE.TEMP_TOKEN,
              tempAuthToken.getAccount().getComputeRegion().getEndpointURL(),
              tempAuthToken.getAccount().getSessionMap().get(applicationId).getExpiry(),
              tempAuthToken.getAccount().getH2ORoles()));
    }
    throw new BadClientCredentialsException();
  }
  private SanjnanUsernameAndTokenResponse validateDeviceToken(String tenantDiscriminator,
                                                              String remoteAddr,
                                                              String applicationId,
                                                              String authToken) throws DatatypeConfigurationException {

    Device device = deviceCacheService.findByAuthToken(authToken);
    if (device != null) {

      if (device.getSession().getExpiry().isBefore(new DateTime())) {

        throw new TokenExpiredException(HttpStatus.UNAUTHORIZED_401, authToken + " is expired.");
      }
      Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
      Account account = accountCacheService.findByTenantAndId(tenant, device.getAccountId());
      return new SanjnanUsernameAndTokenResponse(tenantDiscriminator,
          account.getName(),
          new SanjnanTokenResponse(authToken,
              SanjnanTokenPrincipal.TOKEN_TYPE.TEMP_TOKEN,
              account.getComputeRegion().getEndpointURL(),
              device.getSession().getExpiry(),
              device.getH2ORoles()));
    }
    throw new BadClientCredentialsException();
  }
  private SanjnanUsernameAndTokenResponse refreshAppToken(String tenantDiscriminator,
                                                          String remoteAddr,
                                                          String applicationId,
                                                          String authToken) throws DatatypeConfigurationException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account = accountCacheService.findByAuthToken(tenant, applicationId, authToken);
    if (account != null) {

      accountCacheService.evictFromCache(account);
      Session session = account.getSessionMap().get(applicationId);
      if (session == null) {
        throw new UnauthorizedException(String.format("Token %s doesn't belong to application %s", authToken, applicationId));
      }
      return assignAuthTokenToUser(tenantDiscriminator, account.getName(), remoteAddr, applicationId);
    }
    throw new BadClientCredentialsException();
  }
  private SanjnanUsernameAndTokenResponse refreshDeviceToken(String tenantDiscriminator,
                                                             String remoteAddr,
                                                             String applicationId,
                                                             String authToken) throws DatatypeConfigurationException {

    Device device = deviceCacheService.findByAuthToken(authToken);
    if (device != null) {
      deviceCacheService.evictFromCache(device);
      return assignAuthTokenToDevice(tenantDiscriminator, device.getDeviceRegistrationId(), remoteAddr, applicationId);
    }
    throw new BadClientCredentialsException();
  }
  /**
   * This method generates an auth token for user and returns it. This method is called when a user performs authentication
   * using username and pasword. Usual validation including the validation of the ip address is also performed.
   *
   * @param tenantDiscriminator
   * @param username
   * @param remoteAddr
   * @param applicationId
   * @return
   * @throws DatatypeConfigurationException
   */
  @Transactional
  public SanjnanUsernameAndTokenResponse assignAuthTokenToUser(String tenantDiscriminator,
                                                               String username,
                                                               String remoteAddr,
                                                               String applicationId)
      throws DatatypeConfigurationException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account = accountCacheService.generateAuthToken(tenant, username, remoteAddr, applicationId);
    return new SanjnanUsernameAndTokenResponse(tenantDiscriminator,
        username,
        new SanjnanTokenResponse(account.getSessionMap().get(applicationId).getAuthToken(),
            SanjnanTokenPrincipal.TOKEN_TYPE.APP_TOKEN,
            account.getComputeRegion().getEndpointURL(),
            account.getSessionMap().get(applicationId).getExpiry(),
            account.getH2ORoles()));
  }
  @Transactional
  public SanjnanUsernameAndTokenResponse assignAuthTokenToDevice(String tenantDiscriminator,
                                                                 String deviceRegistrationId,
                                                                 String remoteAddr,
                                                                 String applicationId)
      throws DatatypeConfigurationException {

    Device device = deviceCacheService.findByDeviceRegistrationId(deviceRegistrationId);
    deviceCacheService.evictFromCache(device);
    DeviceEntity deviceEntity = deviceDao.findOne(device.getId());
    SessionEntity sessionEntity
            = new SessionEntity(deviceCacheService.generateAuthToken(deviceRegistrationId),
            remoteAddr,
            applicationId,
            deviceEntity,
            Session.SESSION_TYPE.DEVICE_SESSION.getIValue());
    sessionEntity = sessionDao.save(sessionEntity);
    deviceEntity.setSessionEntity(sessionEntity);
    device = deviceMapper.mapEntityIntoPojo(deviceEntity);
    deviceCacheService.evictFromCache(device);
    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account = accountCacheService.findByTenantAndId(tenant, device.getAccountId());
    return new SanjnanUsernameAndTokenResponse(tenantDiscriminator,
        account.getName(),
        new SanjnanTokenResponse(deviceEntity.getSessionEntity().getAuthToken(),
            SanjnanTokenPrincipal.TOKEN_TYPE.APP_TOKEN,
            deviceEntity.getAccountEntity().getComputeRegionEntity().getEndpointURL(),
            deviceEntity.getSessionEntity().getExpiry(),
            RoleMapper.mapEntitiesIntoDTOs(deviceEntity.getAccountEntity().getRoles())));
  }

  @Transactional
  public SanjnanUsernameAndTokenResponse deleteToken(String tenantDiscriminator,
                                                     UUID id,
                                                     String token,
                                                     SanjnanTokenPrincipal.TOKEN_TYPE tokenType)
      throws DatatypeConfigurationException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    if (tokenType == SanjnanTokenPrincipal.TOKEN_TYPE.APP_TOKEN) {

      Account account = accountCacheService.deleteToken(tenant, id);
      return new SanjnanUsernameAndTokenResponse(tenantDiscriminator,
          account.getName(),
          new SanjnanTokenResponse("",
              SanjnanTokenPrincipal.TOKEN_TYPE.UNKNOWN_TOKEN,
              account.getComputeRegion().getEndpointURL(),
              new DateTime(),
              account.getH2ORoles()));
    }
    else if (tokenType == SanjnanTokenPrincipal.TOKEN_TYPE.DEVICE_TOKEN) {

      Device device = deviceCacheService.deleteToken(id);
      Account account = accountCacheService.findByTenantAndId(tenant, device.getAccountId());
      return new SanjnanUsernameAndTokenResponse(tenantDiscriminator,
          account.getName(),
          new SanjnanTokenResponse("",
              SanjnanTokenPrincipal.TOKEN_TYPE.UNKNOWN_TOKEN,
              "",
              new DateTime(),
              new ArrayList<SanjnanRole>()));
    }
    else if (tokenType == SanjnanTokenPrincipal.TOKEN_TYPE.TEMP_TOKEN) {
      Device device = deviceCacheService.findOne(id);
      if (device != null) {

        DeviceRegistrationTempAuthToken deviceRegistrationTempAuthToken
            = deviceRegistrationTempAuthTokenCacheService.findByTempAuthToken(token);
        deviceRegistrationTempAuthTokenCacheService.delete(deviceRegistrationTempAuthToken);
        Account account = accountCacheService.findByTenantAndId(tenant, device.getAccountId());
        return new SanjnanUsernameAndTokenResponse(tenantDiscriminator,
            account.getName(),
            new SanjnanTokenResponse("",
                SanjnanTokenPrincipal.TOKEN_TYPE.UNKNOWN_TOKEN,
                "",
                new DateTime(),
                new ArrayList<SanjnanRole>()));
      }
    }
    throw new BadClientCredentialsException();
  }

  @Transactional
  public SanjnanUsernameAndTokenResponse deleteToken(String tenantDiscriminator,
                                                     String token)
      throws DatatypeConfigurationException {

    Tenant tenant = tenantCacheService.findByDiscriminator(tenantDiscriminator);
    Account account = accountCacheService.deleteToken(tenant, token);
    return new SanjnanUsernameAndTokenResponse(tenantDiscriminator,
        account.getName(),
        new SanjnanTokenResponse("",
            SanjnanTokenPrincipal.TOKEN_TYPE.UNKNOWN_TOKEN,
            account.getComputeRegion().getEndpointURL(),
            new DateTime(),
            account.getH2ORoles()));
  }
}
