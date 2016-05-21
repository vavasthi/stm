/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.enums;

import com.sanjnan.server.pojos.constants.H2OConstants;

/**
 * Created by maheshsapre on 04/04/16.
 */
public enum AlertType {

    SOUND_DETECTED("sound detected", H2OConstants.SOUND_EVENT, H2OConstants.SOUND_EVENT_MESSAGE),

    HIGH_TEMPERATURE("high temperature detected", H2OConstants.HIGH_TEMP_EVENT, H2OConstants.HIGH_TEMP_EVENT_MESSAGE),

    LOW_TEMPERATURE("low temperature detected", H2OConstants.LOW_TEMP_EVENT, H2OConstants.LOW_TEMP_EVENT_MESSAGE),

    MOTION_DETECTED("motion detected", H2OConstants.MOTION_DETECT_EVENT, H2OConstants.MOTION_DETECT_EVENT_MESSAGE),

    UPDATING_FIRMWARE_EVENT("updating firmware", H2OConstants.UPDATING_FIRMWARE_EVENT, H2OConstants.UPDATING_FIRMWARE_EVENT_MESSAGE),

    FIRMWARE_UPDATE_SUCCESS("firmware updated", H2OConstants.SUCCESS_FIRMWARE_EVENT, H2OConstants.SUCCESS_FIRMWARE_EVENT_MESSAGE),

    RESET_PASSWORD("reset password", H2OConstants.RESET_PASSWORD_EVENT, H2OConstants.RESET_PASSWORD_EVENT_MESSAGE),

    DEVICE_REMOVED("device removed", H2OConstants.DEVICE_REMOVED_EVENT, H2OConstants.DEVICE_REMOVED_EVENT_MESSAGE),

    DEVICE_ADDED("device added", H2OConstants.DEVICE_ADDED_EVENT, H2OConstants.DEVICE_ADDED_EVENT_MESSAGE),

    CHANGE_TEMPERATURE("temperature changed", H2OConstants.CHANGE_TEMPERATURE_EVENT, H2OConstants.CHANGE_TEMPERATURE_EVENT_MESSAGE),

    //# Alert types 14,15,16 and 25 are used by pet tracker
    DOOR_MOTOIN("door motion detected", H2OConstants.DOOR_MOTION_DETECT_EVENT, H2OConstants.DOOR_MOTION_DETECT_EVENT_MESSAGE),

    TAG_PRESENCE("tag is back", H2OConstants.TAG_PRESENCE_DETECT_EVENT, H2OConstants.TAG_PRESENCE_DETECT_EVENT_MESSAGE),

    SENSOR_PAIRED("sensor added", H2OConstants.SENSOR_PAIRED_EVENT, H2OConstants.SENSOR_PAIRED_EVENT_MESSAGE),

    SENSOR_PAIRED_FAIL("failed to add sensor", H2OConstants.SENSOR_PAIRED_FAIL_EVENT, H2OConstants.SENSOR_PAIRED_FAIL_EVENT_MESSAGE),

    SD_CARD_ADDED("sd card added", H2OConstants.SD_CARD_ADDED_EVENT, H2OConstants.SD_CARD_ADDED_EVENT_MESSAGE),

    TAG_LOW_BATTERY("tag loq battery detected", H2OConstants.TAG_LOW_BATTERY_EVENT, H2OConstants.TAG_LOW_BATTERY_EVENT_MESSAGE),

    NO_TAG_PRESENCE("no tag presence detected", H2OConstants.NO_TAG_PRESENCE_DETECT_EVENT, H2OConstants.NO_TAG_PRESENCE_DETECT_EVENT_MESSAGE),

    DOOR_OPEN("door open detected", H2OConstants.DOOR_MOTION_DETECT_OPEN_EVENT, H2OConstants.DOOR_MOTION_DETECT_OPEN_EVENT_MESSAGE),

    DOOR_CLOSE("door close detected", H2OConstants.DOOR_MOTION_DETECT_CLOSE_EVENT, H2OConstants.DOOR_MOTION_DETECT_CLOSE_EVENT_MESSAGE),

    OCS_LOW_BATTERY("low battery detected", H2OConstants.OCS_LOW_BATTERY_EVENT, H2OConstants.OCS_LOW_BATTERY_EVENT_MESSAGE),

    SD_CARD_REMOVED("sd card removed", H2OConstants.SD_CARD_REMOVED_EVENT, H2OConstants.SD_CARD_REMOVED_EVENT_MESSAGE),

    SD_CARD_NEARLY_FULL("sd card nearly full", H2OConstants.SD_CARD_NEARLY_FULL_EVENT, H2OConstants.SD_CARD_NEARLY_FULL_EVENT_MESSAGE),

    PRESS_TO_RECORD("press to record", H2OConstants.PRESS_TO_RECORD_EVENT, H2OConstants.PRESS_TO_RECORD_EVENT_MESSAGE),

    BABY_SMILE("baby smile detected", H2OConstants.BABY_SMILE_DETECTION_EVENT, H2OConstants.BABY_SMILE_DETECTION_EVENT_MESSAGE),

    BABY_SLEEPING_CARE("baby sleeping care", H2OConstants.BABY_SLEEPING_CARE_EVENT, H2OConstants.BABY_SLEEPING_CARE_EVENT_MESSAGE),

    SD_CARD__FULL("sd card full,", H2OConstants.SD_CARD_FULL_EVENT, H2OConstants.SD_CARD_FULL_EVENT_MESSAGE),

    SD_CARD_INSERTED("sd card inserted", H2OConstants.SD_CARD_INSERTED_EVENT, H2OConstants.SD_CARD_INSERTED_EVENT_MESSAGE),
    
    SN_MIST_LEVEL("mist level change detected", H2OConstants.SN_MIST_LEVEL_EVENT, H2OConstants.SN_MIST_LEVEL_MESSAGE),
    SN_HUMIDIFIER("humidifier on", H2OConstants.SN_HUMIDIFIER_STATUS_EVENT, H2OConstants.SN_HUMIDIFIER_STATUS_MESSAGE),
    SN_WATER_LEVEL("low water detected", H2OConstants.SN_WATER_LEVEL_EVENT, H2OConstants.SN_WATER_LEVEL_MESSAGE),
    SN_WEIGHT("new weight update", H2OConstants.SN_WEIGHT_EVENT, H2OConstants.SN_WEIGHT_MESSAGE),
    SN_TEMPERATURE_HIGH("High temperature", H2OConstants.SN_TEMPERATURE_HIGH_EVENT, H2OConstants.SN_TEMPERATURE_HIGH_MESSAGE),
    SN_HUMIDITY_HIGH("High humidity", H2OConstants.SN_HUMIDITY_HIGH_EVENT, H2OConstants.SN_HUMIDITY_HIGH_MESSAGE),
    SN_TEMPERATURE_LOW("Low temperature", H2OConstants.SN_TEMPERATURE_LOW_EVENT, H2OConstants.SN_TEMPERATURE_LOW_MESSAGE),
    SN_HUMIDITY_LOW("Low humidity", H2OConstants.SN_HUMIDITY_LOW_EVENT, H2OConstants.SN_HUMIDITY_LOW_MESSAGE),
    SN_NOISE("Noise notification", H2OConstants.SN_NOISE_EVENT, H2OConstants.SN_NOISE_MESSAGE),
    SN_FILTER_CHANGE("Filter change notification", H2OConstants.SN_FILTER_CHANGE_EVENT, H2OConstants.SN_FILTER_CHANGE_MESSAGE),
    SN_WEIGHT_ANOMALY("Weight variation detected", H2OConstants.SN_WEIGHT_ANOMALY_EVENT, H2OConstants.SN_WEIGHT_ANOMALY_MESSAGE)
    ;

    private final String typeString;
    private final int type;
    private final String formattedMessage;

    AlertType(String typeString, int type, String formattedMessage) {
        this.type = type;
        this.typeString = typeString;
        this.formattedMessage = formattedMessage;
    }

    /**
     * Create mode.
     *
     * @param typeString the mode string
     * @return the mode
     */
    public static AlertType create(String typeString) {
        for (AlertType m : values()) {
            if (m.typeString.equalsIgnoreCase(typeString)) {
                return m;
            }
        }
        throw new IllegalArgumentException(String.format("%s is not a valid alert type", typeString));
    }

    /**
     * Create mode.
     *
     * @param type the mode
     * @return the mode
     */
    public static AlertType create(int type) {
        for (AlertType m : values()) {
            if (m.type == type) {
                return m;
            }
        }
        throw new IllegalArgumentException(String.format("%d is not a valid alert type", type));
    }

    @Override
    public String toString() {
        return typeString;
    }

    public String getFormattedMessage() {
        return formattedMessage;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public Integer getValue() {
        return type;
    }
}
