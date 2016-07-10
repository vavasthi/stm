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
import com.sanjnan.vitarak.common.SanjnanConstants;
import com.sanjnan.vitarak.common.ServerInteractionReturnStatus;
import com.sanjnan.vitarak.server.backend.OfyService;
import com.sanjnan.vitarak.server.backend.entity.BusinessAccountResult;
import com.sanjnan.vitarak.server.backend.entity.BusinessAdminAccount;
import com.sanjnan.vitarak.server.backend.entity.DeliveryAccount;
import com.sanjnan.vitarak.server.backend.entity.DeliveryTripEntity;
import com.sanjnan.vitarak.server.backend.entity.InvoiceItem;
import com.sanjnan.vitarak.server.backend.entity.MasterItem;
import com.sanjnan.vitarak.server.backend.entity.MeasurementUnit;
import com.sanjnan.vitarak.server.backend.entity.SalesInvoice;
import com.sanjnan.vitarak.server.backend.entity.UserAccountRegion;
import com.sanjnan.vitarak.server.backend.exceptions.DeliveryTripAlreadyExistsException;
import com.sanjnan.vitarak.server.backend.exceptions.DeliveryUserDoesntExistException;
import com.sanjnan.vitarak.server.backend.exceptions.InvalidInvoiceException;
import com.sanjnan.vitarak.server.backend.exceptions.InvalidMasterItemException;
import com.sanjnan.vitarak.server.backend.exceptions.InvalidUserAccountException;
import com.sanjnan.vitarak.server.backend.exceptions.MeasurementCategoryAlreadyExists;
import com.sanjnan.vitarak.server.backend.exceptions.MeasurementCategoryDoesntExist;
import com.sanjnan.vitarak.server.backend.exceptions.MeasurementPrimaryUnitException;
import com.sanjnan.vitarak.server.backend.jsonresource.UploadURL;
import com.sanjnan.vitarak.server.backend.utils.Pair;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "deliveryTripApi",
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
public class DeliveryTripEndpoint extends BaseEndpoint {

    private final static Logger logger = Logger.getLogger(DeliveryTripEndpoint.class.getName());

    /**
     * This endpoint is called to check if the user is a registered user or not.
     *
     * @param user
     * @return
     * @throws InvalidUserAccountException
     * @throws ForbiddenException
     * @throws OAuthRequestException
     */
    @ApiMethod(httpMethod = "POST", name = "createDeliveryTrip")
    public DeliveryTripEntity createDeliveryTrip(@Named("name") String name,
                                                 @Named("deliveryUserId") Long deliveryUserId,
                                                 User user)
            throws InvalidUserAccountException,
            ForbiddenException,
            OAuthRequestException,
            DeliveryUserDoesntExistException,
            DeliveryTripAlreadyExistsException {

        BusinessAdminAccount account = authorizeBusinessUser(user);
        DeliveryAccount deliveryAccount = findDeliveryUser(deliveryUserId);
        List<DeliveryTripEntity> deliveryTripEntityList
                = OfyService
                .ofy()
                .load()
                .type(DeliveryTripEntity.class)
                .filter("businessId", account.getId())
                .filter("userId", deliveryUserId)
                .filter("done", false)
                .list();
        if (deliveryTripEntityList != null && deliveryTripEntityList.size() > 0) {
            throw new DeliveryTripAlreadyExistsException(String.format("Delivery Trip already exists for user %s", deliveryAccount.getEmail()));
        }
        DeliveryTripEntity deliveryTripEntity = new DeliveryTripEntity(account.getId(), deliveryUserId, name, new Date());
        Key<DeliveryTripEntity> deliveryTripEntityKey
                = OfyService.ofy().save().entity(deliveryTripEntity).now();
        return OfyService.ofy().load().key(deliveryTripEntityKey).now();
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
        OfyService.ofy().save().entity(account).now();
        return new BusinessAccountResult(account, ServerInteractionReturnStatus.SUCCESS);
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
