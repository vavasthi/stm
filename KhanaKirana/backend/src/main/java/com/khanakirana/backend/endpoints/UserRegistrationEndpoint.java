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
import com.khanakirana.backend.KKConstants;
import com.khanakirana.backend.OfyService;
import com.khanakirana.backend.entity.ItemCategory;
import com.khanakirana.backend.entity.MasterItem;
import com.khanakirana.backend.entity.MeasurementCategory;
import com.khanakirana.backend.entity.MeasurementUnit;
import com.khanakirana.backend.entity.UserAccount;
import com.khanakirana.backend.entity.UserAccountRegion;
import com.khanakirana.backend.exceptions.InvalidUserAccountException;
import com.khanakirana.backend.exceptions.MeasurementCategoryAlreadyExists;
import com.khanakirana.backend.exceptions.MeasurementCategoryDoesntExist;
import com.khanakirana.backend.exceptions.MeasurementPrimaryUnitException;
import com.khanakirana.backend.jsonresource.UploadURL;

import java.sql.Blob;
import java.util.ArrayList;
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
        UserAccount account = new UserAccount(fullname, address, email.toLowerCase(), mobile, password, city, state, longitude, latitude, googleUser);
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

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown by the load.", e);
        }
        return null;
    }

    @ApiMethod(name = "insertSystemAdministrator")
    public UserAccount insertSystemAdministrator(@Named("email") String email,
                                                 @Named("mobile") String mobile) {

        email = email.toLowerCase();
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

    @ApiMethod(name = "addMeasurementUnit")
    public MeasurementUnit addMeasurementUnit(@Named("name") String name,
                                              @Named("acronym") String acronym,
                                              @Named("measurementCategory") String measurementCategory,
                                              @Named("primaryUnit") Boolean primaryUnit,
                                              @Named("factor") Double factor) throws MeasurementCategoryDoesntExist, MeasurementPrimaryUnitException, MeasurementCategoryAlreadyExists {

        name = name.toUpperCase();
        measurementCategory = measurementCategory.toUpperCase();
        MeasurementCategory mc  = getMeasurementCategory(measurementCategory);
        logger.log(Level.INFO, "Queried Measurement Category " + mc.toString() + " " + mc.getId());
        if (primaryUnit) {
            factor = 1.0;
            if (OfyService.ofy().load().type(MeasurementUnit.class).filter("primaryUnit", Boolean.TRUE).filter("measurementCategoryId", mc.getId()).list().size() > 0) {
                throw MeasurementPrimaryUnitException.getExceptionForPrimaryUnitAlreadyExists(mc);
            }
        }
        else {

            if (OfyService.ofy().load().type(MeasurementUnit.class).filter("primaryUnit", Boolean.TRUE).filter("measurementCategoryId", mc.getId()).list().size() == 0) {
                throw MeasurementPrimaryUnitException.getExceptionForPrimaryUnitDoesntExist(mc);
            }
        }
        MeasurementUnit unit = new MeasurementUnit(name, acronym, mc.getId(), primaryUnit, factor);
        OfyService.ofy().save().entity(unit).now();
        return unit;
    }

    @ApiMethod(name = "listMeasurementCategories")
    public List<MeasurementCategory> lisMeasurementCategories() throws MeasurementCategoryDoesntExist, MeasurementPrimaryUnitException, MeasurementCategoryAlreadyExists {

        return OfyService.ofy().load().type(MeasurementCategory.class).list();
    }

    @ApiMethod(name = "getUnitsForCategory")
    public List<MeasurementUnit> getUnitsForCategory(@Named("measurementCategory") String measurementCategory) throws MeasurementCategoryDoesntExist {

        measurementCategory = measurementCategory.toUpperCase();
        MeasurementCategory mc = getMeasurementCategory(measurementCategory);
        List<MeasurementUnit> lmu = OfyService.ofy().load().type(MeasurementUnit.class).filter("measurementCategoryId", mc.getId()).list();
        logger.log(Level.INFO, "Count for units for category :" + mc.toString() + " = " + lmu.size());
        return lmu;
    }

    private MeasurementCategory getMeasurementCategory(String name) throws MeasurementCategoryDoesntExist {

        name = name.toUpperCase();
        MeasurementCategory measurementCategory  = OfyService.ofy().load().type(MeasurementCategory.class).filter("name", name).first().now();
        if (measurementCategory == null) {
            throw new MeasurementCategoryDoesntExist();
        }
        return measurementCategory;
    }

    private UserAccount getUserAccount(String email) throws MeasurementCategoryDoesntExist, InvalidUserAccountException {

        email = email.toLowerCase();
        UserAccount userAccount  = OfyService.ofy().load().type(UserAccount.class).filter("email", email).first().now();
        if (userAccount == null) {
            throw new InvalidUserAccountException();
        }
        return userAccount;
    }

    @ApiMethod(name = "addMeasurementCategory")
    public MeasurementCategory addMeasurementCategory(@Named("name") String name, @Named("fractional") Boolean fractional) throws MeasurementCategoryAlreadyExists {

        name = name.toUpperCase();
        MeasurementCategory measurementCategory = null;
        try {
            measurementCategory = getMeasurementCategory(name);
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

        MeasurementCategory mc = getMeasurementCategory(measurementCategory);
        UserAccount userAccount = getUserAccount(userEmailId);
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
    public ItemCategory createChildItemCategory(@Named("parentId") Long parentId,
                                                @Named("name") String name,
                                                 @Named("description") String description) {

        ItemCategory pic = OfyService.ofy().load().type(ItemCategory.class).id(parentId).now();
        if (pic == null) {
            pic = getRootItemCategory();
        }
        ItemCategory ic = new ItemCategory(pic.getId(), false, name, description);
        OfyService.ofy().save().entity(ic).now();
        return ic;
    }
    @ApiMethod(name = "createItemCategory")
    public ItemCategory createItemCategory(@Named("name") String name,
                                           @Named("description") String description) {
        ItemCategory ric = getRootItemCategory();
        ItemCategory ic = new ItemCategory(ric.getId(), false, name, description);
        OfyService.ofy().save().entity(ic).now();
        return ic;
    }
    @ApiMethod(name = "getItemCategories")
    public List<ItemCategory> getItemCategories() {

        List<ItemCategory> itemCategories = OfyService.ofy().load().type(ItemCategory.class).list();
        if (itemCategories == null || itemCategories.size() == 0) {
            return new ArrayList<ItemCategory>();
        }
        return itemCategories;
    }
    private ItemCategory getRootItemCategory() {
        ItemCategory ric = OfyService.ofy().load().type(ItemCategory.class).filter("root", Boolean.TRUE).first().now();
        if (ric == null) {
            ric = new ItemCategory(0L, true,"root", "This is dummy root category");
            OfyService.ofy().save().entity(ric).now();
            return ric;
        }
        return ric;
    }
    private MeasurementUnit getMeasurementUnit(MeasurementCategory measurementCategory, String name) {

        name = name.toUpperCase();
        MeasurementUnit measurementUnit = OfyService.ofy().load().type(MeasurementUnit.class).filter("measurementCategoryId", measurementCategory.getId()).filter("name", name).first().now();
        return measurementUnit;
    }
}
