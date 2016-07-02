/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-05-27 16:00:31 UTC)
 * on 2016-07-02 at 11:06:06 UTC 
 * Modify at your own risk.
 */

package com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model;

/**
 * Model definition for SensorData.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the roadMeasurementApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class SensorData extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float accuracy;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float bearing;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime createdOn;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long driveId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double latitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double longitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float speed;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime timestamp;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long userId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float verticalAccelerometerMean;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float verticalAccelerometerSD;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getAccuracy() {
    return accuracy;
  }

  /**
   * @param accuracy accuracy or {@code null} for none
   */
  public SensorData setAccuracy(java.lang.Float accuracy) {
    this.accuracy = accuracy;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getBearing() {
    return bearing;
  }

  /**
   * @param bearing bearing or {@code null} for none
   */
  public SensorData setBearing(java.lang.Float bearing) {
    this.bearing = bearing;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getCreatedOn() {
    return createdOn;
  }

  /**
   * @param createdOn createdOn or {@code null} for none
   */
  public SensorData setCreatedOn(com.google.api.client.util.DateTime createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getDriveId() {
    return driveId;
  }

  /**
   * @param driveId driveId or {@code null} for none
   */
  public SensorData setDriveId(java.lang.Long driveId) {
    this.driveId = driveId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public SensorData setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLatitude() {
    return latitude;
  }

  /**
   * @param latitude latitude or {@code null} for none
   */
  public SensorData setLatitude(java.lang.Double latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLongitude() {
    return longitude;
  }

  /**
   * @param longitude longitude or {@code null} for none
   */
  public SensorData setLongitude(java.lang.Double longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getSpeed() {
    return speed;
  }

  /**
   * @param speed speed or {@code null} for none
   */
  public SensorData setSpeed(java.lang.Float speed) {
    this.speed = speed;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getTimestamp() {
    return timestamp;
  }

  /**
   * @param timestamp timestamp or {@code null} for none
   */
  public SensorData setTimestamp(com.google.api.client.util.DateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getUserId() {
    return userId;
  }

  /**
   * @param userId userId or {@code null} for none
   */
  public SensorData setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getVerticalAccelerometerMean() {
    return verticalAccelerometerMean;
  }

  /**
   * @param verticalAccelerometerMean verticalAccelerometerMean or {@code null} for none
   */
  public SensorData setVerticalAccelerometerMean(java.lang.Float verticalAccelerometerMean) {
    this.verticalAccelerometerMean = verticalAccelerometerMean;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getVerticalAccelerometerSD() {
    return verticalAccelerometerSD;
  }

  /**
   * @param verticalAccelerometerSD verticalAccelerometerSD or {@code null} for none
   */
  public SensorData setVerticalAccelerometerSD(java.lang.Float verticalAccelerometerSD) {
    this.verticalAccelerometerSD = verticalAccelerometerSD;
    return this;
  }

  @Override
  public SensorData set(String fieldName, Object value) {
    return (SensorData) super.set(fieldName, value);
  }

  @Override
  public SensorData clone() {
    return (SensorData) super.clone();
  }

}
