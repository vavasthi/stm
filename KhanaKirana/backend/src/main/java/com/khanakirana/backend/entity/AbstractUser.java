package com.khanakirana.backend.entity;

import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.khanakirana.backend.OfyService;
import com.khanakirana.backend.utils.AccountType;

/**
 * Created by vavasthi on 5/10/15.
 */
@Entity
public class AbstractUser {
    enum ACCOUNT_TYPE {
        CUSTOMER,
        BUSINESs
    }
    public AbstractUser() {

    }
    protected AbstractUser(String name, String address, String city, String state, String email, String mobile, String password, Double latitude, Double longitude, Boolean googleUser, AccountType accountType, Boolean locked) {

        this.name = name;
        this.address = address;
        Objectify ofy = OfyService.ofy();
        Query.Filter regionFilter = Query.CompositeFilterOperator.and(new Query.FilterPredicate("state", Query.FilterOperator.EQUAL, state.toUpperCase()),
                new Query.FilterPredicate("city", Query.FilterOperator.EQUAL, city.toUpperCase()));
        UserAccountRegion region = OfyService.ofy().load().type(UserAccountRegion.class).filter(regionFilter).first().now();
        if (region == null) {
            region = new UserAccountRegion(city.toUpperCase(), state.toUpperCase());
            ofy.save().entity(region).now();
        }
        this.region = region;
        this.region = region;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
        this.googleUser = googleUser;
        this.accountType = accountType.type;
        this.locked = locked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserAccountRegion getRegion() {
        return region;
    }

    public void setRegion(UserAccountRegion region) {
        this.region = region;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getGoogleUser() {
        return googleUser;
    }

    public void setGoogleUser(Boolean googleUser) {
        this.googleUser = googleUser;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "AbstractUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", region=" + region +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", googleUser=" + googleUser +
                ", accountType=" + accountType +
                '}';
    }

    @Id
    private String id;
    @Index
    private String name;
    private String address;
    private UserAccountRegion region;
    @Index
    private String email;
    @Index
    private String mobile;
    private String password;
    private Double latitude;
    private Double longitude;
    @Index
    private Boolean googleUser;
    @Index
    private int accountType;
    private Boolean locked;

}
