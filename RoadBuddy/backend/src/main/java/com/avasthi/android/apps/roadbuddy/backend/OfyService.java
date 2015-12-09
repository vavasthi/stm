package com.avasthi.android.apps.roadbuddy.backend;

import com.avasthi.android.apps.roadbuddy.backend.bean.Amenity;
import com.avasthi.android.apps.roadbuddy.backend.bean.Checkpost;
import com.avasthi.android.apps.roadbuddy.backend.bean.City;
import com.avasthi.android.apps.roadbuddy.backend.bean.Establishment;
import com.avasthi.android.apps.roadbuddy.backend.bean.Event;
import com.avasthi.android.apps.roadbuddy.backend.bean.Group;
import com.avasthi.android.apps.roadbuddy.backend.bean.GroupMembership;
import com.avasthi.android.apps.roadbuddy.backend.bean.Member;
import com.avasthi.android.apps.roadbuddy.backend.bean.Rating;
import com.avasthi.android.apps.roadbuddy.backend.bean.RegistrationRecord;
import com.avasthi.android.apps.roadbuddy.backend.bean.SensorData;
import com.avasthi.android.apps.roadbuddy.backend.bean.Toll;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/
 */
public class OfyService {

    static {
        ObjectifyService.register(Member.class);
        ObjectifyService.register(City.class);
        ObjectifyService.register(Group.class);
        ObjectifyService.register(GroupMembership.class);
        ObjectifyService.register(Event.class);
        ObjectifyService.register(RegistrationRecord.class);
        ObjectifyService.register(Rating.class);
        ObjectifyService.register(Establishment.class);
        ObjectifyService.register(Amenity.class);
        ObjectifyService.register(SensorData.class);
        ObjectifyService.register(Toll.class);
        ObjectifyService.register(Checkpost.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
