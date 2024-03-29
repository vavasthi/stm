/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.avasthi.roadcompanion.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.avasthi.roadcompanion.backend.OfyService;
import com.avasthi.roadcompanion.backend.entity.Actionable;
import com.avasthi.roadcompanion.backend.entity.BusinessAccount;
import com.avasthi.roadcompanion.backend.entity.ItemCategory;
import com.avasthi.roadcompanion.backend.entity.MasterItem;
import com.avasthi.roadcompanion.backend.entity.MeasurementCategory;
import com.avasthi.roadcompanion.backend.entity.MeasurementUnit;
import com.avasthi.roadcompanion.backend.entity.NotApprovedBusinessAccount;
import com.avasthi.roadcompanion.backend.entity.SysadminAccount;
import com.avasthi.roadcompanion.backend.entity.UserAccount;
import com.avasthi.roadcompanion.backend.exceptions.InvalidUserAccountException;
import com.avasthi.roadcompanion.backend.exceptions.MeasurementCategoryAlreadyExists;
import com.avasthi.roadcompanion.backend.exceptions.MeasurementCategoryDoesntExist;
import com.avasthi.roadcompanion.backend.exceptions.MeasurementPrimaryUnitException;
import com.avasthi.roadcompanion.backend.jsonresource.UploadURL;
import com.avasthi.roadcompanion.common.KKActionTypes;
import com.avasthi.roadcompanion.common.KKConstants;
import com.avasthi.roadcompanion.common.KKStringCodes;

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
        audiences = {KKConstants.ANDROID_AUDIENCE},
        clientIds = {KKConstants.WEB_CLIENT_ID, KKConstants.SYSTEM_ADMIN_ANDROID_CLIENT_ID},
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
                                        @Named("googleUser") Boolean googleUser,
                                        User user) throws ForbiddenException, OAuthRequestException {
        authorizeApi(user);
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
                                    @Named("city") String city,
                                    @Named("state") String state,
                                    @Named("latitude") Double latitude,
                                    @Named("longitude") Double longitude) {

        SysadminAccount account = new SysadminAccount(name, address, email.toLowerCase(), mobile, city, state, longitude, latitude);
        OfyService.ofy().save().entity(account).now();
        return account;
    }

    @ApiMethod(name = "getActionables")
    public List<Actionable> getActionables(User user) throws ForbiddenException, OAuthRequestException {

        authorizeApi(user);
        List<Actionable> actionables = new ArrayList<>();
        // First find pending for approval business users.
        List<BusinessAccount> accounts = OfyService.ofy().load().type(BusinessAccount.class).filter("locked", true).list();
        logger.log(Level.INFO, "Total business accounts pending for approval " + accounts.size());
        if (accounts.size() > 0) {

            Actionable actionable = new Actionable(KKActionTypes.APPROVE_BUSINESS_USER,
                    KKStringCodes.APPROVE_BUSINESS_USER_TITLE,
                    KKStringCodes.APPROVE_BUSINESS_USER_DESCRIPTION,
                    accounts.size(),
                    false);
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
                                           User user) throws InvalidUserAccountException, ForbiddenException, OAuthRequestException {

        authorizeApi(user);
        try {

            BusinessAccount account = OfyService.ofy().load().type(BusinessAccount.class).id(id).now();
            if (account != null) {
                if (approve) {
                    account.setLocked(Boolean.FALSE);
                    logger.log(Level.INFO, "Business Account approved." + account.toString());
                    OfyService.ofy().save().entity(account).now();
                    return account;
                } else {
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
    public SysadminAccount isRegisteredUser(User user) throws InvalidUserAccountException, OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        try {

            if (OfyService.ofy().load().type(SysadminAccount.class).list().size() == 0) {
                SysadminAccount admin = new SysadminAccount("Vinay Avasthi", "Somewhere someplace", "vinay@avasthi.com", "+919845614175", "Bangalore", "Karnataka", 0.0, 0.0);
                OfyService.ofy().save().entity(admin).now();
                return admin;
            }
            SysadminAccount account = OfyService.ofy().load().type(SysadminAccount.class).filter("email", user.getEmail().toLowerCase()).first().now();
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
                                              @Named("factor") Double factor,
                                              User user) throws MeasurementCategoryDoesntExist, MeasurementPrimaryUnitException, MeasurementCategoryAlreadyExists, OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        name = name.toUpperCase();
        measurementCategory = measurementCategory.toUpperCase();
        MeasurementCategory mc = getMeasurementCategory(measurementCategory);
        logger.log(Level.INFO, "Queried Measurement Category " + mc.toString() + " " + mc.getId());
        if (primaryUnit) {
            factor = 1.0;
            if (OfyService.ofy().load().type(MeasurementUnit.class).filter("primaryUnit", Boolean.TRUE).filter("measurementCategoryId", mc.getId()).list().size() > 0) {
                throw MeasurementPrimaryUnitException.getExceptionForPrimaryUnitAlreadyExists(mc);
            }
        } else {

            if (OfyService.ofy().load().type(MeasurementUnit.class).filter("primaryUnit", Boolean.TRUE).filter("measurementCategoryId", mc.getId()).list().size() == 0) {
                throw MeasurementPrimaryUnitException.getExceptionForPrimaryUnitDoesntExist(mc);
            }
        }
        MeasurementUnit unit = new MeasurementUnit(name, acronym, mc.getId(), primaryUnit, factor);
        OfyService.ofy().save().entity(unit).now();
        return unit;
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
        MeasurementCategory mc = getMeasurementCategory(measurementCategory);
        List<MeasurementUnit> lmu = OfyService.ofy().load().type(MeasurementUnit.class).filter("measurementCategoryId", mc.getId()).list();
        logger.log(Level.INFO, "Count for units for category :" + mc.toString() + " = " + lmu.size());
        return lmu;
    }

    @ApiMethod(name = "addMeasurementCategory")
    public MeasurementCategory addMeasurementCategory(@Named("name") String name,
                                                      @Named("fractional") Boolean fractional,
                                                      User user) throws MeasurementCategoryAlreadyExists, OAuthRequestException, ForbiddenException {

        authorizeApi(user);
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
                                          @Named("measurementCategory") String measurementCategory,
                                          User user) throws MeasurementCategoryAlreadyExists, MeasurementCategoryDoesntExist, InvalidUserAccountException, OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        MeasurementCategory mc = getMeasurementCategory(measurementCategory);
        UserAccount userAccount = getUserAccount(user.getEmail().toLowerCase());
        MasterItem mi = new MasterItem(name, description, upc, imageType, imageCloudKey, userAccount.getEmail(), mc.getId());
        OfyService.ofy().save().entity(mi).now();
        return mi;
    }

    @ApiMethod(name = "getUploadURL")
    public UploadURL getUploadURL(User user) throws OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        return new UploadURL(blobstoreService.createUploadUrl(KKConstants.MASTER_ITEM_IMAGE_UPLOAD_URL));
    }

    @ApiMethod(name = "createChildItemCategory")
    public List<ItemCategory> createChildItemCategory(@Named("parentId") Long parentId,
                                                      @Named("name") String name,
                                                      @Named("description") String description,
                                                      User user) throws OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        ItemCategory pic = OfyService.ofy().load().type(ItemCategory.class).id(parentId).now();
        ItemCategory ic;
        if (pic == null) {

            ic = new ItemCategory(0L, true, name, description);
        } else {

            ic = new ItemCategory(pic.getId(), false, name, description);
        }
        OfyService.ofy().save().entity(ic).now();
        return getItemCategories();
    }

    @ApiMethod(name = "createItemCategory")
    public List<ItemCategory> createItemCategory(@Named("name") String name,
                                                 @Named("description") String description,
                                                 User user) throws OAuthRequestException, ForbiddenException {
        authorizeApi(user);
        ItemCategory ic = new ItemCategory(0L, true, name, description);
        OfyService.ofy().save().entity(ic).now();
        return getItemCategories();
    }

    @ApiMethod(name = "getItemCategories")
    public List<ItemCategory> getItemCategories(User user) throws OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        return getItemCategories();
    }
    private List<ItemCategory> getItemCategories() {

        List<ItemCategory> itemCategories = OfyService.ofy().load().type(ItemCategory.class).list();
        if (itemCategories == null || itemCategories.size() == 0) {
            return new ArrayList<>();
        }
        return itemCategories;
    }
    private MeasurementCategory getMeasurementCategory(String name) throws MeasurementCategoryDoesntExist {

        name = name.toUpperCase();
        MeasurementCategory measurementCategory = OfyService.ofy().load().type(MeasurementCategory.class).filter("name", name).first().now();
        if (measurementCategory == null) {
            throw new MeasurementCategoryDoesntExist();
        }
        return measurementCategory;
    }

    private UserAccount getUserAccount(String email) throws MeasurementCategoryDoesntExist, InvalidUserAccountException {

        email = email.toLowerCase();
        UserAccount userAccount = OfyService.ofy().load().type(UserAccount.class).filter("email", email).first().now();
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

    private void authorizeApi(User user) throws ForbiddenException, OAuthRequestException {
        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        List<SysadminAccount> accounts = OfyService.ofy().load().type(SysadminAccount.class).filter("email", user.getEmail().toLowerCase()).list();
        if (accounts.size() == 0) {
            throw new ForbiddenException("User " + user.getUserId() + " is not authorized for admin activities.");
        }

    }
}
