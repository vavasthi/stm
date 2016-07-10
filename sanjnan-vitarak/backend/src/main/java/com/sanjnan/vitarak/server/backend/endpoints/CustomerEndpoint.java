/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.sanjnan.vitarak.server.backend.endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;
import com.sanjnan.vitarak.common.SanjnanConstants;
import com.sanjnan.vitarak.server.backend.OfyService;
import com.sanjnan.vitarak.server.backend.entity.AccountAccount;
import com.sanjnan.vitarak.server.backend.entity.UserAccountRegion;
import com.sanjnan.vitarak.server.backend.exceptions.InvalidUserAccountException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "customerApi",
        version = "v1",
        scopes = {SanjnanConstants.EMAIL_SCOPE},
        audiences = {SanjnanConstants.ANDROID_AUDIENCE},
        clientIds = {SanjnanConstants.WEB_CLIENT_ID, SanjnanConstants.CUSTOMER_ANDROID_CLIENT_ID},
        namespace = @ApiNamespace(
                ownerDomain = "backend.server.vitarak.sanjnan.com",
                ownerName = "backend.server.vitarak.sanjnan.com",
                packagePath = ""
        )
)
public class CustomerEndpoint {

    private final static Logger logger = Logger.getLogger(CustomerEndpoint.class.getName());

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "register")
    public AccountAccount register(@Named("name") String name,
                                   @Named("address") String address,
                                   @Named("mobile") String mobile,
                                   @Named("city") String city,
                                   @Named("state") String state,
                                   @Named("latitude") Double latitude,
                                   @Named("longitude") Double longitude,
                                   User user) throws ForbiddenException {
        if (user == null) {
            throw new ForbiddenException("user is null.");
        }
        AccountAccount account = new AccountAccount(name, address, user.getEmail().toLowerCase(), mobile, city, state, longitude, latitude);
        OfyService.ofy().save().entity(account).now();
        return account;
    }

    @ApiMethod(name = "authenticate")
    public AccountAccount authenticate(@Named("email") String email,
                                       @Named("password") String password,
                                       @Named("googleUser") Boolean googleUser) {
        try {
            email = email.toLowerCase();
            AccountAccount account = OfyService.ofy().load().type(AccountAccount.class).filter("email", email).first().now();
            logger.log(Level.INFO, "User Account retrieved." + account.toString());
            if (account == null) {

                throw new InvalidUserAccountException();
            }
            return account;

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception thrown by the load.", e);
        }
        return null;
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
    public AccountAccount isRegisteredUser(User user) throws InvalidUserAccountException, OAuthRequestException, ForbiddenException {

        authorizeApi(user);
        try {

            AccountAccount account = OfyService.ofy().load().type(AccountAccount.class).filter("email", user.getEmail().toLowerCase()).first().now();
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


    private void authorizeApi(User user) throws ForbiddenException, OAuthRequestException {
        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        List<AccountAccount> accounts = OfyService.ofy().load().type(AccountAccount.class).filter("email", user.getEmail().toLowerCase()).list();
        if (accounts.size() == 0) {
            throw new ForbiddenException("User " + user.getUserId() + " is not authorized for customer activities.");
        }

    }
}
