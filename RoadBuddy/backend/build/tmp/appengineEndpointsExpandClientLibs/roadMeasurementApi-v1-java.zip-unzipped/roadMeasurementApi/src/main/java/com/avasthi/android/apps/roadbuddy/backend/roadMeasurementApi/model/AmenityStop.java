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
 * (build: 2015-11-16 19:10:01 UTC)
 * on 2015-12-19 at 09:24:03 UTC 
 * Modify at your own risk.
 */

package com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model;

/**
 * Model definition for AmenityStop.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the roadMeasurementApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class AmenityStop extends com.google.api.client.json.GenericJson {

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
  private java.lang.Long establishmentId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float foodAmount;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float fuelAmount;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Float fuelQuantity;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer petrolStationRating;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer restaurantRating;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer restroomRating;

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
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getCreatedOn() {
    return createdOn;
  }

  /**
   * @param createdOn createdOn or {@code null} for none
   */
  public AmenityStop setCreatedOn(com.google.api.client.util.DateTime createdOn) {
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
  public AmenityStop setDriveId(java.lang.Long driveId) {
    this.driveId = driveId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getEstablishmentId() {
    return establishmentId;
  }

  /**
   * @param establishmentId establishmentId or {@code null} for none
   */
  public AmenityStop setEstablishmentId(java.lang.Long establishmentId) {
    this.establishmentId = establishmentId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getFoodAmount() {
    return foodAmount;
  }

  /**
   * @param foodAmount foodAmount or {@code null} for none
   */
  public AmenityStop setFoodAmount(java.lang.Float foodAmount) {
    this.foodAmount = foodAmount;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getFuelAmount() {
    return fuelAmount;
  }

  /**
   * @param fuelAmount fuelAmount or {@code null} for none
   */
  public AmenityStop setFuelAmount(java.lang.Float fuelAmount) {
    this.fuelAmount = fuelAmount;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Float getFuelQuantity() {
    return fuelQuantity;
  }

  /**
   * @param fuelQuantity fuelQuantity or {@code null} for none
   */
  public AmenityStop setFuelQuantity(java.lang.Float fuelQuantity) {
    this.fuelQuantity = fuelQuantity;
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
  public AmenityStop setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPetrolStationRating() {
    return petrolStationRating;
  }

  /**
   * @param petrolStationRating petrolStationRating or {@code null} for none
   */
  public AmenityStop setPetrolStationRating(java.lang.Integer petrolStationRating) {
    this.petrolStationRating = petrolStationRating;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getRestaurantRating() {
    return restaurantRating;
  }

  /**
   * @param restaurantRating restaurantRating or {@code null} for none
   */
  public AmenityStop setRestaurantRating(java.lang.Integer restaurantRating) {
    this.restaurantRating = restaurantRating;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getRestroomRating() {
    return restroomRating;
  }

  /**
   * @param restroomRating restroomRating or {@code null} for none
   */
  public AmenityStop setRestroomRating(java.lang.Integer restroomRating) {
    this.restroomRating = restroomRating;
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
  public AmenityStop setTimestamp(com.google.api.client.util.DateTime timestamp) {
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
  public AmenityStop setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public AmenityStop set(String fieldName, Object value) {
    return (AmenityStop) super.set(fieldName, value);
  }

  @Override
  public AmenityStop clone() {
    return (AmenityStop) super.clone();
  }

}
