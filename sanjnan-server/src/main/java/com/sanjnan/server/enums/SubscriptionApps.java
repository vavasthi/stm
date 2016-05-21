/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.enums;

import com.sanjnan.server.pojos.constants.H2OConstants;

import java.util.List;


/**
 * Created by praveen on 12/4/16.
 */

public enum SubscriptionApps {

    GCM(H2OConstants.STR_NOTIFICATION_TYPE_GCM, H2OConstants.SUBSCRIPTION_GCM_APPS),
    APNS(H2OConstants.STR_NOTIFICATION_TYPE_APNS, H2OConstants.SUBSCRIPTION_APNS_APPS);

    private final String appType;
    private final List<String> appIdList;


    SubscriptionApps(String appType, List<String> appIdList) {
        this.appType = appType;
        this.appIdList = appIdList;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public List<String> getValue() {
        return appIdList;
    }
}
