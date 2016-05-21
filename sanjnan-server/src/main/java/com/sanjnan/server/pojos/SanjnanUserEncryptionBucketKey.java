/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.pojos;

import java.io.Serializable;

/**
 * Created by vinay on 3/24/16.
 */
public class SanjnanUserEncryptionBucketKey implements Serializable {

    public static final String H2O_USER_ENCRYPTION_BUCKET_KEY_PREFIX = "H2O_USER_ENCRYPTION_BUCKET_KEY_PREFIX_";
    public static final int H2O_NUMBER_OF_KEY_BUCKETS = 0;
    public static final String h2OUserKeyAliasPrefix = "h2o/users/buckets/";
    private int bucketNumber;
    private byte[] key;
    private String alias;

    public SanjnanUserEncryptionBucketKey(final int bucketNumber, final byte[] key, final String alias) {
        this.bucketNumber = bucketNumber;
        this.key = key;
        this.alias = alias;
    }

    public SanjnanUserEncryptionBucketKey() {
    }

    public static String getAliasNameForBucket(final int bucketNumber) {

        return h2OUserKeyAliasPrefix + bucketNumber;
    }

    public static String getCacheKeyForBucket(final int bucketNumber) {
        return H2O_USER_ENCRYPTION_BUCKET_KEY_PREFIX + bucketNumber;
    }

    public int getBucketNumber() {
        return bucketNumber;
    }

    public void setBucketNumber(final int bucketNumber) {
        this.bucketNumber = bucketNumber;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(final byte[] key) {
        this.key = key;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(final String alias) {
        this.alias = alias;
    }
}
