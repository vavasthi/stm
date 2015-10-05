/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.khanakirana.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.khanakirana.backend.utils.KKConstants;
import com.khanakirana.backend.OfyService;
import com.khanakirana.backend.entity.ItemCategory;
import com.khanakirana.backend.entity.MeasurementCategory;
import com.khanakirana.backend.entity.MeasurementUnit;
import com.khanakirana.backend.entity.UserAccount;
import com.khanakirana.backend.entity.UserAccountRegion;
import com.khanakirana.backend.exceptions.InvalidUserAccountException;
import com.khanakirana.backend.exceptions.MeasurementCategoryAlreadyExists;
import com.khanakirana.backend.exceptions.MeasurementCategoryDoesntExist;
import com.khanakirana.backend.exceptions.MeasurementPrimaryUnitException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "customerApi",
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
public class CustomerEndpoint {

    private final static Logger logger = Logger.getLogger(CustomerEndpoint.class.getName());

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "register")
    public UserAccount register(@Named("name") String name,
                                        @Named("address") String address,
                                        @Named("email") String email,
                                        @Named("mobile") String mobile,
                                        @Named("password") String password,
                                        @Named("city") String city,
                                        @Named("state") String state,
                                        @Named("latitude") Double latitude,
                                        @Named("longitude") Double longitude,
                                        @Named("googleUser") Boolean googleUser) {

        UserAccount account = new UserAccount(name, address, email.toLowerCase(), mobile, password, city, state, longitude, latitude, googleUser);
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
            email = email.toLowerCase();
            UserAccount account = OfyService.ofy().load().type(UserAccount.class).filter("email", email).first().now();
            logger.log(Level.INFO, "User Account retrieved." + account.toString());
            if (account == null) {

                throw new InvalidUserAccountException();
            }
            return account;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown by the load.", e);
        }
        return null;
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

    @ApiMethod(name = "listMeasurementCategories")
    public List<MeasurementCategory> lisMeasurementCategories() throws MeasurementCategoryDoesntExist, MeasurementPrimaryUnitException, MeasurementCategoryAlreadyExists {

        return OfyService.ofy().load().type(MeasurementCategory.class).list();
    }

    @ApiMethod(name = "getUnitsForCategory")
    public List<MeasurementUnit> getUnitsForCategory(@Named("measurementCategory") String measurementCategory) throws MeasurementCategoryDoesntExist {

        measurementCategory = measurementCategory.toUpperCase();
        MeasurementCategory mc = EndpointUtility.getMeasurementCategory(measurementCategory);
        List<MeasurementUnit> lmu = OfyService.ofy().load().type(MeasurementUnit.class).filter("measurementCategoryId", mc.getId()).list();
        logger.log(Level.INFO, "Count for units for category :" + mc.toString() + " = " + lmu.size());
        return lmu;
    }

    @ApiMethod(name = "getItemCategories")
    public List<ItemCategory> getItemCategories() {

        List<ItemCategory> itemCategories = OfyService.ofy().load().type(ItemCategory.class).list();
        if (itemCategories == null || itemCategories.size() == 0) {
            return new ArrayList<ItemCategory>();
        }
        return itemCategories;
    }
}
