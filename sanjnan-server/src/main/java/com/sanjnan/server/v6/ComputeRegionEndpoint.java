/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.v6;

import com.sanjnan.server.exception.PatchingException;
import com.sanjnan.server.pojos.ComputeRegion;
import com.sanjnan.server.service.ComputeRegionService;
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
@RequestMapping(H2OConstants.V6_COMPUTE_REGION_ENDPOINT)
public class ComputeRegionEndpoint extends BaseEndpoint {
  private final ComputeRegionService computeRegionService;

  @Autowired
  public ComputeRegionEndpoint(ComputeRegionService computeRegionService) {
    this.computeRegionService = computeRegionService;
  }

  @Transactional(readOnly = true)
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  List<ComputeRegion> listComputeRegions() {
    return computeRegionService.listComputeRegions();
  }

  @Transactional(readOnly = true)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public ComputeRegion getComputeRegion(@PathVariable("id") UUID id) throws IOException {

    return computeRegionService.getComputeRegion(id);
  }

  @Transactional
  @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public ComputeRegion createComputeRegion(@RequestBody @Valid ComputeRegion computeRegion) {
    return computeRegionService.createComputeRegion(computeRegion);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
  public ComputeRegion updateComputeRegion(@PathVariable("id") UUID id,
                             @RequestBody @Valid ComputeRegion computeRegion) throws IllegalAccessException, PatchingException,
      InvocationTargetException {
    return computeRegionService.updateRegion(id, computeRegion);
  }

  @Transactional
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ComputeRegion deleteComputeRegion(@PathVariable("id") UUID id) throws IllegalAccessException, PatchingException,
      InvocationTargetException {
    return computeRegionService.deleteComputeRegion(id);
  }
}
