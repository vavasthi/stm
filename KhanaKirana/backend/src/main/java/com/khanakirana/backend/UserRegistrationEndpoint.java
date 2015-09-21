/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.khanakirana.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.khanakirana.backend.exceptions.InvalidUserAccountException;
import com.khanakirana.backend.exceptions.MeasurementCategoryAlreadyExists;
import com.khanakirana.backend.exceptions.MeasurementCategoryDoesntExist;
import com.khanakirana.backend.exceptions.MeasurementPrimaryUnitException;

import java.sql.Blob;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "userRegistrationApi",
        version = "v1",
        scopes = {KKConstants.EMAIL_SCOPE},
        audiences = {KKConstants.ANDROID_CLIENT_ID},
        clientIds = {KKConstants.WEB_CLIENT_ID, KKConstants.ANDROID_CLIENT_ID},
        namespace = @ApiNamespace(
                ownerDomain = "backend.khanakirana.com",
                ownerName = "backend.khanakirana.com",
                packagePath = ""
        )
)
public class UserRegistrationEndpoint {

    private final static Logger logger = Logger.getLogger(UserRegistrationEndpoint.class.getName());

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "register")
    public UserAccount register(@Named("fullname") String fullname,
                                @Named("address") String address,
                                @Named("email") String email,
                                @Named("mobile") String mobile,
                                @Named("password") String password,
                                @Named("city") String city,
                                @Named("state") String state,
                                @Named("latitude") Double latitude,
                                @Named("longitude") Double longitude,
                                @Named("googleUser") Boolean googleUser) {
        UserAccount account = new UserAccount(fullname, address, email, mobile, password, city, state, longitude, latitude, googleUser);
        if (googleUser) {
            account.setPassword(null);
        }
        OfyService.ofy().save().entity(account).now();
        return account;
    }

    @ApiMethod(name = "authenticate")
    public UserAccount authenticate(@Named("email") String email,
                                    @Named("password") String password,
                                    @Named("googleUser") Boolean googleUser) {
        try {

            UserAccount account = OfyService.ofy().load().type(UserAccount.class).filter("email", email).first().now();
            logger.log(Level.INFO, "User Account retrieved." + account.toString());
            if (account == null) {

                throw new InvalidUserAccountException();
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown by the load.", e);
        }
        return null;
    }

    @ApiMethod(name = "insertSystemAdministrator")
    public UserAccount insertSystemAdministrator(@Named("email") String email,
                                                 @Named("mobile") String mobile) {
        UserAccount account = OfyService.ofy().load().type(UserAccount.class).filter("email", email).first().now();
        if (account != null) {
            account.setSystemAdministrator(Boolean.TRUE);
        }
        OfyService.ofy().save().entity(account);
        return account;
    }

    @ApiMethod(name = "supportedRegions")
    public List<UserAccountRegion> supportedRegions() {
        List<UserAccountRegion> regions = OfyService.ofy().load().type(UserAccountRegion.class).list();
        return regions;
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "isRegisteredUser")
    public UserAccount isRegisteredUser(@Named("email") String email) throws InvalidUserAccountException {

        try {

            UserAccount account = OfyService.ofy().load().type(UserAccount.class).filter("email", email).first().now();
            if (account == null) {

                throw new InvalidUserAccountException();
            }
            logger.log(Level.INFO, "User Account retrieved." + account.toString());
            return account;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown by the load.", e);
        }
        return null;
    }

    @ApiMethod(name = "addMasterItem")
    public MasterItem addMasterItem(@Named("name") String name,
                                    @Named("upc") String upc,
                                    @Named("imageType") String imageType,
                                    Blob image,
                                    @Named("user") String user,
                                    @Named("measurementCategory") String measurementCategory,
                                    @Named("unit") String unit) throws MeasurementCategoryAlreadyExists {

        MeasurementCategory mc = getMeasurementCategory(measurementCategory);
        MeasurementUnit mu = getMeasurementUnit(mc, unit);
        MasterItem item = new MasterItem(name, upc, imageType, image, user, mu);
        OfyService.ofy().save().entity(item).now();
        return item;
    }

    @ApiMethod(name = "addMeasurementCategory")
    public MeasurementCategory addMeasurementCategory(@Named("name") String name) throws MeasurementCategoryAlreadyExists {
        name = name.toUpperCase();
        return getMeasurementCategory(name);
    }

    @ApiMethod(name = "addMeasurementUnit")
    public MeasurementUnit addMeasurementUnit(@Named("name") String name,
                                              @Named("measurementCategory") String measurementCategory,
                                              @Named("primaryUnit") Boolean primaryUnit,
                                              @Named("factor") Double factor) throws MeasurementCategoryDoesntExist, MeasurementPrimaryUnitException, MeasurementCategoryAlreadyExists {

        name = name.toUpperCase();
        MeasurementCategory mc  = getMeasurementCategory(measurementCategory);
        if (primaryUnit) {
            factor = 1.0;
            if (OfyService.ofy().load().type(MeasurementUnit.class).filter("primaryUnit", Boolean.TRUE).filter("name", name).list().size() > 0) {
                throw MeasurementPrimaryUnitException.getExceptionForPrimaryUnitAlreadyExists(mc);
            }
        }
        else {

            if (OfyService.ofy().load().type(MeasurementUnit.class).filter("primaryUnit", Boolean.TRUE).filter("name", name).list().size() == 0) {
                throw MeasurementPrimaryUnitException.getExceptionForPrimaryUnitDoesntExist(mc);
            }
        }
        MeasurementUnit unit = new MeasurementUnit(name, mc, primaryUnit, factor);
        OfyService.ofy().save().entity(unit).now();
        return unit;
    }

    @ApiMethod(name = "getUnitsForCategory")
    public List<MeasurementUnit> getUnitsForCategory(@Named("measurementCategory") String measurementCategory) throws MeasurementCategoryAlreadyExists {
        MeasurementCategory mc = getMeasurementCategory(measurementCategory);
        return OfyService.ofy().load().type(MeasurementUnit.class).filter("measurementCategory", measurementCategory).list();
    }

    private MeasurementCategory getMeasurementCategory(String name) throws MeasurementCategoryAlreadyExists {

        name = name.toUpperCase();
        MeasurementCategory measurementCategory  = OfyService.ofy().load().type(MeasurementCategory.class).filter("name", name).first().now();
        if (measurementCategory != null) {
            throw new MeasurementCategoryAlreadyExists();
        }
        measurementCategory = new MeasurementCategory(name);
        OfyService.ofy().save().entity(measurementCategory).now();
        return measurementCategory;
    }
    private MeasurementUnit getMeasurementUnit(MeasurementCategory measurementCategory, String name) throws MeasurementCategoryAlreadyExists {

        name = name.toUpperCase();
        measurementCategory = new MeasurementCategory(name);
        MeasurementUnit measurementUnit = OfyService.ofy().load().type(MeasurementUnit.class).filter("measurementCategory", measurementCategory).filter("name", name).first().now();
        return measurementUnit;
    }
}
