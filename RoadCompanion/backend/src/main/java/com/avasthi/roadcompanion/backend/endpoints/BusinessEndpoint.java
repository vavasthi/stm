/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.avasthi.roadcompanion.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.avasthi.roadcompanion.backend.OfyService;
import com.avasthi.roadcompanion.backend.entity.BusinessAccountResult;
import com.avasthi.roadcompanion.backend.entity.ItemCategory;
import com.avasthi.roadcompanion.backend.entity.MeasurementCategory;
import com.avasthi.roadcompanion.backend.entity.MeasurementUnit;
import com.avasthi.roadcompanion.backend.entity.BusinessAccount;
import com.avasthi.roadcompanion.backend.entity.UserAccountRegion;
import com.avasthi.roadcompanion.backend.exceptions.InvalidUserAccountException;
import com.avasthi.roadcompanion.backend.exceptions.MeasurementCategoryAlreadyExists;
import com.avasthi.roadcompanion.backend.exceptions.MeasurementCategoryDoesntExist;
import com.avasthi.roadcompanion.backend.exceptions.MeasurementPrimaryUnitException;
import com.avasthi.roadcompanion.backend.jsonresource.UploadURL;
import com.avasthi.roadcompanion.common.KKConstants;
import com.avasthi.roadcompanion.common.ServerInteractionReturnStatus;

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


        return new BusinessAccountResult(authorizeApi(user), ServerInteractionReturnStatus.SUCCESS);
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "register")
    public BusinessAccountResult register(@Named("name") String name,
                                        @Named("address") String address,
                                        @Named("mobile") String mobile,
                                        @Named("city") String city,
                                        @Named("state") String state,
                                        @Named("latitude") Double latitude,
                                        @Named("longitude") Double longitude,
                                          User user) throws ForbiddenException, OAuthRequestException {

        if (user == null) {
            throw new ForbiddenException("user is null.");
        }
        try {

            BusinessAccount account = authorizeApi(user);
            throw new ConflictException(user.getEmail() + " already exists.");
        }
        catch(Exception ex) {

        }

        BusinessAccount account = new BusinessAccount(name, address, user.getEmail().toLowerCase(), mobile, city, state, longitude, latitude);
        OfyService.ofy().save().entity(account).now();
        return new BusinessAccountResult(account, ServerInteractionReturnStatus.SUCCESS);
    }

    @ApiMethod(name = "updateLocation")
    public BusinessAccountResult updateLocation(@Named("latitude") Double latitude,
                                             @Named("longitude") Double longitude,
                                             User user) throws ForbiddenException, OAuthRequestException {

        BusinessAccount account = authorizeApi(user);
        if (account == null) {
            throw new ForbiddenException(user.getEmail() + " is an invalid user.");
        }
        try {
            account.setLatitude(latitude);
            account.setLongitude(longitude);
            OfyService.ofy().save().entity(account).now();
            return new BusinessAccountResult(account, ServerInteractionReturnStatus.SUCCESS);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown by the load.", e);
        }
        return new BusinessAccountResult(null, ServerInteractionReturnStatus.FATAL_ERROR);
    }

    @ApiMethod(name = "supportedRegions")
    public List<UserAccountRegion> supportedRegions(User user) throws OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        List<UserAccountRegion> regions = OfyService.ofy().load().type(UserAccountRegion.class).list();
        return regions;
    }

    @ApiMethod(name = "listMeasurementCategories")
    public List<MeasurementCategory> lisMeasurementCategories(User user) throws MeasurementCategoryDoesntExist, MeasurementPrimaryUnitException, MeasurementCategoryAlreadyExists, OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        return OfyService.ofy().load().type(MeasurementCategory.class).list();
    }

    @ApiMethod(name = "getUnitsForCategory")
    public List<MeasurementUnit> getUnitsForCategory(@Named("measurementCategory") String measurementCategory,
                                                     User user) throws MeasurementCategoryDoesntExist, OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        measurementCategory = measurementCategory.toUpperCase();
        MeasurementCategory mc = EndpointUtility.getMeasurementCategory(measurementCategory);
        List<MeasurementUnit> lmu = OfyService.ofy().load().type(MeasurementUnit.class).filter("measurementCategoryId", mc.getId()).list();
        logger.log(Level.INFO, "Count for units for category :" + mc.toString() + " = " + lmu.size());
        return lmu;
    }

    @ApiMethod(name = "getUploadURL")
    public UploadURL getUploadURL(User user) throws OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        return new UploadURL(blobstoreService.createUploadUrl(KKConstants.MASTER_ITEM_IMAGE_UPLOAD_URL));
    }
    @ApiMethod(name = "getItemCategories")
    public List<ItemCategory> getItemCategories(User user) throws OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        List<ItemCategory> itemCategories = OfyService.ofy().load().type(ItemCategory.class).list();
        if (itemCategories == null || itemCategories.size() == 0) {
            return new ArrayList<ItemCategory>();
        }
        return itemCategories;
    }
    private BusinessAccount authorizeApi(com.google.appengine.api.users.User user) throws ForbiddenException, OAuthRequestException {
        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        BusinessAccount account = OfyService.ofy().load().type(BusinessAccount.class).filter("email", user.getEmail().toLowerCase()).first().now();
        if (account == null) {

            throw new ForbiddenException("User " + user.getUserId() + " is not authorized for admin activities.");
        }
        return account;
    }
}
