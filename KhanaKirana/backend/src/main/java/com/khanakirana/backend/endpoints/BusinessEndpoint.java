/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.khanakirana.backend.endpoints;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.khanakirana.backend.OfyService;
import com.khanakirana.backend.entity.BusinessAccountResult;
import com.khanakirana.backend.entity.ItemCategory;
import com.khanakirana.backend.entity.MasterItem;
import com.khanakirana.backend.entity.MeasurementCategory;
import com.khanakirana.backend.entity.MeasurementUnit;
import com.khanakirana.backend.entity.BusinessAccount;
import com.khanakirana.backend.entity.UserAccount;
import com.khanakirana.backend.entity.UserAccountRegion;
import com.khanakirana.backend.exceptions.InvalidUserAccountException;
import com.khanakirana.backend.exceptions.MeasurementCategoryAlreadyExists;
import com.khanakirana.backend.exceptions.MeasurementCategoryDoesntExist;
import com.khanakirana.backend.exceptions.MeasurementPrimaryUnitException;
import com.khanakirana.backend.jsonresource.UploadURL;
import com.khanakirana.common.KKConstants;
import com.khanakirana.common.ServerInteractionReturnStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "businessApi",
        version = "v1",
        scopes = {KKConstants.EMAIL_SCOPE},
        audiences = {KKConstants.ANDROID_AUDIENCE},
        clientIds = {KKConstants.WEB_CLIENT_ID, KKConstants.BUSINESS_ADMIN_ANDROID_CLIENT_ID},
        namespace = @ApiNamespace(
                ownerDomain = "backend.khanakirana.com",
                ownerName = "backend.khanakirana.com",
                packagePath = ""
        )
)
public class BusinessEndpoint {

    private final static Logger logger = Logger.getLogger(BusinessEndpoint.class.getName());

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "isRegisteredUser")
    public BusinessAccountResult isRegisteredUser(User user) throws InvalidUserAccountException, ForbiddenException, OAuthRequestException {

        if (user == null) {
            throw new OAuthRequestException("User is null");
        }

        try {

            BusinessAccount account = OfyService.ofy().load().type(BusinessAccount.class).filter("email", user.getEmail().toLowerCase()).first().now();
            if (account == null) {

                return new BusinessAccountResult(account, ServerInteractionReturnStatus.INVALID_USER);
            }
            logger.log(Level.INFO, "User Account retrieved." + account.toString());
            return new BusinessAccountResult(account, ServerInteractionReturnStatus.SUCCESS);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown by the load.", e);
        }
        return new BusinessAccountResult(null, ServerInteractionReturnStatus.INVALID_USER);
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "register")
    public BusinessAccountResult register(@Named("name") String name,
                                        @Named("address") String address,
                                        @Named("email") String email,
                                        @Named("mobile") String mobile,
                                        @Named("password") String password,
                                        @Named("city") String city,
                                        @Named("state") String state,
                                        @Named("latitude") Double latitude,
                                        @Named("longitude") Double longitude,
                                        @Named("googleUser") Boolean googleUser,
                                          User user) throws ForbiddenException {

        if (!user.getEmail().equalsIgnoreCase(email)) {
            throw new ForbiddenException(email + " has not authenticated.");
        }

        BusinessAccount account = new BusinessAccount(name, address, email.toLowerCase(), mobile, password, city, state, longitude, latitude, googleUser);
        if (googleUser) {
            account.setPassword(null);
        }
        OfyService.ofy().save().entity(account).now();
        return new BusinessAccountResult(account, ServerInteractionReturnStatus.SUCCESS);
    }

    @ApiMethod(name = "updateLocation")
    public BusinessAccountResult updateLocation(@Named("latitude") Double latitude,
                                                @Named("longitude") Double longitude,
                                                User user) throws ForbiddenException, OAuthRequestException {

        if (user == null) {
            throw new OAuthRequestException("User is null");
        }


        try {
            String email = user.getEmail().toLowerCase();
            BusinessAccount account = OfyService.ofy().load().type(BusinessAccount.class).filter("email", email).first().now();
            logger.log(Level.INFO, "User Account retrieved." + account.toString());
            if (account == null) {

                return new BusinessAccountResult(account, ServerInteractionReturnStatus.INVALID_USER);
            }
            account.setLatitude(latitude);
            account.setLongitude(longitude);
            OfyService.ofy().save().entity(account).now();
            return new BusinessAccountResult(account, ServerInteractionReturnStatus.SUCCESS);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown by the load.", e);
        }
        return new BusinessAccountResult(null, ServerInteractionReturnStatus.FATAL_ERROR);
    }

    @ApiMethod(name = "authenticate")
    public BusinessAccountResult authenticate(@Named("email") String email,
                                    @Named("password") String password,
                                    @Named("googleUser") Boolean googleUser) {
        try {
            email = email.toLowerCase();
            BusinessAccount account = OfyService.ofy().load().type(BusinessAccount.class).filter("email", email).first().now();
            logger.log(Level.INFO, "User Account retrieved." + account.toString());
            if (account == null) {

                return new BusinessAccountResult(account, ServerInteractionReturnStatus.INVALID_USER);
            } else if (!googleUser && !password.equalsIgnoreCase(password)) {

                return new BusinessAccountResult(account, ServerInteractionReturnStatus.AUTHENTICATION_FAILED);
            }
            return new BusinessAccountResult(account, ServerInteractionReturnStatus.SUCCESS);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown by the load.", e);
        }
        return new BusinessAccountResult(null, ServerInteractionReturnStatus.FATAL_ERROR);
    }

    @ApiMethod(name = "supportedRegions")
    public List<UserAccountRegion> supportedRegions(User user) throws OAuthRequestException {

        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        List<UserAccountRegion> regions = OfyService.ofy().load().type(UserAccountRegion.class).list();
        return regions;
    }

    @ApiMethod(name = "listMeasurementCategories")
    public List<MeasurementCategory> lisMeasurementCategories(User user) throws MeasurementCategoryDoesntExist, MeasurementPrimaryUnitException, MeasurementCategoryAlreadyExists, OAuthRequestException {

        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        return OfyService.ofy().load().type(MeasurementCategory.class).list();
    }

    @ApiMethod(name = "getUnitsForCategory")
    public List<MeasurementUnit> getUnitsForCategory(@Named("measurementCategory") String measurementCategory,
                                                     User user) throws MeasurementCategoryDoesntExist, OAuthRequestException {

        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        measurementCategory = measurementCategory.toUpperCase();
        MeasurementCategory mc = EndpointUtility.getMeasurementCategory(measurementCategory);
        List<MeasurementUnit> lmu = OfyService.ofy().load().type(MeasurementUnit.class).filter("measurementCategoryId", mc.getId()).list();
        logger.log(Level.INFO, "Count for units for category :" + mc.toString() + " = " + lmu.size());
        return lmu;
    }

    @ApiMethod(name = "getUploadURL")
    public UploadURL getUploadURL(User user) throws OAuthRequestException {

        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        return new UploadURL(blobstoreService.createUploadUrl(KKConstants.MASTER_ITEM_IMAGE_UPLOAD_URL));
    }
    @ApiMethod(name = "getItemCategories")
    public List<ItemCategory> getItemCategories(User user) throws OAuthRequestException {

        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        List<ItemCategory> itemCategories = OfyService.ofy().load().type(ItemCategory.class).list();
        if (itemCategories == null || itemCategories.size() == 0) {
            return new ArrayList<ItemCategory>();
        }
        return itemCategories;
    }
}
