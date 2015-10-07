/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.khanakirana.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
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
        audiences = {KKConstants.ANDROID_CLIENT_ID},
        clientIds = {KKConstants.WEB_CLIENT_ID, KKConstants.ANDROID_CLIENT_ID},
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
    public BusinessAccountResult isRegisteredUser(@Named("email") String email) throws InvalidUserAccountException {

        try {

            BusinessAccount account = OfyService.ofy().load().type(BusinessAccount.class).filter("email", email).first().now();
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
                                        @Named("googleUser") Boolean googleUser) {

        BusinessAccount account = new BusinessAccount(name, address, email.toLowerCase(), mobile, password, city, state, longitude, latitude, googleUser);
        if (googleUser) {
            account.setPassword(null);
        }
        OfyService.ofy().save().entity(account).now();
        return new BusinessAccountResult(account, ServerInteractionReturnStatus.SUCCESS);
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
    public List<UserAccountRegion> supportedRegions() {
        List<UserAccountRegion> regions = OfyService.ofy().load().type(UserAccountRegion.class).list();
        return regions;
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

    @ApiMethod(name = "addMeasurementCategory")
    public MeasurementCategory addMeasurementCategory(@Named("name") String name, @Named("fractional") Boolean fractional) throws MeasurementCategoryAlreadyExists {

        name = name.toUpperCase();
        MeasurementCategory measurementCategory = null;
        try {
            measurementCategory = EndpointUtility.getMeasurementCategory(name);
            throw new MeasurementCategoryAlreadyExists();

        } catch (MeasurementCategoryDoesntExist e) {
        }

        measurementCategory = new MeasurementCategory(name, fractional);
        OfyService.ofy().save().entity(measurementCategory).now();
        return measurementCategory;
    }

    @ApiMethod(name = "addItemInMasterList")
    public MasterItem addItemInMasterList(@Named("name") String name,
                                          @Named("description") String description,
                                          @Named("upc") String upc,
                                          @Named("imageType") String imageType,
                                          @Named("imageCloudKey") String imageCloudKey,
                                          @Named("userEmailId") String userEmailId,
                                          @Named("measurementCategory") String measurementCategory) throws MeasurementCategoryAlreadyExists, MeasurementCategoryDoesntExist, InvalidUserAccountException {

        MeasurementCategory mc = EndpointUtility.getMeasurementCategory(measurementCategory);
        UserAccount userAccount = EndpointUtility.getUserAccount(userEmailId);
        MasterItem mi = new MasterItem(name, description, upc, imageType, imageCloudKey, userAccount.getEmail(), mc.getId());
        OfyService.ofy().save().entity(mi).now();
        return mi;
    }

    @ApiMethod(name = "getUploadURL")
    public UploadURL getUploadURL() {

        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        return new UploadURL(blobstoreService.createUploadUrl(KKConstants.MASTER_ITEM_IMAGE_UPLOAD_URL));
    }
    @ApiMethod(name = "createChildItemCategory")
    public List<ItemCategory> createChildItemCategory(@Named("parentId") Long parentId,
                                                @Named("name") String name,
                                                 @Named("description") String description) {

        ItemCategory pic = OfyService.ofy().load().type(ItemCategory.class).id(parentId).now();
        ItemCategory ic;
        if (pic == null) {

            ic = new ItemCategory(0L, true, name, description);
        }
        else {

            ic = new ItemCategory(pic.getId(), false, name, description);
        }
        OfyService.ofy().save().entity(ic).now();
        return getItemCategories();
    }

    @ApiMethod(name = "createItemCategory")
    public List<ItemCategory> createItemCategory(@Named("name") String name,
                                           @Named("description") String description) {
        ItemCategory ic = new ItemCategory(0L, true, name, description);
        OfyService.ofy().save().entity(ic).now();
        return getItemCategories();
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
