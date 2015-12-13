package com.avasthi.android.apps.roadbuddy.backend.bean;

import com.avasthi.android.apps.roadbuddy.backend.OfyService;
import com.avasthi.android.apps.roadbuddy.backend.fence.Fence;
import com.avasthi.android.apps.roadbuddy.backend.fence.FenceUtils;
import com.avasthi.roadbuddy.common.RBConstants;
import com.googlecode.objectify.annotation.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vavasthi on 9/12/15.
 */
@Entity
public class PointsOfInterest {
    public PointsOfInterest(List<Amenity> amenityList, List<Toll> tollList, List<Checkpost> checkpostList) {
        this.amenityList = amenityList;
        this.checkpostList = checkpostList;
        this.tollList = tollList;
    }

    public PointsOfInterest() {
    }

    public List<Amenity> getAmenityList() {
        return amenityList;
    }

    public void setAmenityList(List<Amenity> amenityList) {
        this.amenityList = amenityList;
    }

    public List<Checkpost> getCheckpostList() {
        return checkpostList;
    }

    public void setCheckpostList(List<Checkpost> checkpostList) {
        this.checkpostList = checkpostList;
    }

    public List<Toll> getTollList() {
        return tollList;
    }

    public void setTollList(List<Toll> tollList) {
        this.tollList = tollList;
    }

    private List<Amenity> amenityList;
    private List<Toll> tollList;
    private List<Checkpost> checkpostList;
}
