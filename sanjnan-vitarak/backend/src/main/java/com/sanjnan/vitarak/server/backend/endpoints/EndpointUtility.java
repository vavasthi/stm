package com.sanjnan.vitarak.server.backend.endpoints;

import com.sanjnan.vitarak.server.backend.OfyService;
import com.sanjnan.vitarak.server.backend.entity.EstablishmentAccount;
import com.sanjnan.vitarak.server.backend.entity.MeasurementCategory;
import com.sanjnan.vitarak.server.backend.entity.MeasurementUnit;
import com.sanjnan.vitarak.server.backend.exceptions.InvalidUserAccountException;
import com.sanjnan.vitarak.server.backend.exceptions.MeasurementCategoryDoesntExist;

/**
 * Created by vavasthi on 5/10/15.
 */
public class EndpointUtility {

    public static MeasurementCategory getMeasurementCategory(String name) throws MeasurementCategoryDoesntExist {

        name = name.toUpperCase();
        MeasurementCategory measurementCategory  = OfyService.ofy().load().type(MeasurementCategory.class).filter("name", name).first().now();
        if (measurementCategory == null) {
            throw new MeasurementCategoryDoesntExist();
        }
        return measurementCategory;
    }

    public static EstablishmentAccount getUserAccount(String email) throws MeasurementCategoryDoesntExist, InvalidUserAccountException {

        email = email.toLowerCase();
        EstablishmentAccount userAccount  = OfyService.ofy().load().type(EstablishmentAccount.class).filter("email", email).first().now();
        if (userAccount == null) {
            throw new InvalidUserAccountException();
        }
        return userAccount;
    }
    private MeasurementUnit getMeasurementUnit(MeasurementCategory measurementCategory, String name) {

        name = name.toUpperCase();
        MeasurementUnit measurementUnit = OfyService.ofy().load().type(MeasurementUnit.class).filter("measurementCategoryId", measurementCategory.getId()).filter("name", name).first().now();
        return measurementUnit;
    }

}
