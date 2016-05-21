/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.util;

import com.google.common.base.Optional;
import com.sanjnan.server.exception.MismatchedCredentialHeaderAndAuthException;
import com.sanjnan.server.sanjnan.config.mapper.TenantMapper;
import com.sanjnan.server.sanjnan.dao.TenantDao;
import com.sanjnan.server.sanjnan.entity.TenantEntity;
import com.sanjnan.server.sanjnan.pojos.Tenant;
import com.sanjnan.server.sanjnan.security.token.SanjnanTokenPrincipal;
import com.sanjnan.server.pojos.constants.H2OConstants;
import org.springframework.cache.CacheManager;

import java.util.List;

/**
 * Created by vinay on 2/12/16.
 */
public class H2OUtils {


  public static String getTenantKeyFromDiscriminator(String discriminator) {
    return "<DiSCRIMINATOR>" + discriminator + "</DiSCRIMINATOR>";
  }

  public static String getAccountKeyFromAuthToken(String authToken) {
    return "<AUTH_TOKEN>" + authToken + "</AUTH_TOKEN>";
  }

  public static SanjnanTokenPrincipal.TOKEN_TYPE getTokenType(Optional<String> tokenTypeStr) {

    SanjnanTokenPrincipal.TOKEN_TYPE tokenType = SanjnanTokenPrincipal.TOKEN_TYPE.UNKNOWN_TOKEN;
    if (tokenTypeStr.isPresent()) {
      tokenType = SanjnanTokenPrincipal.TOKEN_TYPE.createFromString(tokenTypeStr.get());
    }
    return tokenType;
  }
  public static Tenant getTenant(CacheManager cacheManager,
                                 TenantMapper tenantMapper,
                                 TenantDao tenantDao,
                                 String tenantDiscriminator) {

    Tenant tenant
            = cacheManager.getCache(H2OConstants.H2O_TENANT_CACHE_NAME).
        get(H2OUtils.getTenantKeyFromDiscriminator(tenantDiscriminator), Tenant.class);
    TenantEntity te = null;
    if (tenant != null) {

      return tenant;
    } else {
      List<TenantEntity> tenantEntityList = tenantDao.findByDiscriminator(tenantDiscriminator);
      if (tenantEntityList != null && tenantEntityList.size() > 0) {
        te = tenantEntityList.get(0);
        tenant = tenantMapper.mapEntityIntoPojo(te);
        cacheManager.getCache(H2OConstants.H2O_TENANT_CACHE_NAME).put(te.getId(), tenant);
        cacheManager.getCache(H2OConstants.H2O_TENANT_CACHE_NAME).put(te.getDiscriminator(), tenant);
        return tenant;
      } else {
        throw new MismatchedCredentialHeaderAndAuthException(tenantDiscriminator + " tenant not found.");
      }
    }
  }

}
