/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.v6;

import com.sanjnan.server.pojos.Device;
import com.sanjnan.server.service.DeviceService;
import com.sanjnan.server.pojos.constants.H2OConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

/**
 * Created by vinay on 1/4/16.
 */
@RestController
@RequestMapping(H2OConstants.V6_DEVICES_ENDPOINT)
public class DeviceEndpoint extends BaseEndpoint {

  private final DeviceService deviceService;

  @Autowired
  public DeviceEndpoint(DeviceService deviceService) {
    this.deviceService = deviceService;
  }

  @Transactional(readOnly = true)
  @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  Set<Device> listDevices() {
    return deviceService.list();
  }

  @Transactional(readOnly = true)
  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public Device getDevice(@PathVariable("id") UUID id) throws IOException {

    return deviceService.findOne(id);
  }

}
