package com.sanjnan.vitarak.server.backend.endpoints;

import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.googlecode.objectify.Key;
import com.sanjnan.vitarak.server.backend.OfyService;
import com.sanjnan.vitarak.server.backend.entity.BusinessEstablishmentAccount;
import com.sanjnan.vitarak.server.backend.entity.TaxCategoryEntity;
import com.sanjnan.vitarak.server.backend.entity.TaxSurchargeEntity;
import com.sanjnan.vitarak.server.backend.utils.Pair;

/**
 * Created by vavasthi on 3/7/16.
 */
public class BaseEndpoint {

    protected BusinessEstablishmentAccount authorizeBusinessUser(com.google.appengine.api.users.User user) throws ForbiddenException, OAuthRequestException {
        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        BusinessEstablishmentAccount account
                = OfyService
                .ofy()
                .load()
                .type(BusinessEstablishmentAccount.class)
                .filter("email", user.getEmail().toLowerCase())
                .first()
                .now();
        if (account == null) {

            throw new ForbiddenException("User " + user.getUserId() + " is not authorized for admin activities.");
        }
        return account;
    }
    protected BusinessEstablishmentAccount findBusinessUser(com.google.appengine.api.users.User user) throws ForbiddenException, OAuthRequestException {
        BusinessEstablishmentAccount account
                = OfyService
                .ofy()
                .load()
                .type(BusinessEstablishmentAccount.class)
                .filter("email", user.getEmail().toLowerCase())
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
