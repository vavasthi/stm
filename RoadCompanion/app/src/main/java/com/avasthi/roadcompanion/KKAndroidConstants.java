package com.avasthi.roadcompanion;


import com.khanakirana.admin.khanakirana.BuildConfig;

/**
 * Created by vavasthi on 19/9/15.
 */
public class KKAndroidConstants {

    static final String SENDER_ID = BuildConfig.SENDER_ID;

    /**
     * Web client ID from Google Cloud console.
     */
    static final String WEB_CLIENT_ID = BuildConfig.WEB_CLIENT_ID;

    /**
     * The web client ID from Google Cloud Console.
     */
    static final String AUDIENCE_ANDROID_CLIENT_ID =
            "server:client_id:" + WEB_CLIENT_ID;

    /**
     * The URL to the API. Default when running locally on your computer:
     * "http://10.0.2.2:8080/_ah/api/"
     */
    public static final String ROOT_URL = BuildConfig.ROOT_URL;

    /**
     * Defines whether authentication is required or not.
     */
    public static final boolean SIGN_IN_REQUIRED = BuildConfig.SIGN_IN_REQUIRED;

    public static final int REQUEST_ACCOUNT_PICKER = 2;
    public static final int CAMERA_REQUEST = 1888;
    public static final int SCAN_REQUEST = 1999;
    public static final int ADD_MEASUREMENT_CATEGORY_REQUEST = 2111;
    public static final int ADD_MEASUREMENT_UNIT_REQUEST = 2112;
    public static final int BARCODE_SCAN_REQUEST = 0x0000c0de; // Only use bottom 16 bits
    public static final int ADD_ITEM_CATEGORY_REQUEST = 2123;
    public static final int MAP_POLYGON_FOR_BUSINESS = 2124;
    public static final String BLOB_STORE_MASTER_LIST_PREFIX = "MasterList-Item-";
    public static final String BLOB_STORE_KEY_NAME = "name";
    public static final String BLOB_STORE_KEY_FILE = "file";
    public static final String BLOB_CLOUD_KEY = "X-KhanaKirana-Blob-Cloud-key";


    /**
     * Default constructor, never called.
     */
    private KKAndroidConstants() { }

}
