package com.avasthi.android.apps.roadbuddy.backend.endpoints;

import com.avasthi.android.apps.roadbuddy.backend.OfyService;
import com.avasthi.android.apps.roadbuddy.backend.bean.City;
import com.avasthi.android.apps.roadbuddy.backend.bean.Member;
import com.avasthi.android.apps.roadbuddy.backend.exceptions.InvalidMemberException;
import com.avasthi.roadbuddy.common.RBConstants;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.ForbiddenException;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import java.util.logging.Logger;

import javax.inject.Named;


/**
 * An endpoint class we are exposing
 */
@Api(
        name = "roadMeasurementApi",
        version = "v1",
        scopes = {RBConstants.EMAIL_SCOPE},
        audiences = {RBConstants.ANDROID_AUDIENCE},
        clientIds = {RBConstants.WEB_CLIENT_ID, RBConstants.ANDROID_CLIENT_ID},
        namespace = @ApiNamespace(
                ownerDomain = RBConstants.OWNER_DOMAIN,
                ownerName = RBConstants.OWNER_DOMAIN,
                packagePath = ""
        )
)
public class RoadMeasurementBeanEndpoint {

    private static final Logger logger = Logger.getLogger(RoadMeasurementBeanEndpoint.class.getName());

    @ApiMethod(name = "isRegisteredUser")
    public Member isRegisteredMember(User user) throws InvalidMemberException, ForbiddenException, OAuthRequestException {


        return authorizeApi(user);
    }

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "register")
    public Member register(@Named("name") String name,
                           @Named("mobile") String mobile,
                           @Named("city") String city,
                           @Named("state") String state,
                           @Named("detectedCity") String detectedCity,
                           @Named("detectedState") String detectedState,
                           @Named("latitude") Double latitude,
                           @Named("logitude") Double longitude,
                           User user) throws ForbiddenException, OAuthRequestException {
        if (user == null) {
            throw new ForbiddenException("user is null.");
        }
        try {

            Member member = authorizeApi(user);
            throw new ConflictException(user.getEmail() + " already exists.");
        } catch (Exception ex) {

        }
        City c = getCity(city, state);
        City dc = getCity(detectedCity, detectedState);
        Member member = new Member(name, user.getEmail().toLowerCase(), mobile, c.getId(), dc.getId(), latitude, longitude);
        OfyService.ofy().save().entity(member).now();
        return member;
    }

    private Member authorizeApi(com.google.appengine.api.users.User user) throws InvalidMemberException, OAuthRequestException {
        if (user == null) {
            throw new OAuthRequestException("User is null");
        }
        Member member = OfyService.ofy().load().type(Member.class).filter("email", user.getEmail().toLowerCase()).first().now();
        if (member == null) {

            throw new InvalidMemberException(user.getEmail());
        }
        return member;
    }
    private City getCity(String city, String state) {

        city = city.toUpperCase();
        City c = OfyService.ofy().load().type(City.class).filter("city", city).filter("state", state).first().now();
        if (c == null) {
            c = new City(city, state);
            OfyService.ofy().save().entity(c).now();
        }
        return c;
    }

}
