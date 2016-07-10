package com.sanjnan.vitarak.server.backend.endpoints;

import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.googlecode.objectify.Key;
import com.sanjnan.vitarak.server.backend.OfyService;
import com.sanjnan.vitarak.server.backend.entity.BusinessAdminAccount;
import com.sanjnan.vitarak.server.backend.entity.DeliveryAccount;
import com.sanjnan.vitarak.server.backend.entity.TaxCategoryEntity;
import com.sanjnan.vitarak.server.backend.entity.TaxSurchargeEntity;
import com.sanjnan.vitarak.server.backend.exceptions.DeliveryUserDoesntExistException;
import com.sanjnan.vitarak.server.backend.utils.Pair;

/**
 * Created by vavasthi on 3/7/16.
 */
public class BaseEndpoint {

    protected BusinessAdminAccount authorizeBusinessUser(com.google.appengine.api.users.User user) throws ForbiddenException, OAuthRequestException {
        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        BusinessAdminAccount account
                = OfyService
                .ofy()
                .load()
                .type(BusinessAdminAccount.class)
                .filter("email", user.getEmail().toLowerCase())
                .first()
                .now();
        if (account == null) {

            throw new ForbiddenException("User " + user.getUserId() + " is not authorized for admin activities.");
        }
        return account;
    }
    protected DeliveryAccount authorizeDeliveryUser(com.google.appengine.api.users.User user) throws ForbiddenException, OAuthRequestException {
        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        DeliveryAccount account
                = OfyService
                .ofy()
                .load()
                .type(DeliveryAccount.class)
                .filter("email", user.getEmail().toLowerCase())
                .first()
                .now();
        if (account == null) {

            throw new ForbiddenException("User " + user.getUserId() + " is not authorized for delivery activities.");
        }
        return account;
    }
    protected BusinessAdminAccount findBusinessUser(com.google.appengine.api.users.User user) throws ForbiddenException, OAuthRequestException {
        BusinessAdminAccount account
                = OfyService
                .ofy()
                .load()
                .type(BusinessAdminAccount.class)
                .filter("email", user.getEmail().toLowerCase())
                .first()
                .now();
        return account;
    }
    protected DeliveryAccount findDeliveryUser(Long deliveryUserId)
            throws ForbiddenException,
            OAuthRequestException,
            DeliveryUserDoesntExistException {

        Key<DeliveryAccount> deliveryAccountKey = Key.create(DeliveryAccount.class, deliveryUserId);
        DeliveryAccount deliveryAccount =
                OfyService.ofy().load().key(deliveryAccountKey).now();
        if (deliveryAccount == null) {
            throw new DeliveryUserDoesntExistException(String.format("Delivery user doesn't exist for userId %d", deliveryUserId));
        }
        return deliveryAccount;
    }
    protected DeliveryAccount findDeliveryUser(String email) throws ForbiddenException, OAuthRequestException {
        DeliveryAccount account
                = OfyService
                .ofy()
                .load()
                .type(DeliveryAccount.class)
                .filter("email", email.toLowerCase())
                .first()
                .now();
        return account;
    }
    protected  Pair<Float, Float> computeTaxPercentage(Long taxCategoryId) {
        Key<TaxCategoryEntity> taxCategoryEntityKey
                = Key.create(TaxCategoryEntity.class, taxCategoryId);
        TaxCategoryEntity taxCategoryEntity = OfyService.ofy().load().key(taxCategoryEntityKey).now();
        float surcharge = 0;
        for (Long taxSurchargeId : taxCategoryEntity.getSurchargeEntitySet()) {
            Key<TaxSurchargeEntity> taxSurchargeEntityKey
                    = Key.create(TaxSurchargeEntity.class, taxSurchargeId);
            TaxSurchargeEntity taxSurchargeEntity
                    = OfyService.ofy().load().key(taxSurchargeEntityKey).now();
            surcharge += taxSurchargeEntity.getSurchargeRate();
        }
        return new Pair<>(taxCategoryEntity.getTaxRate(), surcharge);
    }
}
