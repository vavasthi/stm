package com.khanakirana.backend;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.khanakirana.backend.entity.ItemCategory;
import com.khanakirana.backend.entity.MasterItem;
import com.khanakirana.backend.entity.MeasurementCategory;
import com.khanakirana.backend.entity.MeasurementUnit;
import com.khanakirana.backend.entity.BusinessAccount;
import com.khanakirana.backend.entity.UserAccount;
import com.khanakirana.backend.entity.UserAccountRegion;

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
        ObjectifyService.register(MasterItem.class);
        ObjectifyService.register(MeasurementCategory.class);
        ObjectifyService.register(MeasurementUnit.class);
        ObjectifyService.register(MasterItem.class);
        ObjectifyService.register(ItemCategory.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
