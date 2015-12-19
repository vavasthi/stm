package com.avasthi.android.apps.roadbuddy.backend;

import com.avasthi.android.apps.roadbuddy.backend.bean.AbstractEntity;
import com.avasthi.android.apps.roadbuddy.backend.bean.AbstractStop;
import com.avasthi.android.apps.roadbuddy.backend.bean.Amenity;
import com.avasthi.android.apps.roadbuddy.backend.bean.AmenityStop;
import com.avasthi.android.apps.roadbuddy.backend.bean.Checkpost;
import com.avasthi.android.apps.roadbuddy.backend.bean.CheckpostStop;
import com.avasthi.android.apps.roadbuddy.backend.bean.City;
import com.avasthi.android.apps.roadbuddy.backend.bean.Drive;
import com.avasthi.android.apps.roadbuddy.backend.bean.DriveParameters;
import com.avasthi.android.apps.roadbuddy.backend.bean.Establishment;
import com.avasthi.android.apps.roadbuddy.backend.bean.Event;
import com.avasthi.android.apps.roadbuddy.backend.bean.Fence;
import com.avasthi.android.apps.roadbuddy.backend.bean.Group;
import com.avasthi.android.apps.roadbuddy.backend.bean.GroupMembership;
import com.avasthi.android.apps.roadbuddy.backend.bean.Member;
import com.avasthi.android.apps.roadbuddy.backend.bean.RegistrationRecord;
import com.avasthi.android.apps.roadbuddy.backend.bean.SensorData;
import com.avasthi.android.apps.roadbuddy.backend.bean.Toll;
import com.avasthi.android.apps.roadbuddy.backend.bean.TollStop;
import com.avasthi.android.apps.roadbuddy.backend.bean.Vehicle;
import com.avasthi.android.apps.roadbuddy.backend.bean.VehicleOwnership;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 */
public class OfyService {

    static {
        ObjectifyService.register(AbstractEntity.class);
        ObjectifyService.register(AbstractStop.class);
        ObjectifyService.register(Member.class);
        ObjectifyService.register(City.class);
        ObjectifyService.register(Group.class);
        ObjectifyService.register(GroupMembership.class);
        ObjectifyService.register(Event.class);
        ObjectifyService.register(RegistrationRecord.class);
        ObjectifyService.register(Establishment.class);
        ObjectifyService.register(Amenity.class);
        ObjectifyService.register(SensorData.class);
        ObjectifyService.register(Toll.class);
        ObjectifyService.register(Checkpost.class);
        ObjectifyService.register(AmenityStop.class);
        ObjectifyService.register(TollStop.class);
        ObjectifyService.register(CheckpostStop.class);
        ObjectifyService.register(Vehicle.class);
        ObjectifyService.register(VehicleOwnership.class);
        ObjectifyService.register(Drive.class);
        ObjectifyService.register(DriveParameters.class);
        ObjectifyService.register(Fence.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
