package com.khanakirana.backend;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Created by vavasthi on 13/9/15.
 */

/**
 * Objectify service wrapper so we can statically register our persistence classes
 * More on Objectify here : https://code.google.com/p/objectify-appengine/pushd ~
 *
 *
 */
public class OfyService {

    static {
        ObjectifyService.register(UserAccount.class);
        ObjectifyService.register(UserAccountRegion.class);
        ObjectifyService.register(RegisteredBusiness.class);
        ObjectifyService.register(MasterItem.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
