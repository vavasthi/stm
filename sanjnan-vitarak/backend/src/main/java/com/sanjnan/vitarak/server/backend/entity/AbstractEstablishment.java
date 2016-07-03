package com.sanjnan.vitarak.server.backend.entity;

import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.sanjnan.vitarak.server.backend.OfyService;
import com.sanjnan.vitarak.server.backend.utils.AccountType;

/**
 * Created by vavasthi on 5/10/15.
 */
@Entity
public class AbstractEstablishment {
    public AbstractEstablishment() {

    }
    protected AbstractEstablishment(String name,
                                    String address,
                                    String city,
                                    String state,
                                    String email,
                                    String mobile,
                                    Double latitude,
                                    Double longitude,
                                    Boolean locked,
                                    Boolean approved) {

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
        this.latitude = latitude;
        this.longitude = longitude;
        this.locked = locked;
        this.approved = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "AbstractEstablishment{" +
                "accountType=" + accountType +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", region=" + region +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", googleUser=" + googleUser +
                ", locked=" + locked +
                ", approved=" + approved +
                '}';
    }

    @Id
    private Long id;
    @Index
    private String name;
    private String address;
    private UserAccountRegion region;
    @Index
    private String email;
    @Index
    private String mobile;
    private Double latitude;
    private Double longitude;
    @Index
    private Boolean googleUser;
    @Index
    private int accountType;
    @Index
    private Boolean locked;
    @Index
    private Boolean approved;
}
