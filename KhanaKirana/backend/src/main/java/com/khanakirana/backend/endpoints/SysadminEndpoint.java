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
import com.google.appengine.api.users.User;
import com.khanakirana.backend.OfyService;
import com.khanakirana.backend.entity.Actionable;
import com.khanakirana.backend.entity.BusinessAccount;
import com.khanakirana.backend.entity.ItemCategory;
import com.khanakirana.backend.entity.MasterItem;
import com.khanakirana.backend.entity.MeasurementCategory;
import com.khanakirana.backend.entity.MeasurementUnit;
import com.khanakirana.backend.entity.NotApprovedBusinessAccount;
import com.khanakirana.backend.entity.SysadminAccount;
import com.khanakirana.backend.entity.UserAccount;
import com.khanakirana.backend.exceptions.InvalidUserAccountException;
import com.khanakirana.backend.exceptions.MeasurementCategoryAlreadyExists;
import com.khanakirana.backend.exceptions.MeasurementCategoryDoesntExist;
import com.khanakirana.backend.exceptions.MeasurementPrimaryUnitException;
import com.khanakirana.backend.jsonresource.UploadURL;
import com.khanakirana.common.KKActionTypes;
import com.khanakirana.common.KKConstants;
import com.khanakirana.common.KKStringCodes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "sysadminApi",
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
public class SysadminEndpoint {

    private final static Logger logger = Logger.getLogger(SysadminEndpoint.class.getName());

    @ApiMethod(name = "authenticate")
    public SysadminAccount authenticate(@Named("email") String email,
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
    @ApiMethod(name = "register")
    public SysadminAccount register(@Named("name") String name,
                                @Named("address") String address,
                                @Named("email") String email,
                                @Named("mobile") String mobile,
                                @Named("password") String password,
                                @Named("city") String city,
                                @Named("state") String state,
                                @Named("latitude") Double latitude,
                                @Named("longitude") Double longitude,
                                @Named("googleUser") Boolean googleUser) {

        SysadminAccount account = new SysadminAccount(name, address, email.toLowerCase(), mobile, password, city, state, longitude, latitude, googleUser);
        if (googleUser) {
            account.setPassword(null);
        }
        OfyService.ofy().save().entity(account).now();
        return account;
    }
    @ApiMethod(name = "getActionables")
    public List<Actionable> getActionables(User user) {

        List<Actionable> actionables = new ArrayList<>();
        // First find pending for approval business users.
        List<BusinessAccount> accounts = OfyService.ofy().load().type(BusinessAccount.class).filter("locked",true).list();
        logger.log(Level.INFO, "Total business accounts pending for approval " + accounts.size());
        for (BusinessAccount ba : accounts) {
            String details = ba.getName() + "\n" + ba.getAddress() + "\n" + ba.getRegion().getCity() + " " + ba.getRegion().getState();
            Actionable actionable = new Actionable(KKActionTypes.APPROVE_BUSINESS_USER,
                    KKStringCodes.APPROVE_BUSINESS_USER_TITLE,
                    KKStringCodes.APPROVE_BUSINESS_USER_ACTION_TITLE,
                    KKStringCodes.APPROVE_BUSINESS_USER_DESCRIPTION,
                    ba.getId(),
                    details,
                    false);
            logger.log(Level.INFO, "Adding business account approval actionable " + ba.getId());
            actionables.add(actionable);
        }
        return actionables;
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "approveBusiness")
    public BusinessAccount approveBusiness(@Named("id") Long id,
                                           @Named("approve") Boolean approve,
                                            User user) throws InvalidUserAccountException {

        try {

            BusinessAccount account = OfyService.ofy().load().type(BusinessAccount.class).id(id).now();
            if (account != null) {
                if (approve) {
                    account.setLocked(Boolean.FALSE);
                    logger.log(Level.INFO, "Business Account approved." + account.toString());
                    OfyService.ofy().save().entity(account).now();
                    return account;
                }
                else {
                    NotApprovedBusinessAccount naba = new NotApprovedBusinessAccount(account);
                    OfyService.ofy().save().entity(naba).now();
                    OfyService.ofy().delete().entity(account).now();
                    logger.log(Level.INFO, "Business Account deleted." + account.toString());
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown by the load.", e);
        }
        return null;
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "isRegisteredUser")
    public SysadminAccount isRegisteredUser(@Named("email") String email,
                                   User user) throws InvalidUserAccountException {

        try {

            if (OfyService.ofy().load().type(SysadminAccount.class).list().size() == 0) {
                SysadminAccount admin = new SysadminAccount("Vinay Avasthi", "Somewhere someplace", "vinay@avasthi.com", "+919845614175", "NOTSET", "Bangalore", "Karnataka", 0.0, 0.0, true);
                OfyService.ofy().save().entity(admin).now();
                return admin;
            }
            SysadminAccount account = OfyService.ofy().load().type(SysadminAccount.class).filter("email", email).first().now();
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
    private MeasurementUnit getMeasurementUnit(MeasurementCategory measurementCategory, String name) {

        name = name.toUpperCase();
        MeasurementUnit measurementUnit = OfyService.ofy().load().type(MeasurementUnit.class).filter("measurementCategoryId", measurementCategory.getId()).filter("name", name).first().now();
        return measurementUnit;
    }
}
