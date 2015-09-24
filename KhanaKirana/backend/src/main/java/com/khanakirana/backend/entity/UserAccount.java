package com.khanakirana.backend.entity;

import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.khanakirana.backend.OfyService;

/**
 * The object model for the data we are sending through endpoints
 */
@Entity
public class UserAccount {
    public UserAccount() {
    }

    public UserAccount(String fullname, String address, String email, String mobile, String password, String city, String state, Double latitude, Double longitude,Boolean googleUser) {
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        Objectify ofy = OfyService.ofy();
        Query.Filter regionFilter = Query.CompositeFilterOperator.and(new Query.FilterPredicate("state", Query.FilterOperator.EQUAL, state.toUpperCase()),
                new Query.FilterPredicate("city", Query.FilterOperator.EQUAL, city.toUpperCase()));
        UserAccountRegion region = OfyService.ofy().load().type(UserAccountRegion.class).filter(regionFilter).first().now();
        if (region == null) {
            region = new UserAccountRegion(city.toUpperCase(), state.toUpperCase());
            ofy.save().entity(region).now();
        }
        this.region = region;
        this.latitude = latitude;
        this.longitude = longitude;
        this.googleUser = googleUser;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public UserAccountRegion getRegion() {
        return region;
    }

    public void setRegion(UserAccountRegion region) {
        this.region = region;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSystemAdministrator() {
        return systemAdministrator;
    }

    public void setSystemAdministrator(Boolean systemAdministrator) {
        this.systemAdministrator = systemAdministrator;
    }

    public Boolean getBusinessAdministrator() {
        return businessAdministrator;
    }

    public void setBusinessAdministrator(Boolean businessAdministrator) {
        this.businessAdministrator = businessAdministrator;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "mobile='" + mobile + '\'' +
                ", fullname='" + fullname + '\'' +
                ", address='" + address + '\'' +
                ", region=" + region +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", googleUser=" + googleUser +
                '}';
    }

    @Id
    private String mobile;
    @Index
    private String fullname;
    private String address;
    private UserAccountRegion region;
    @Index
    private String email;
    private String password;
    private Double latitude;
    private Double longitude;
    @Index
    private Boolean systemAdministrator;
    @Index
    private Boolean businessAdministrator;
    @Index
    private Boolean googleUser;
}