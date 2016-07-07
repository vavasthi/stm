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
import com.googlecode.objectify.Key;
import com.sanjnan.vitarak.common.SanjnanConstants;
import com.sanjnan.vitarak.server.backend.OfyService;
import com.sanjnan.vitarak.server.backend.entity.TaxCategoryEntity;
import com.sanjnan.vitarak.server.backend.entity.TaxSurchargeEntity;
import com.sanjnan.vitarak.server.backend.exceptions.InvalidUserAccountException;

import java.util.List;
import java.util.logging.Logger;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "taxationApi",
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
public class TaxationEndpoint extends BaseEndpoint{

    private final static Logger logger = Logger.getLogger(TaxationEndpoint.class.getName());

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "findAllTaxCategories")
    public List<TaxCategoryEntity> findAllTaxCategories(User user) throws InvalidUserAccountException, ForbiddenException, OAuthRequestException {

        authorizeBusinessUser(user);
        return OfyService.ofy().load().type(TaxCategoryEntity.class).list();
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "findAllTaxSurchargeCategories")
    public List<TaxSurchargeEntity> findAllTaxSurchargeCategories(User user) throws InvalidUserAccountException, ForbiddenException, OAuthRequestException {

        authorizeBusinessUser(user);
        return OfyService.ofy().load().type(TaxSurchargeEntity.class).list();
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "addTaxCategory")
    public TaxCategoryEntity addTaxCategory(TaxCategoryEntity taxCategoryEntity, User user) throws InvalidUserAccountException, ForbiddenException, OAuthRequestException {

        authorizeBusinessUser(user);
        Key<TaxCategoryEntity> taxCategoryEntityKey = OfyService.ofy().save().entity(taxCategoryEntity).now();
        return OfyService.ofy().load().key(taxCategoryEntityKey).now();
    }
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "addTaxSurchageCategory")
    public TaxSurchargeEntity addTaxSurchargeCategory(TaxSurchargeEntity taxSurchargeEntity, User user) throws InvalidUserAccountException, ForbiddenException, OAuthRequestException {

        authorizeBusinessUser(user);
        Key<TaxSurchargeEntity> taxSurchargeEntityKey = OfyService.ofy().save().entity(taxSurchargeEntity).now();
        return OfyService.ofy().load().key(taxSurchargeEntityKey).now();
    }
}
