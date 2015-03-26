package com.avasthi.android.apps.roadbuddy.backend.server;

import com.avasthi.android.apps.roadbuddy.backend.bean.RoadMeasurementBean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

/**
 * Created by vavasthi on 26/3/15.
 */
@Api(name="roadMeasurementApi",version="v1", description="An API to road measurement datapoints")
public class RoadMeasurementAPI {

    @ApiMethod(name = "addMeasurement")
    public void addMeasurement(RoadMeasurementBean measurement) {

    }
}
