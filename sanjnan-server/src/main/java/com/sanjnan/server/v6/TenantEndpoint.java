/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.v6;

import com.sanjnan.server.exception.PatchingException;
import com.sanjnan.server.pojos.Tenant;
import com.sanjnan.server.service.TenantService;
import com.sanjnan.server.pojos.constants.H2OConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

/**
 * Created by vinay on 1/4/16.
 */
@RestController
@RequestMapping(H2OConstants.V6_TENANTS_ENDPOINT)
public class TenantEndpoint extends BaseEndpoint {

  private final TenantService tenantService;

  @Autowired
  public TenantEndpoint(TenantService tenantService) {
    this.tenantService = tenantService;
  }

  @Transactional(readOnly = true)
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  List<Tenant> listTenants() {
    return tenantService.listTenants();
  }

  @Transactional(readOnly = true)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Tenant getTenant(@PathVariable("id") UUID id) throws IOException {

    return tenantService.getTenant(id);
  }

  @Transactional
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public Tenant createTenant(@RequestBody @Valid Tenant tenant) {
    return tenantService.createTenant(tenant);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public Tenant updateTenant(@PathVariable("id") UUID id,
                             @RequestBody @Valid Tenant tenant) throws IllegalAccessException, PatchingException, InvocationTargetException {
    return tenantService.updateTenant(id, tenant);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Tenant deleteTenant(@PathVariable("id") UUID id) throws IllegalAccessException, PatchingException, InvocationTargetException {
    return tenantService.deleteTenant(id);
  }
}
