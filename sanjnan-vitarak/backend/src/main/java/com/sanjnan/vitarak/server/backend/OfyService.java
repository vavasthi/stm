package com.sanjnan.vitarak.server.backend;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.sanjnan.vitarak.server.backend.entity.BusinessAdminAccount;
import com.sanjnan.vitarak.server.backend.entity.DeliveryTripEntity;
import com.sanjnan.vitarak.server.backend.entity.MasterItem;
import com.sanjnan.vitarak.server.backend.entity.MasterStockEntity;
import com.sanjnan.vitarak.server.backend.entity.MeasurementUnit;
import com.sanjnan.vitarak.server.backend.entity.SysadminAccount;
import com.sanjnan.vitarak.server.backend.entity.AccountAccount;
import com.sanjnan.vitarak.server.backend.entity.TaxCategoryEntity;
import com.sanjnan.vitarak.server.backend.entity.TaxSurchargeEntity;
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
        ObjectifyService.register(AccountAccount.class);
        ObjectifyService.register(BusinessAdminAccount.class);
        ObjectifyService.register(UserAccountRegion.class);

        ObjectifyService.register(SysadminAccount.class);
        ObjectifyService.register(MasterItem.class);
        ObjectifyService.register(MasterStockEntity.class);
        ObjectifyService.register(MeasurementUnit.class);
        ObjectifyService.register(MasterItem.class);

        ObjectifyService.register(TaxCategoryEntity.class);
        ObjectifyService.register(TaxSurchargeEntity.class);
        ObjectifyService.register(DeliveryTripEntity.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
