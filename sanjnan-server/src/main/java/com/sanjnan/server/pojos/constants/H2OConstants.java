/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.pojos.constants;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vinay on 1/4/16.
 */
public class H2OConstants {

    
    public static  final TreeMap<String,List<String>> deviceCapability = new TreeMap<>();



  public static final int NOTIFICATION_POOL=500;
  public static final String H2O_INTERNAL_DEFAULT_USER = "Hubble";
  public static final String H2O_INTERNAL_DEFAULT_PASSWORD = "Hobble";
  public static final String H2O_INTERNAL_TENANT = "internal";
  public static final String H2O_INTERNAL_ADMIN_EMAIL = "h2o-admin@hubblehome.com";
  public static final String H2O_DEFAULT_COMPUTE_REGION_NAME = "default";
  public static final String H2O_MAINAPP_TIMEZONE_CACHE_NAME = "H2O_MAINAPP_TIMEZONE_CACHE";
  public static final String H2O_MAINAPP_TIMEZONE_CACHE_PREFIX = "MAINAPP_TIMEZONE:";
  public static final String H2O_INTERNAL_CACHE_NAME = "H2O_INTERNAL_TOKEN_CACHE";
  public static final String H2O_TENANT_CACHE_NAME = "H2O_TENANT_CACHE_NAME";
  public static final String H2O_ACCOUNT_CACHE_NAME = "H2O_ACCOUNT_CACHE_NAME";
  public static final String H2O_TEMP_AUTH_TOKEN_CACHE_NAME = "H2O_TEMP_AUTH_TOKEN_CACHE_NAME";
  public static final String H2O_DEVICE_CACHE_NAME = "H2O_DEVICE_CACHE_NAME";
  public static final String H2O_INTERNAL_CACHE_PREFIX = "H2O_INTERNAL_CACHE_";
  public static final String H2O_TENANT_CACHE_PREFIX = "H2O_TENANT_CACHE_PREFIX_";
  public static final String H2O_ACCOUNT_CACHE_PREFIX = "H2O_ACCOUNT_CACHE_PREFIX_";
  public static final String H2O_TEMP_AUTH_TOKEN_CACHE_PREFIX = "H2O_TEMP_AUTH_TOKEN_CACHE_PREFIX_";
  public static final String H2O_DEVICE_CACHE_PREFIX = "H2O_DEVICE_CACHE_PREFIX_";
  public static final String H2O_MAINAPP_DEVICE_CACHE_NAME = "H2O_MAINAPP_DEVICE_CACHE";
  public static final String H2O_MAINAPP_DEVICE_CACHE_PREFIX = "MAINAPP_DEVICE:";
  public static final String H2O_MAINAPP_USER_CACHE_NAME = "H2O_MAINAPP_USER_CACHE";
  public static final String H2O_MAINAPP_USER_CACHE_PREFIX = "MAINAPP_USER:";
  public static final String H2O_MAINAPP_RELAY_SESSION_CACHE_NAME = "H2O_MAINAPP_RELAY_SESSION_CACHE";
  public static final String H2O_MAINAPP_RELAY_SESSION_CACHE_PREFIX = "MAINAPP_RELAY_SESSION:";
  public static final String H2O_MAINAPP_JOB_CACHE_NAME = "H2O_MAINAPP_JOB_CACHE";
  public static final String H2O_MAINAPP_JOB_CACHE_PREFIX = "MAINAPP_JOB:";
  public static final String H2O_MAINAPP_DEVICE_MODEL_CACHE_NAME = "H2O_DEVICE_MODEL_JOB_CACHE";
  public static final String H2O_MAINAPP_DEVICE_MODEL_CACHE_PREFIX = "DEVICE_MODEL_JOB:";
  public static final String H2O_MAINAPP_DEVICE_TYPE_CACHE_NAME = "H2O_DEVICE_TYPE_JOB_CACHE";
  public static final String H2O_MAINAPP_DEVICE_TYPE_CACHE_PREFIX = "DEVICE_TYPE_CACHE:";
  public static final String H2O_MAINAPP_STREAM_JOB_CACHE_NAME = "H2O_MAINAPP_STREAM_JOB_CACHE";
  public static final String H2O_MAINAPP_STREAM_JOB_CACHE_PREFIX = "MAINAPP_STREAM_JOB:";
  public static final String H2O_MAINAPP_APP_CACHE_NAME = "H2O_MAINAPP_APP_CACHE";
  public static final String H2O_MAINAPP_APP_CACHE_PREFIX = "MAINAPP_APP:";
  public static final String H2O_MAINAPP_SNS_CACHE_NAME = "H2O_MAINAPP_SNS_CACHE";
  public static final String H2O_MAINAPP_SNS_CACHE_PREFIX = "MAINAPP_SNS:";
  public static final String H2O_MAINAPP_DEVICE_MASTER_CACHE_NAME = "H2O_MAINAPP_DEVICE_MASTER_CACHE";
  public static final String H2O_MAINAPP_DEVICE_MASTER_CACHE_PREFIX = "MAINAPP_DEVICE_MASTER_CACHE:";
  public static final String H2O_MAINAPP_SUBSCRIPTION_PLAN_CACHE_NAME = "H2O_MAINAPP_SUBSCRIPTION_PLAN_CACHE";
  public static final String H2O_MAINAPP_SUBSCRIPTION_PLAN_CACHE_PREFIX = "MAINAPP_SUBSCRIPTION_PLAN:";
  public static final String H2O_MAINAPP_KEY_MANAGEMENT_CACHE_NAME = "H2O_MAINAPP_KEY_MANAGEMENT_CACHE";
  public static final String H2O_MAINAPP_KEY_MANAGEMENT_CACHE_PREFIX = "MAINAPP_KEY_MANAGEMENT:";
  public static final String H2O_MAINAPP_WOWZA_CACHE_NAME = "H2O_MAINAPP_WOWZA_CACHE";
  public static final String H2O_MAINAPP_WOWZA_CACHE_PREFIX = "MAINAPP_WOWZA:";
  public static final String H2O_MAINAPP_MQTT_CACHE_NAME = "H2O_MAINAPP_MQTT_CACHE";
  public static final String H2O_MAINAPP_MQTT_CACHE_PREFIX = "MAINAPP_MQTT:";
  public static final String H2O_DEFAULT_API_URL = "https://api.hubble.in";
  public static final String TOKEN_CACHE_NAME = "TOKEN_CACHE_NAME";
  public static final int DEFAULT_TOKEN_EXPIRY = 7 * 24 * 60 * 60;
  public static final String H2O_DEFAULT_TENANT = "hubble";
  public static final String AUTH_USERNAME_HEADER = "X-Auth-Username";
  public static final String AUTH_PASSWORD_HEADER = "X-Auth-Password";
  public static final String AUTH_TENANT_HEADER = "X-Auth-Tenant";
  public static final String AUTH_TOKEN_HEADER = "X-Auth-Token";
  public static final String AUTH_TOKEN_TYPE_HEADER = "X-Auth-Token-Type";
  public static final String AUTH_AUTHORIZATION_HEADER = "Authorization";
  public static final String AUTH_APPLICATION_ID_HEADER = "X-Application-Id";
  public static final String TENANT_PARAMETER_PATTERN = "{tenant}";
  public static final String VERSION_1 = "/v1";
  public static final String VERSION_2 = "/v2";
  public static final String VERSION_3 = "/v3";
  public static final String VERSION_4 = "/v4";
  public static final String VERSION_5 = "/v5";
  public static final String VERSION_6 = "/v6";
  public static final String APPLICATION_API_KEY_VARIABLE_NAME = "api_key";
  public static final String DEVIE_AUTH_KEY_VARIABLE_NAME = "auth_token";
  public static final String V6_TENANTS_ENDPOINT = VERSION_6 + "/internal/tenants";
  public static final String V6_COMPUTE_REGION_ENDPOINT = VERSION_6 + "/internal/computeregions";
  public static final String V6_SETUP_ENDPOINT = VERSION_6 + "/setup";
  public static final String V6_ACCOUNTS_ENDPOINT = VERSION_6 + "/{tenant}/accounts";
  public static final String V6_ADMINS_ENDPOINT = VERSION_6 + "/admins";
  public static final String V6_AUTHENTICATE_URL = VERSION_6 + "/" + TENANT_PARAMETER_PATTERN + "/authenticate";
  public static final String V6_DEVICES_ENDPOINT = VERSION_6 + "/{tenant}/devices";
  public static final String RECURLY_ENDPOINT = "/recurly";
  public static final String USER_ENDPOINT = "/users";
  public static final String TEST_ENDPOINT = "/test";
  public static final String DEVICE_ENDPOINT = "/devices";
  public static final String DEVICE_ATMOSPHERE_ENDPOINT = "/device_atmosphere";
  public static final String JOB_ENDPOINT = "/jobs";
  public static final String APP_ENDPOINT = "/apps";
  public static final String UPLOAD_ENDPOINT = "/uploads";
  public static final String DEVICE_SETTINGS_ENDPOINT = "/device_settings";
  public static final String SNS_ENDPOINT = "/sns";
  public static final String SUBSCRIPTION_PLAN_ENDPOINT = "/subscription_plans";
  public static final String DEVICE_MASTER_BATCH_ENDPOINT = "/device_master_batches";
  public static final String DEVICE_EVENT_ENDPOINT = "/device_events";
  public static final String CAMERA_SERVICE_ENDPOINT = "/BMS/cameraservice";

  public static final String UPLOADS_ENDPOINT = "/v1/uploads";
  public static final String UPLOAD_TOKEN_ENDPOINT = "/v1/users/upload_token";
  public static final String UPLOAD_TOKEN_JSON_ENDPOINT = "/v1/users/upload_token.json";
  public static final String portNumberProperty = "com.hubbleconnected.server.config.portNumber";
  // messages
  public static final String RESET_PASSWORD_TOKEN_NOT_FOUND = "reset_password_token is invalid or already taken";
  public static final int USER_AUTH_TOKEN_LENGTH = 20;
  public static final int DEVICE_AUTH_TOKEN_LENGTH = 16;
  public static final int DEVICE_UPLOAD_TOKEN_LENGTH = 16;
  public static final int DEVICE_UPLOAD_TOKEN_EXPIRES_IN_DAYS = 180; // 6 MONTHS
  public static final String EXCEPTION_URL = "http://monitoreverywhere.com/errors/%s";
  public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
  public static final String DATE_PATTERN_CREATE_EVENT = "yyyy-MM-dd HH:mm:ss";
  public static final long SIX_HOURS = 6 * 60 * 60;
  public static final long SIX_DAYS = 6 * 24 * 60 * 60;
  public static final long HALF_HOUR = 30 * 60;
  public static final long ONE_HOUR = 60 * 60;
  public static final int HTTP_RETRY_COUNT = 5;
  public static final int HTTP_TIMEOUT = 12;
  public static final int[] HTTP_RETRY_RESPONSE_CODES = {499, 500, 501, 502, 503};
  public static final String UPLOAD_SERVER_VERSION = "01.14.00" ;
  public static final String Focus73 = "0073" ;
  public static final String UPLOAD_SERVER_VERSION_0073 = "01.15.16";
  // Spring Boot Actuator services
  public static final String AUTOCONFIG_ENDPOINT = "/autoconfig";
  public static final String BEANS_ENDPOINT = "/beans";
  public static final String CONFIGPROPS_ENDPOINT = "/configprops";
  public static final String ENV_ENDPOINT = "/env";
  public static final String MAPPINGS_ENDPOINT = "/mappings";
  public static final String METRICS_ENDPOINT = "/metrics";
  public static final String SHUTDOWN_ENDPOINT = "/shutdown";
  public static final String ANNOTATION_ROLE_USER = "hasAuthority('user')"; // 0
  public static final String ANNOTATION_ROLE_TALK_BACK_USER = "hasAuthority('talk_back_user')"; // 1
  public static final String ANNOTATION_ROLE_UPLOAD_SERVER = "hasAuthority('upload_server')"; // 2
  public static final String ANNOTATION_ROLE_BP_SERVER = "hasAuthority('bp_server')"; // 3
  public static final String ANNOTATION_ROLE_WOWZA_USER = "hasAuthority('wowza_user')"; // 4
  public static final String ANNOTATION_ROLE_FW_UPGRADE_USER = "hasAuthority('fw_upgrade_user')"; // 5
  public static final String ANNOTATION_ROLE_HELPDESK_AGENT = "hasAuthority('helpdesk_agent')"; // 6
  public static final String ANNOTATION_ROLE_FACTORY_USER = "hasAuthority('factory_user')"; // 7
  public static final String ANNOTATION_ROLE_TESTER = "hasAuthority('tester')"; // 8
  public static final String ANNOTATION_ROLE_MARKETING_ADMIN = "hasAuthority('marketing_admin')"; // 9
  public static final String ANNOTATION_ROLE_FW_UPGRADE_ADMIN = "hasAuthority('fw_upgrade_admin')"; // 10
  public static final String ANNOTATION_ROLE_POLICY_ADMIN = "hasAuthority('policy_admin')"; // 11
  public static final String ANNOTATION_ROLE_ADMIN = "hasAuthority('admin')"; // 12
  public static final String ANNOTATION_ROLE_TENANT_ADMIN = "hasAuthority('tenant_admin')"; // 13
  public static final String ANNOTATION_DEVICE = "hasAuthority('device')"; // 13
  public static final String ANNOTATION_ROLE_ADMIN_AND_TENANT_ADMIN = "hasAuthority('admin') OR hasAuthority('tenant_admin')"; // 14
  public static final String ANNOTATION_ROLE_USER_ADMIN_AND_TENANT_ADMIN
      = "hasAuthority('admin') OR hasAuthority('tenant_admin') OR hasAuthority('user')";
  public static final HashMap<Integer, String> alerts;
  //15
  public static final TreeMap<String, List<HashMap<String, List<String>>>> listMap;
  public static final String UTILS_ENDPOINT = "/utils";
  public static final String MSG_ACCESS_DENIED ="Access Denied. The login-password combination OR the authentication token is invalid.";
  public static final String APP_ID_NOT_FOUND="Not found! App: ";
  public static final String API_KEY_NOT_FOUND="api_key cannot be blank";
  public static final int MAX_ID_VALUE = 2147483646;
  public static final String MSG_SUCCESS = "Success!";
  public static final HashMap<String, String> wowzaIpMapping;
  public static final String DEVICE_TYPE_ENDPOINT = "/device_types";
  public static final String DEVICE_MODEL_ENDPOINT = "/device_models";
  public static final String DELETED_MESSGE="Deleted";
  public static final String AUTHENTICATION_TOKEN = "TrAuVqGVStGWyNgAdDwc";
  public static final String TIMEZONE_GMT ="GMT";
  public static final String CREATE_SESSION_RETRY_INTERVAL="3";
  public static final int FREE_TRIAL_EXPIRY_REMINDER_DAYS=1;
  public static final String AUTH_TOKEN="auth_token";
  public static final String MAC_ADDRESS="mac_address";
  public static final String CLIENT_TYPE="client_type";
  public static final String STREAM_ID="stream_id";
  public static final String JOB_TYPE="job_type";
  public static final String SECURE="secure";
  public static final String CREATE_SESSION = "create_session";
  public static final String CLIENT_TYPE_IOS = "IOS";
  public static final String CLIENT_TYPE_ANDRIOD = "ANDROID";
  public static final String CLIENT_TYPE_BROWSER = "BROWSER";
  public static final String JOB_FAILED = "failed";
  public static final List<String> FILTER_OPERATOR_SUPPORT;
  public static final String DERIVED_KEY_BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789abcdefghijklmnopqrstuvwxyz";
  public static final String DEVICE_ENTITY = "Device";
  public static final String USER_ENTITY = "User";
  public static final String DEVICE_MODEL_ENTITY = "DeviceModel";
  public static final String ENTITY_SUPPORT = "Device";
  public static final Integer NO_FILETER_PARAMETER = 5;
  public static final String INVALID_FILTER_SYNTAX_MESSAGE = "Invalid Filter Syntax";
  public static final String INVALID_FILTER_VALUE_MESSAGE = "Contains invalid value for filter parameters";
  public static final String FILTER_SEPARATOR = ":";
  public static final Integer FILTER_SEPARATOR_LENGTH = 1;
  public static final String APP_DELETED = "App Deleted!";
  //Device Master Batch
  public static final Set<String> VALID_HEADERS = new HashSet<>(Arrays.asList(new String[]{"deviceid,macid,firmware_version,hardware_version,time", "deviceid,macid,firmware_version,hardware_version,order_date"}));
  //devicemaster:
  public static final int MAC_ADDRESS_LENGTH = 12;
  public static final int REGISTRATION_ID_LENGTH = 26;
  public static final int SOUND_EVENT = 1;
  public static final int HIGH_TEMP_EVENT = 2;
  public static final int LOW_TEMP_EVENT = 3;
  public static final int MOTION_DETECT_EVENT = 4;
  public static final String SERVER_VERSION = "_api_staging_";
  public static final int UNKNOWN_STATUS = 100;
  public static final int DEVICE_FOUND = 101;
  public static final int USER_FOUND = 102;
  public static final int DEVICE_NOT_FOUND = 121;
  public static final int UPLOAD_TOKEN_REQUIRED = 122;
  public static final int INVALID_UPLOAD_TOKEN = 123;
  public static final int SERVER_CONNECTION_ERROR = 124;
  public static final int USER_NOT_FOUND = 125;
  public static final int STREAM_NAME_LENGTH = 12;
  public static final int SESSION_KEY_LENGTH = 64;
  public static final String USER_ENDPOINT_AUTHENTICATION_URL = "/v1/users/authentication_token";
  public static final String USER_ENDPOINT_REGISTER_URL = "/v1/users/register";
  public static final String USER_ENDPOINT_AUTHENTICATION_JSON_URL = "/v1/users/authentication_token.json";
  public static final String USER_ENDPOINT_REGISTER_JSON_URL = "/v1/users/register.json";
  public static final String USER_ENDPOINT_FORGOT_PASSWORD_URL = "/v1/users/forgot_password";
  public static final String USER_ENDPOINT_FORGOT_PASSWORD_JSON_URL = "/v1/users/forgot_password.json";
  public static final String DEVICES_ENDPOINT_REGISTER_URL = "/v1/devices/register";
  public static final String DEVICES_ENDPOINT_REGISTER_JSON_URL = "/v1/devices/register.json";
  public static final String DEVICES_ENDPOINT_V4_REGISTER_URL = "/v4/devices/register";
  public static final String DEVICES_ENDPOINT_V4_REGISTER_JSON_URL = "/v4/devices/register.json";
  public static final String BMS_CAMERASERVICE_PREFIX = "/BMS/cameraservice";
  public static final String DEVICE_ENDPOINT_V1_ATMOSPHERE = "/v1/device_atmosphere";
  public static final String DEVICE_ENDPOINT_V2_BOOTUP = "/v2/devices/bootup";
  //  public static final String DEVICE_MASTER_BATCH_ENDPOINT = "/device_master_batches";
  public static final String DEVICE_BASIC_URL = "/v1/devices/([0-9a-zA-Z]+)/basic";
  public static final String DEVICE_BASIC_JSON_URL = "/v1/devices/([0-9a-zA-Z]+)/basic.json";
  public static final String APPS_REGISTER_URL = "/v1/apps/register";
  public static final String APPS_REGISTER_JSON_URL = "/v1/apps/register.json";
  public static final String APPS_REGISTER_NOTIFICATION_URL = "/v1/apps/([0-9]+)/register_notifications";
  public static final String APPS_REGISTER_NOTIFICATION_JSON_URL = "/v1/apps/([0-9]+)/register_notifications.json";
  public static final String FIND_BY_PASSWORD_TOKEN_URL = "/v1/users/find_by_password_token";
  public static final String FIND_BY_PASSWORD_TOKEN_JSON_URL = "/v1/users/find_by_password_token.json";
  public static final String USER_RESET_PASSWORD_URL = "/v1/users/reset_password";
  public static final String USER_RESET_PASSWORD_JSON_URL = "/v1/users/reset_password.json";
  public static final String UTILS_VERSION_URL = "/v1/utils/version";
  public static final String UTILS_VERSION_JSON_URL = "/v1/utils/version.json";
  public static final String DEVICE_SETTINGS_URL = "/v1/device_settings";
  public static final String DEVICE_SETTINGS_JSON_URL = "/v1/device_settings.json";
  public static final String DEVICE_TYPE_NOT_AVAILABLE = "N/A";
  public static final int UPDATING_FIRMWARE_EVENT = 5;
  public static final int SUCCESS_FIRMWARE_EVENT = 6;
  public static final int RESET_PASSWORD_EVENT = 7;
  public static final int DEVICE_REMOVED_EVENT = 8;
  public static final int DEVICE_ADDED_EVENT = 9;
  public static final int CHANGE_TEMPERATURE_EVENT = 11;
  public static final int LENGTH_TEMPERATURE_INFO = 3; // 'old_temperature'-'current_temperature'-'minutes' ;
  //# Alert types 14,15,16 and 25 are used by pet tracker
  public static final int DOOR_MOTION_DETECT_EVENT = 21;
  public static final int TAG_PRESENCE_DETECT_EVENT = 22;
  public static final int SENSOR_PAIRED_EVENT = 23;
  public static final int SENSOR_PAIRED_FAIL_EVENT = 24;
  public static final int SD_CARD_ADDED_EVENT = 26;
  public static final int TAG_LOW_BATTERY_EVENT = 27;
  public static final int NO_TAG_PRESENCE_DETECT_EVENT = 28;
  public static final int DOOR_MOTION_DETECT_OPEN_EVENT = 29;
  public static final int DOOR_MOTION_DETECT_CLOSE_EVENT = 30;
  public static final int OCS_LOW_BATTERY_EVENT = 31;
  public static final int SD_CARD_REMOVED_EVENT = 32;
  public static final int SD_CARD_NEARLY_FULL_EVENT = 33;
  public static final int PRESS_TO_RECORD_EVENT = 34;
  public static final int BABY_SMILE_DETECTION_EVENT = 35;
  public static final int BABY_SLEEPING_CARE_EVENT = 36;
  public static final int SD_CARD_FULL_EVENT = 37;
  public static final int SD_CARD_INSERTED_EVENT = 38;
  
  public static final int SN_MIST_LEVEL_EVENT = 50;
  public static final int SN_HUMIDIFIER_STATUS_EVENT = 51;
  public static final int SN_WATER_LEVEL_EVENT = 52;
  public static final int SN_WEIGHT_EVENT = 53;
  public static final int SN_TEMPERATURE_HIGH_EVENT = 56;
  public static final int SN_HUMIDITY_HIGH_EVENT = 57;
  
  
  public static final int SN_TEMPERATURE_LOW_EVENT = 58;
  public static final int SN_HUMIDITY_LOW_EVENT = 59;
  public static final int SN_NOISE_EVENT = 60;
  public static final int SN_FILTER_CHANGE_EVENT = 61;
  public static final int SN_WEIGHT_ANOMALY_EVENT = 62;
  public static final int MAC_ADDRESS_START_INDEX = 6;
  public static final int MAC_ADDRESS_END_INDEX = 18;
  
  
  
  
  
  
  
  
  
  public static final String SUBSCRIPTION_PLAN_URL = "https://hubbleconnected.com/plans/";
  public static final String FREE_TRIAL_EXPIRED = "free trial expired";
  public static final String FREE_TRIAL_EXPIRY_PENDING = "free trial expiry pending";
  public static final String FREE_TRIAL_APPLIED = "free trial applied";
  public static final String FREE_TRIAL_AVAILABLE = "free trial available";
  public static final String SUBSCRIPTION_CANCELED = "subscription cancelled";
  public static final String SUBSCRIPTION_APPLIED = "subscription applied";
  private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(H2OConstants.class);
  //**************
  private static final String API_PATH = "/api/v1";
  private static final Map<String, String> DEFAULT_APP_UNIQUE_IDS;
  public static String DM_TEMPLATE_NAME = "templateName";
  public static String DM_TEMPLATE_VARS = "templateVars";
  public static String DM_ATTACHMENT_MAP = "attachmentMap";
  public static String DM_TEMPLATE_TYPE = "type";
  public static String DM_TEMPLATE_FILE_NAME = "fileName";
  public static String DM_TEMPLATE_FILE_PATH = "filePath";
  public static String DM_CONTENTTYPE_TEXT = "text/plain";
  public static String DM_TEMPLATE_NAME_SUCCESS_FAIL = "Verify device master report partial";
  public static String DM_TEMPLATE_NAME_SUCCESS = "Verify device master report";
  public static String DM_TEMPLATE_NAME_FAIL = "Verify device master report fail";
  public static String DM_TEMPLATE_NAME_UPLOAD_SUCCESS_FAIL = "Verify device master report partial";
  public static String DM_TEMPLATE_NAME_UPLOAD_SUCCESS = "Verify device master report";
  public static String DM_TEMPLATE_NAME_UPLOAD_FAIL = "Verify device master report fail";
  public static String DM_TEMPLATE_VAR_USER_NAME = "USER_NAME";
  public static String DM_TEMPLATE_VAR_SUCCESS_FILE = "SUCCESS_FILE";
  public static String DM_TEMPLATE_VAR_ERROR_FILE = "ERROR_FILE";
  public static String DM_TEMPLATE_VAR_BATCH_NO = "BATCH_NO";
  public static String DM_CSV_HEADER = "registration_id,mac_address,firmware_version,hardware_version,order_date,is_valid,errors";
  public static int USER_UPLOAD_TOKEN_MIN_EXPIRE_TIME_SECONDS = (2 * 60 * 60);
  public static int NOTIFICATION_TYPE_NONE = 0;
  public static int NOTIFICATION_TYPE_APNS = 2;
  public static int NOTIFICATION_TYPE_GCM = 1;
  public static String STR_NOTIFICATION_TYPE_NONE = "none";
  public static String STR_NOTIFICATION_TYPE_APNS = "apns";
  public static String STR_NOTIFICATION_TYPE_GCM = "gcm";
  public static String JOB_STATUS_WAITING="waiting";
  public static String FREE_TRIAL_STATUS_ACTIVE = "active";
  public static String FREE_TRIAL_STATUS_EXPIRED = "expired";
  public static String SNS_MESSAGE_FORMAT = "json";
  public static String SOUND_EVENT_MESSAGE = "Sound detected from %s";
  public static String HIGH_TEMP_EVENT_MESSAGE = "High temp from %s";
  public static String LOW_TEMP_EVENT_MESSAGE = "Low temp from %s";
  public static String MOTION_DETECT_EVENT_MESSAGE = "Motion detected from %s";
  public static String DEFAULT_SOUND_KEY_IOS_NOTIFICATION = "default";
  public static String UPDATING_FIRMWARE_EVENT_MESSAGE = "Updating firmware on %s";
  public static String SUCCESS_FIRMWARE_EVENT_MESSAGE = "Firmware successfully updated on %s";
  public static String RESET_PASSWORD_EVENT_MESSAGE = "Reset password on %s platform";
  public static String DEVICE_REMOVED_EVENT_MESSAGE = "Removed %s device from account";
  public static String DEVICE_ADDED_EVENT_MESSAGE = " Added %s device in account";
  public static String CHANGE_TEMPERATURE_EVENT_MESSAGE = "Change temperature from %s to %s in %s minutes for %s";
  public static String TEMPERATURE_SPLIT_SPECIFIER = "-";
  public static String DEFAULT_CHANGE_TEMPERATURE_EVENT_MESSAGE = "Temperature is changed for %s";
  public static String DOOR_MOTION_DETECT_EVENT_MESSAGE = "Motion detected at %s on %s";
  public static String TAG_PRESENCE_DETECT_EVENT_MESSAGE = "%s is back";
  public static String SENSOR_PAIRED_EVENT_MESSAGE = "Sensor %s successfully paired with camera";
  public static String SENSOR_PAIRED_FAIL_EVENT_MESSAGE = "Failed to pair sensor %s with camera";
  public static String SD_CARD_ADDED_EVENT_MESSAGE = "SD-Card is added/removed or full";
  public static String TAG_LOW_BATTERY_EVENT_MESSAGE = "Tag [%s]: Battery low";
  public static String NO_TAG_PRESENCE_DETECT_EVENT_MESSAGE = "%s has left";
  public static String DOOR_MOTION_DETECT_OPEN_EVENT_MESSAGE = "Door Motion open event detected on %s";
  public static String DOOR_MOTION_DETECT_CLOSE_EVENT_MESSAGE = "Door Motion close event detected on %s";
  public static String OCS_LOW_BATTERY_EVENT_MESSAGE = "Open/Close Sensor [%s]: Battery low";
  public static String SD_CARD_REMOVED_EVENT_MESSAGE = "SD-Card is removed from %s";
  public static String SD_CARD_NEARLY_FULL_EVENT_MESSAGE = "SD-Card of %s is nearly full";
  public static String PRESS_TO_RECORD_EVENT_MESSAGE = "press to record from %s";
  public static String BABY_SMILE_DETECTION_EVENT_MESSAGE = "Baby smile detection from %s";
  public static String BABY_SLEEPING_CARE_EVENT_MESSAGE = "Baby sleeping care from %s";
  public static String SD_CARD_FULL_EVENT_MESSAGE = "SD-Card of %s is full %s";
  public static String SD_CARD_INSERTED_EVENT_MESSAGE = "SD-Card is added/removed or full";
  public static final String SN_MIST_LEVEL_MESSAGE = "Mist Level change detected";
  public static final String SN_HUMIDIFIER_STATUS_MESSAGE = "Humidifier ON";
  public static final String SN_WATER_LEVEL_MESSAGE = "Low water detected";
  public static final String SN_WEIGHT_MESSAGE = "New weight update";
  public static final String SN_TEMPERATURE_HIGH_MESSAGE = "High temperature";
  public static final String SN_HUMIDITY_HIGH_MESSAGE = "High humidity";


  public static final String SN_TEMPERATURE_LOW_MESSAGE = "Low temperature";
  public static final String SN_HUMIDITY_LOW_MESSAGE = "Low humidity";
  public static final String SN_NOISE_MESSAGE = "Noise notification";
  public static final String SN_FILTER_CHANGE_MESSAGE = "Filter change notification";
  public static final String SN_WEIGHT_ANOMALY_MESSAGE = "Weight variation detected";
  public static final String SET_STREAM_MODE_RESPONSE="Sucessfully updated Streaming mode";







  public static String AWS_CREDENTIAL_FILE_PATH = "/.aws/credentials";
  public static String SERVER_CERTIFICATE_PATH = "server-certs/%s/mqtt/";
  public static String DEVICE_CLIENT_CERTIFICATE_PATH = "tmp/certs/devices/%s";
  public static String USER_CLIENT_CERTIFICATE_PATH = "tmp/certs/users/%s";
  public static String S3_DEVICE_CLIENT_CERTIFICATE_PATH = "devices/%s/certs";
  public static String S3_CA_CERTIFICATE_PATH =  "mqtt-server-certs/ca.crt";
  public static String S3_CA_CERTIFICATE_KEY_PATH =  "mqtt-server-certs/ca.key";
  public static String S3_CA_CERTIFICATE_SRL_PATH =  "mqtt-server-certs/ca.srl";
  public static String S3_CLIENT_CERTIFICATE_KEY_PATH =  "mqtt-server-certs/client.key";
  public static String S3_CLIENT_CERTIFICATE_PATH =  "mqtt-server-certs/client.crt";
  public static String S3_USER_CLIENT_CERTIFICATE_PATH =  "users/%s/certs";
  public static List<String> SUBSCRIPTION_GCM_APPS = Arrays.asList("hubble_gcm", "inanny_gcm");
  public static List<String> SUBSCRIPTION_APNS_APPS = Arrays.asList("hubble_apns", "hubble_apns");
  public static int CUSTOM_MESSAGE_EVENT = 999999;
  public static Integer STATUS_ACTIVE = 1;
  public static Integer STATUS_INACTIVE = 0;
  public static String FREETRIAL_AVAILABLE_MSG = "A Free Trial is available for '%s'";
  public static String FREETRIAL_EXPIRING_MSG = "Your Free Trial will expire in %s days for '%s'.";
  public static String FREETRIAL_APPLIED_MSG = "A Free Trial has been applied to '%s'.";
  public static String EXPIRED_SUBSCRIPTION_MSG = "Your subscription '%s' has been cancelled";
  public static String FAILED_SUBSCRIPTION_PAYMENT_MESSAGE = "Your payment for subscription '%s' has failed";
  public static String SUBSCRIPTION_APPLIED_MSG = "Your subscription '%s' has been applied to '%s'.";
  public static String SUBSCRIPTION_DISABLED_MSG = "Your subscription '%s' has been disabled on '%s'.";
  public static String DEFAULT_SNAP_EXTN = ".jpg";
  public static String WOWZA_RTMPS_PORT = "443";
  public static String PROTOCOL_RTMP = "rtmp";
  public static String PROTOCOL_RTMPS = "rtmps";
  public static final String PLAN_HT1 = "hubble-tier1";

  public static final String PLAN_HT1_YEARLY = "hubble-tier1-yearly";

  public static final String PLAN_HT2 = "hubble-tier2";

  public static final String PLAN_HT2_YEARLY = "hubble-tier2-yearly";

  public static final String PLAN_HT3 = "hubble-tier3";

  public static final String PLAN_HT3_YEARLY = "hubble-tier3-yearly";

  public static final String STATE_ACTIVE = "active";

  public static final int DEFAULT_EVENT_EXPIRY_DURATION = 259200;// 3 days

  public static final int HT1_EVENT_EXPIRY_DURATION = 345600;// 4 days

  public static final int HT2_EVENT_EXPIRY_DURATION = 864000;// 10 days

  public static final int HT3_EVENT_EXPIRY_DURATION = 2937600;// 34 days


  public final static List<String> mqttDeviceModelList = new ArrayList<>();
  static {
    mqttDeviceModelList.add("0008");
    mqttDeviceModelList.add("0005");
    mqttDeviceModelList.add("0004");
    mqttDeviceModelList.add("0009");
  }

  static {
    alerts = new HashMap<Integer, String>();
    alerts.put(21, "DOOR_MOTION_DETECT_EVENT");
    alerts.put(22, "TAG_PRESENCE_DETECT_EVENT");
    alerts.put(28, "NO_TAG_PRESENCE_DETECT_EVENT");

    listMap = new TreeMap<>();
    initializeModelSupportedApp(listMap);

    DEFAULT_APP_UNIQUE_IDS = new HashMap<String, String>();
    DEFAULT_APP_UNIQUE_IDS.put("gcm", "default_gcm");
    DEFAULT_APP_UNIQUE_IDS.put("apns", "default_apns");
    FILTER_OPERATOR_SUPPORT = new ArrayList<String>() {
      {
        add("=");
        add("!=");
        add(">");
        add("<");
        add(">=");
        add("<=");
      }
    };
    wowzaIpMapping = new HashMap<String, String>();
    wowzaIpMapping.put("52.74.59.9", "dev-h2o-wowza.hubble.in");
    wowzaIpMapping.put("52.74.236.217", "staging-h2o-wowza.hubble.in");
  }


  public static void initializeModelSupportedApp(TreeMap<String, List<HashMap<String, List<String>>>> modelSupportedApps) {

    List defaultHubbleGcmAppIds = Arrays.asList("default_gcm", "hubble_gcm");
    List defaultHubbleAdhocApnsAppIds = Arrays.asList("default_apns", "hubble_apns", "adhoc_apns");
    List vtechGcmAppIds = Arrays.asList("vtech_gcm");
    List vtechApnsAppIds = Arrays.asList("vtech_apns");
    List alcatelInannyGcmAppIds = Arrays.asList("alcatel_gcm", "inanny_gcm");
    List alcatelInannyApnsAppIds = Arrays.asList("alcatel_apns", "inanny_apns");
    List beurerInannyGcmAppIds = Arrays.asList("beurer_gcm", "inanny_gcm");
    List beurerInannyApnsAppIds = Arrays.asList("beurer_apns", "inanny_apns");
    List borkGcmAppIds = Arrays.asList("bork_gcm");
    List borkApnsAppIds = Arrays.asList("bork_apns");
    List inannyGcmAppIds = Arrays.asList("inanny_gcm");
    List inannyApnsIds = Arrays.asList("inanny_apns");

    List smartNurseryGcmAppIds = Arrays.asList("smart_nursery_gcm");
    List smartNurseryApnsIds = Arrays.asList("smart_nursery_apns");


    HashMap<String, List<String>> defaultHubbleGcmMap = new HashMap<>();
    defaultHubbleGcmMap.put("gcm", defaultHubbleGcmAppIds);

    HashMap<String, List<String>> defaultHubbleAdhocApnsMap = new HashMap<>();
    defaultHubbleAdhocApnsMap.put("apns", defaultHubbleAdhocApnsAppIds);

    List<HashMap<String, List<String>>> defaultGcmApnsGroup = new ArrayList<>();
    defaultGcmApnsGroup.add(defaultHubbleGcmMap);
    defaultGcmApnsGroup.add(defaultHubbleAdhocApnsMap);

    modelSupportedApps.put("0836", defaultGcmApnsGroup);
    modelSupportedApps.put("0066", defaultGcmApnsGroup);
    modelSupportedApps.put("0096", defaultGcmApnsGroup);
    modelSupportedApps.put("0083", defaultGcmApnsGroup);
    modelSupportedApps.put("0036", defaultGcmApnsGroup);
    modelSupportedApps.put("0076", defaultGcmApnsGroup);
    modelSupportedApps.put("0085", defaultGcmApnsGroup);
    modelSupportedApps.put("0073", defaultGcmApnsGroup);
    modelSupportedApps.put("0086", defaultGcmApnsGroup);
    modelSupportedApps.put("0854", defaultGcmApnsGroup);
    modelSupportedApps.put("0662", defaultGcmApnsGroup);
    modelSupportedApps.put("1854", defaultGcmApnsGroup);
    modelSupportedApps.put("1662", defaultGcmApnsGroup);
    modelSupportedApps.put("0173", defaultGcmApnsGroup);
    modelSupportedApps.put("0877", defaultGcmApnsGroup);
    modelSupportedApps.put("0002", defaultGcmApnsGroup);
    modelSupportedApps.put("0003", defaultGcmApnsGroup);
    modelSupportedApps.put("1000", defaultGcmApnsGroup);
    modelSupportedApps.put("0004", defaultGcmApnsGroup);


    HashMap<String, List<String>> vtechGcmMap = new HashMap<>();
    vtechGcmMap.put("gcm", vtechGcmAppIds);


    HashMap<String, List<String>> vtechApnsMap = new HashMap<>();
    vtechApnsMap.put("apns", vtechApnsAppIds);

    List<HashMap<String, List<String>>> vtechGcmApnsGroup = new ArrayList<>();
    vtechGcmApnsGroup.add(vtechGcmMap);
    vtechGcmApnsGroup.add(vtechApnsMap);

    modelSupportedApps.put("0921", vtechGcmApnsGroup);
    modelSupportedApps.put("0931", vtechGcmApnsGroup);


    HashMap<String, List<String>> alcatelInannyGcmMap = new HashMap<>();
    alcatelInannyGcmMap.put("gcm", alcatelInannyGcmAppIds);


    HashMap<String, List<String>> alcatelInannyApnsMap = new HashMap<>();
    alcatelInannyApnsMap.put("apns", alcatelInannyApnsAppIds);

    List<HashMap<String, List<String>>> alcatelInannyGcmApnsGroup = new ArrayList<>();
    alcatelInannyGcmApnsGroup.add(alcatelInannyGcmMap);
    alcatelInannyGcmApnsGroup.add(alcatelInannyApnsMap);

    modelSupportedApps.put("0113", alcatelInannyGcmApnsGroup);

    HashMap<String, List<String>> beurerInannyGcmMap = new HashMap<>();
    beurerInannyGcmMap.put("gcm", beurerInannyGcmAppIds);

    HashMap<String, List<String>> beurerInannyApnsMap = new HashMap<>();
    beurerInannyApnsMap.put("apns", beurerInannyApnsAppIds);

    List<HashMap<String, List<String>>> beurerInannyGcmApnsGroup = new ArrayList<>();
    beurerInannyGcmApnsGroup.add(beurerInannyGcmMap);
    beurerInannyGcmApnsGroup.add(beurerInannyApnsMap);
    modelSupportedApps.put("0112", alcatelInannyGcmApnsGroup);
    modelSupportedApps.put("0204", alcatelInannyGcmApnsGroup);


    HashMap<String, List<String>> borkGcmMap = new HashMap<>();
    borkGcmMap.put("gcm", borkGcmAppIds);

    HashMap<String, List<String>> borkApnsMap = new HashMap<>();
    borkApnsMap.put("apns", borkApnsAppIds);

    List<HashMap<String, List<String>>> borkGcmApnsGroup = new ArrayList<>();
    borkGcmApnsGroup.add(borkGcmMap);
    borkGcmApnsGroup.add(borkApnsMap);

    modelSupportedApps.put("0001", borkGcmApnsGroup);

    HashMap<String, List<String>> inannyGcmMap = new HashMap<>();
    borkGcmMap.put("gcm", inannyGcmAppIds);

    HashMap<String, List<String>> inannyApnsMap = new HashMap<>();
    borkApnsMap.put("apns", inannyApnsIds);

    List<HashMap<String, List<String>>> inannyGcmApnsGroup = new ArrayList<>();
    borkGcmApnsGroup.add(inannyGcmMap);
    borkGcmApnsGroup.add(inannyApnsMap);

    modelSupportedApps.put("0288", inannyGcmApnsGroup);


    HashMap<String, List<String>> smartNurseryGcmMap = new HashMap<>();
    smartNurseryGcmMap.put("gcm", smartNurseryGcmAppIds);

    HashMap<String, List<String>> smartNurseryApnsMap = new HashMap<>();
    smartNurseryApnsMap.put("apns", smartNurseryApnsIds);

    List<HashMap<String, List<String>>> smartNurseryGcmApnsGroup = new ArrayList<>();
    smartNurseryGcmApnsGroup.add(smartNurseryGcmMap);
    smartNurseryGcmApnsGroup.add(smartNurseryApnsMap);

    modelSupportedApps.put("0008", smartNurseryGcmApnsGroup);
    modelSupportedApps.put("0009", smartNurseryGcmApnsGroup);
    modelSupportedApps.put("0005", smartNurseryGcmApnsGroup);
    modelSupportedApps.put("0004", smartNurseryGcmApnsGroup);


    deviceCapability.put("stun", STUN_SUPPORTED_MODELS);
        deviceCapability.put("mqtt", MQTT_SUPPORTED_MODELS);

  }

  public static List<String> getAppsByModelNumber(String modelNumber, String apptype) {

      logger.info("model num:"+modelNumber+"apptype: "+apptype);
    if (!listMap.containsKey(modelNumber)) {
      return null;
    }
    if ("gcm".equals(apptype)) {
      return listMap.get(modelNumber).get(0).get(apptype);
    } else if ("apns".equals(apptype)) {
      return listMap.get(modelNumber).get(1).get(apptype);
    } else {
      return null;
    }
  }

  /**
   * Default app unique ids map.
   *
   * @return the map
   */
  public static Map<String, String> DEFAULT_APP_UNIQUE_IDS() {
    final Map<String, String> appIdMap = new HashMap<>();
    appIdMap.put("gcm", "default_gcm");
    appIdMap.put("apns", "default_apns");
    return Collections.unmodifiableMap(appIdMap);
  }


  public static final List<String> STUN_SUPPORTED_MODELS
          = Stream.of(
          "0836",
          "0066",
          "0096",
          "0083",
          "0854",
          "0085",
          "0921",
          "0931",
          "0073",
          "0113",
          "0112",
          "0204",
          "0002",
          "0003",
          "0086",
          "0662",
          "1854",
          "1662",
          "0001",
          "0877",
          "0173",
          "0007",
          "1000",
          "0072",
          "0080",
          "0081",
          "0855").collect(Collectors.toList());
  public static final List<String> MQTT_SUPPORTED_MODELS
          = Stream.of("0008", "0009", "0005", "0004").collect(Collectors.toList());
  
    
    public static void main(String[] args) throws IOException {
        
        System.out.println("logger :"+STUN_SUPPORTED_MODELS.contains("0006"));
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(new File("/Users/nikhilvs9999/WORK/HUBBLE/DEV/hubble-server/app.json"), deviceCapability);
        
        
    }
  
}