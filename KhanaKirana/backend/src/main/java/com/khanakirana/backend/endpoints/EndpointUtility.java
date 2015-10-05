package com.khanakirana.backend.endpoints;

import com.khanakirana.backend.OfyService;
import com.khanakirana.backend.entity.MeasurementCategory;
import com.khanakirana.backend.entity.MeasurementUnit;
import com.khanakirana.backend.entity.UserAccount;
import com.khanakirana.backend.exceptions.InvalidUserAccountException;
import com.khanakirana.backend.exceptions.MeasurementCategoryDoesntExist;

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

    public static UserAccount getUserAccount(String email) throws MeasurementCategoryDoesntExist, InvalidUserAccountException {

        email = email.toLowerCase();
        UserAccount userAccount  = OfyService.ofy().load().type(UserAccount.class).filter("email", email).first().now();
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
