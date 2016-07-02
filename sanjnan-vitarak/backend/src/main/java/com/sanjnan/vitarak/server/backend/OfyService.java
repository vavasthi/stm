package com.sanjnan.vitarak.server.backend;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.sanjnan.vitarak.server.backend.entity.ItemCategory;
import com.sanjnan.vitarak.server.backend.entity.MasterItem;
import com.sanjnan.vitarak.server.backend.entity.MeasurementCategory;
import com.sanjnan.vitarak.server.backend.entity.MeasurementUnit;
import com.sanjnan.vitarak.server.backend.entity.BusinessAccount;
import com.sanjnan.vitarak.server.backend.entity.NotApprovedBusinessAccount;
import com.sanjnan.vitarak.server.backend.entity.SysadminAccount;
import com.sanjnan.vitarak.server.backend.entity.UserAccount;
import com.sanjnan.vitarak.server.backend.entity.UserAccountRegion;

/**
 * Created by vavasthi on 13/9/15.
 */

/**
 * Objectify service wrapper so we can statically registerCustomer our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/pushd ~
 *
 *
 */
public class OfyService {

    static {
        ObjectifyService.register(UserAccount.class);
        ObjectifyService.register(UserAccountRegion.class);
        ObjectifyService.register(BusinessAccount.class);
        ObjectifyService.register(SysadminAccount.class);
        ObjectifyService.register(MasterItem.class);
        ObjectifyService.register(MeasurementCategory.class);
        ObjectifyService.register(MeasurementUnit.class);
        ObjectifyService.register(MasterItem.class);
        ObjectifyService.register(ItemCategory.class);
        ObjectifyService.register(NotApprovedBusinessAccount.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
