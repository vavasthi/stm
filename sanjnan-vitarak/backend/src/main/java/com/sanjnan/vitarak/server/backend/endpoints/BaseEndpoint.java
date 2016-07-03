package com.sanjnan.vitarak.server.backend.endpoints;

import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.sanjnan.vitarak.server.backend.OfyService;
import com.sanjnan.vitarak.server.backend.entity.BusinessEstablishmentAccount;

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
}
