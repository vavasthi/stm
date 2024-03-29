/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.sanjnan.vitarak.server.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;
import com.sanjnan.vitarak.server.backend.OfyService;
import com.sanjnan.vitarak.server.backend.entity.BusinessAdminAccount;
import com.sanjnan.vitarak.server.backend.entity.BusinessAccountResult;
import com.sanjnan.vitarak.server.backend.entity.DeliveryAccount;
import com.sanjnan.vitarak.server.backend.entity.DisaggregationEntity;
import com.sanjnan.vitarak.server.backend.entity.InvoiceItem;
import com.sanjnan.vitarak.server.backend.entity.MasterItem;
import com.sanjnan.vitarak.server.backend.entity.MasterStockEntity;
import com.sanjnan.vitarak.server.backend.entity.MeasurementUnit;
import com.sanjnan.vitarak.server.backend.entity.SalesInvoice;
import com.sanjnan.vitarak.server.backend.entity.UserAccountRegion;
import com.sanjnan.vitarak.server.backend.exceptions.CantBeDisaggregatedException;
import com.sanjnan.vitarak.server.backend.exceptions.InvalidInvoiceException;
import com.sanjnan.vitarak.server.backend.exceptions.InvalidMasterItemException;
import com.sanjnan.vitarak.server.backend.exceptions.InvalidUserAccountException;
import com.sanjnan.vitarak.server.backend.exceptions.MasterItemStockZeroException;
import com.sanjnan.vitarak.server.backend.exceptions.NoDisaggregationPolicyException;
import com.sanjnan.vitarak.server.backend.jsonresource.UploadURL;
import com.sanjnan.vitarak.common.SanjnanConstants;
import com.sanjnan.vitarak.common.ServerInteractionReturnStatus;
import com.sanjnan.vitarak.server.backend.utils.Pair;

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
        scopes = {SanjnanConstants.EMAIL_SCOPE},
        audiences = {SanjnanConstants.ANDROID_AUDIENCE},
        clientIds = {SanjnanConstants.WEB_CLIENT_ID, SanjnanConstants.BUSINESS_ADMIN_ANDROID_CLIENT_ID},
        namespace = @ApiNamespace(
                ownerDomain = "backend.server.vitarak.sanjnan.com",
                ownerName = "backend.server.vitarak.sanjnan.com",
                packagePath = ""
        )
)
public class BusinessEndpoint extends BaseEndpoint {

    private final static Logger logger = Logger.getLogger(BusinessEndpoint.class.getName());

    /**
     * This endpoint is called to check if the user is a registered user or not.
     *
     * @param user
     * @return
     * @throws InvalidUserAccountException
     * @throws ForbiddenException
     * @throws OAuthRequestException
     */
    @ApiMethod(name = "isRegisteredUser")
    public BusinessAccountResult isRegisteredUser(User user) throws InvalidUserAccountException, ForbiddenException, OAuthRequestException {


        return new BusinessAccountResult(authorizeBusinessUser(user), ServerInteractionReturnStatus.SUCCESS);
    }

    /**
     * This endpoint needs to be called for registering a user as a business user. The user needs
     * to be an authenticated google user on an android phone.
     *
     * @param name name of the user. This could be business name
     * @param address Address of the user.
     * @param mobile Mobile number that the business could be reached on
     * @param city City of the user,
     * @param state State of the user
     * @param latitude Latitude of primary place of business
     * @param longitude Longitude of primary place of business
     * @param user Authenticated google user.
     * @return BusinessAccountResult containing the details of the account created.
     * @throws ForbiddenException
     * @throws OAuthRequestException
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

            BusinessAdminAccount account = findBusinessUser(user);
            if (account != null) {

                throw new ConflictException(user.getEmail() + " already exists.");
            }
        }
        catch(Exception ex) {
            logger.log(Level.SEVERE, String.format("Some error while authorizing user %s", user.getEmail()));
        }

        BusinessAdminAccount account
                = new BusinessAdminAccount(name, address, user.getEmail().toLowerCase(), mobile, city, state, longitude, latitude);
        Key<BusinessAdminAccount> businessAdminAccountKey
        = OfyService.ofy().save().entity(account).now();
        account = OfyService.ofy().load().key(businessAdminAccountKey).now();
        return new BusinessAccountResult(account, ServerInteractionReturnStatus.SUCCESS);
    }

    @ApiMethod(name = "addDisaggregationPolicy")
    public DisaggregationEntity addDisaggregationPolicy(@Named("aggregateItemId") Long aggregateItemId,
                                                        @Named("aggregateItemMeasurementUnitId") Long aggregateItemMeasurementUnitId,
                                                        @Named("disaggregateItemId") Long disaggregateItemId,
                                                        @Named("disAggregateItemMeasurementUnitId") Long disAggregateItemMeasurementUnitId,
                                                        @Named("factor") Float factor,
                                                        User user) throws ForbiddenException, OAuthRequestException {
        BusinessAdminAccount account = findBusinessUser(user);
        DisaggregationEntity disaggregationEntity
                 = new DisaggregationEntity(account.getId(),
                aggregateItemId,
                aggregateItemMeasurementUnitId,
                disaggregateItemId,
                disAggregateItemMeasurementUnitId,
                factor);
        Key<DisaggregationEntity> disaggregationEntityKey
        = OfyService.ofy().save().entity(disaggregationEntity).now();
        disaggregationEntity = OfyService.ofy().load().key(disaggregationEntityKey).now();
        return disaggregationEntity;
    }

    @ApiMethod(name = "getAllDisaggregationPolicies")
    public DisaggregationEntity[] getAllDisaggregationPolicies(User user) throws ForbiddenException, OAuthRequestException {

        BusinessAdminAccount account = findBusinessUser(user);
        List<DisaggregationEntity> disaggregationEntities
                = OfyService.ofy().load().type(DisaggregationEntity.class).filter("businessId", account.getId()).list();
        return disaggregationEntities.toArray(new DisaggregationEntity[disaggregationEntities.size()]);
    }

    @ApiMethod(name = "disAggregateItem")
    public DisaggregationEntity[] disAggregateItem(@Named("itemId") Long itemId,
                                                   @Named("measurementUnitId") Long measurementUnitId,
                                                   User user)
            throws ForbiddenException,
            OAuthRequestException,
            InvalidMasterItemException,
            MasterItemStockZeroException,
            CantBeDisaggregatedException,
            NoDisaggregationPolicyException {

        BusinessAdminAccount account = findBusinessUser(user);
        Key<MasterItem> masterItemKey = Key.create(MasterItem.class, itemId);
        MasterItem masterItem
                = OfyService
                .ofy()
                .load()
                .key(masterItemKey)
                .now();
        if (masterItem.getFractional()) {
            throw new CantBeDisaggregatedException(itemId);
        }
        MeasurementUnit measurementUnit
                = OfyService
                .ofy()
                .load()
                .key(Key.create(MeasurementUnit.class, measurementUnitId))
                .now();
        if (masterItem == null || measurementUnit == null) {
            throw new InvalidMasterItemException(String.format("Master item %d not found with unit %d", itemId, measurementUnitId));
        }
        List<MasterStockEntity> masterStockEntities
                = OfyService.ofy().load().type(MasterStockEntity.class).filter("itemId", itemId).list();
        if (masterStockEntities == null ||
                masterStockEntities.size() == 0 ||
                (masterStockEntities.size() == 1 && masterStockEntities.get(0).getStock() != 0)) {
            throw new MasterItemStockZeroException(itemId);
        }
        MasterStockEntity masterStockEntity = masterStockEntities.iterator().next();
        List<DisaggregationEntity> disaggregationEntities
                = OfyService
                .ofy()
                .load()
                .type(DisaggregationEntity.class)
                .filter("businessId", account.getId())
                .filter("aggregatedItemId", itemId)
                .filter("aggregateItemMeasurementUnitId", measurementUnitId)
                .list();
        if (disaggregationEntities == null || disaggregationEntities.size() == 0) {
            throw new NoDisaggregationPolicyException(itemId);
        }
        DisaggregationEntity disaggregationEntity = disaggregationEntities.iterator().next();
        MasterStockEntity disaggregatedItem
                = new MasterStockEntity(account.getId(),
                disaggregationEntity.getDisaggregateItemId(),
                disaggregationEntity.getDisAggregateItemMeasurementUnitId(),
                disaggregationEntity.getFactor());
        masterStockEntity.setStock(masterStockEntity.getStock() - 1);
        if (masterStockEntity.getStock() == 0) {
            OfyService.ofy().delete().entity(masterStockEntity);
        }
        else {

            OfyService.ofy().save().entity(masterStockEntity);
        }
        OfyService.ofy().save().entity(disaggregatedItem);
        return disaggregationEntities.toArray(new DisaggregationEntity[disaggregationEntities.size()]);
    }

    @ApiMethod(name = "registerMasterItem")
    public MasterItem registerMasterItem(@Named("name") String name,
                                         @Named("description") String description,
                                         @Named("upc") String upc,
                                         @Named("fractional") Boolean fractional,
                                         @Named("imageType") String imageType,
                                         @Named("imageCloudKey") String imageCloudKey,
                                         @Named("measurementCategoryId") Long measurementCategoryId,
                                         @Named("taxSurchargeId") Long taxSurchargeId,
                                         User user) throws ForbiddenException, OAuthRequestException {

        BusinessAdminAccount account = authorizeBusinessUser(user);
        if (account == null) {
            throw new ForbiddenException(user.getEmail() + " is an invalid user.");
        }
        MasterItem masterItem = new MasterItem(account.getId(),
                name,
                description,
                upc,
                fractional,
                imageType,
                imageCloudKey,
                user.getEmail(),
                taxSurchargeId);
        Key<MasterItem> masterItemKey = OfyService.ofy().save().entity(masterItem).now();
        return OfyService.ofy().load().key(masterItemKey).now();
    }

    @ApiMethod(name = "createSalesInvoice")
    public SalesInvoice createSalesInvoice(@Named("businessId") Long businessId,
                                           @Named("buyerId") Long buyerId,
                                           @Named("sellerId") Long sellerId,
                                           @Named("userId") Long userId,
                                           @Named("pricePostTax") Boolean pricePostTax,
                                           User user)
            throws ForbiddenException, OAuthRequestException {

        BusinessAdminAccount account = authorizeBusinessUser(user);
        if (account == null) {
            throw new ForbiddenException(user.getEmail() + " is an invalid user.");
        }
        SalesInvoice salesInvoice = new SalesInvoice(businessId, buyerId, sellerId, userId, pricePostTax);
        Key<SalesInvoice> salesInvoiceKey = OfyService.ofy().save().entity(salesInvoice).now();
        return OfyService.ofy().load().key(salesInvoiceKey).now();
    }
    @ApiMethod(name = "addItemToInvoice")
    public InvoiceItem addItemToInvoice(@Named("invoiceId") Long invoiceId,
                                         @Named("masterItemId") Long masterItemId,
                                         @Named("measurementUnitId") Long measurementUnitId,
                                         @Named("price") Float price,
                                         @Named("quantity") Float quantity,
                                        User user)
            throws ForbiddenException,
            OAuthRequestException,
            InvalidInvoiceException,
            InvalidMasterItemException {

        BusinessAdminAccount account = authorizeBusinessUser(user);
        if (account == null) {
            throw new ForbiddenException(user.getEmail() + " is an invalid user.");
        }
        Key<SalesInvoice> salesInvoiceKey = Key.create(SalesInvoice.class, invoiceId);
        SalesInvoice salesInvoice = OfyService.ofy().load().key(salesInvoiceKey).now();
        if (salesInvoice == null) {
            throw new InvalidInvoiceException(invoiceId.toString());
        }
        Key<MasterItem> masterItemKey = Key.create(MasterItem.class, masterItemId);
        MasterItem mi = OfyService.ofy().load().key(masterItemKey).now();
        if (mi == null) {
            throw new InvalidMasterItemException(masterItemId);
        }
        Pair<Float, Float> taxPercentage = computeTaxPercentage(mi.getTaxCategoryId());
        float total = price * quantity;
        if (salesInvoice.getPricePostTax()) {
            total = (price * quantity) * ( (100 -   taxPercentage.getValue1()) / 100);
        }
        float tax = (total * taxPercentage.getValue1() / 100);
        float surcharge = (total * taxPercentage.getValue2() / 100);
        InvoiceItem ii = new InvoiceItem(invoiceId, masterItemId, measurementUnitId, price, quantity, total, tax, surcharge);
        Key<InvoiceItem> invoiceItemKey = OfyService.ofy().save().entity(ii).now();
        return OfyService.ofy().load().key(invoiceItemKey).now();
    }
    @ApiMethod(name = "completeSalesInvoice")
    public SalesInvoice completeSalesInvoice(@Named("invoiceId") Long invoiceId,
                                             User user)
            throws ForbiddenException,
            OAuthRequestException, InvalidInvoiceException {

        BusinessAdminAccount account = authorizeBusinessUser(user);
        if (account == null) {
            throw new ForbiddenException(user.getEmail() + " is an invalid user.");
        }
        Key<SalesInvoice> salesInvoiceKey = Key.create(SalesInvoice.class, invoiceId);
        SalesInvoice salesInvoice = OfyService.ofy().load().key(salesInvoiceKey).now();
        if (salesInvoice == null) {
            throw new InvalidInvoiceException(invoiceId.toString());
        }
        List<InvoiceItem> invoiceItemList
                = OfyService
                .ofy()
                .load()
                .type(InvoiceItem.class)
                .filter("invoiceId", invoiceId)
                .list();
        salesInvoice.setNumberOfItems(invoiceItemList.size());
        float total = 0;
        float totalTax = 0;
        float totalSurcharge = 0;
        for (InvoiceItem ii : invoiceItemList) {
            total += ii.getTotal();
            totalTax += ii.getTax();
            totalSurcharge += ii.getSurcharge();
        }
        float amountPayable = total + totalTax + totalSurcharge;
        salesInvoice.setTotal(total);
        salesInvoice.setTotalTax(totalTax);
        salesInvoice.setTotalSurcharge(totalSurcharge);
        salesInvoiceKey = OfyService.ofy().save().entity(salesInvoice).now();
        return OfyService.ofy().load().key(salesInvoiceKey).now();
    }
    @ApiMethod(name = "getMasterItems")
    public MasterItem[] getMasterItems(User user)
            throws ForbiddenException,
            OAuthRequestException,
            InvalidInvoiceException,
            InvalidMasterItemException {

        BusinessAdminAccount account = authorizeBusinessUser(user);
        List<MasterItem> masterItemList
                = OfyService.ofy().load().type(MasterItem.class).filter("businessId", account.getId()).list();
        return masterItemList.toArray(new MasterItem[masterItemList.size()]);
    }

    @ApiMethod(name = "getAllMeasurementUnits")
    public MeasurementUnit[] getAllMeasurementUnits(User user)
            throws ForbiddenException,
            OAuthRequestException,
            InvalidInvoiceException,
            InvalidMasterItemException {

        BusinessAdminAccount account = authorizeBusinessUser(user);
        List<MeasurementUnit> measurementUnitList
                = OfyService.ofy().load().type(MeasurementUnit.class).filter("businessId", account.getId()).list();
        return measurementUnitList.toArray(new MeasurementUnit[measurementUnitList.size()]);
    }
    @ApiMethod(name = "updateLocation")
    public BusinessAccountResult updateLocation(@Named("latitude") Double latitude,
                                                @Named("longitude") Double longitude,
                                                User user) throws ForbiddenException, OAuthRequestException {

        BusinessAdminAccount account = authorizeBusinessUser(user);
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

        authorizeBusinessUser(user);
        List<UserAccountRegion> regions = OfyService.ofy().load().type(UserAccountRegion.class).list();
        return regions;
    }

    @ApiMethod(name = "getUploadURL")
    public UploadURL getUploadURL(User user) throws OAuthRequestException, ForbiddenException {

        authorizeBusinessUser(user);
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        return new UploadURL(blobstoreService.createUploadUrl(SanjnanConstants.MASTER_ITEM_IMAGE_UPLOAD_URL));
    }
}
