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
 * on 2015-12-17 at 17:44:06 UTC 
 * Modify at your own risk.
 */

package com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model;

/**
 * Model definition for Amenity.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the roadMeasurementApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Amenity extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String city;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String country;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime createdOn;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean hasPetrolStation;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean hasRestaurant;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean hasRestrooms;

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
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String state;

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
  public java.lang.String getCity() {
    return city;
  }

  /**
   * @param city city or {@code null} for none
   */
  public Amenity setCity(java.lang.String city) {
    this.city = city;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCountry() {
    return country;
  }

  /**
   * @param country country or {@code null} for none
   */
  public Amenity setCountry(java.lang.String country) {
    this.country = country;
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
  public Amenity setCreatedOn(com.google.api.client.util.DateTime createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getHasPetrolStation() {
    return hasPetrolStation;
  }

  /**
   * @param hasPetrolStation hasPetrolStation or {@code null} for none
   */
  public Amenity setHasPetrolStation(java.lang.Boolean hasPetrolStation) {
    this.hasPetrolStation = hasPetrolStation;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getHasRestaurant() {
    return hasRestaurant;
  }

  /**
   * @param hasRestaurant hasRestaurant or {@code null} for none
   */
  public Amenity setHasRestaurant(java.lang.Boolean hasRestaurant) {
    this.hasRestaurant = hasRestaurant;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getHasRestrooms() {
    return hasRestrooms;
  }

  /**
   * @param hasRestrooms hasRestrooms or {@code null} for none
   */
  public Amenity setHasRestrooms(java.lang.Boolean hasRestrooms) {
    this.hasRestrooms = hasRestrooms;
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
  public Amenity setId(java.lang.Long id) {
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
  public Amenity setLatitude(java.lang.Double latitude) {
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
  public Amenity setLongitude(java.lang.Double longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public Amenity setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getState() {
    return state;
  }

  /**
   * @param state state or {@code null} for none
   */
  public Amenity setState(java.lang.String state) {
    this.state = state;
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
  public Amenity setTimestamp(com.google.api.client.util.DateTime timestamp) {
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
  public Amenity setUserId(java.lang.Long userId) {
    this.userId = userId;
    return this;
  }

  @Override
  public Amenity set(String fieldName, Object value) {
    return (Amenity) super.set(fieldName, value);
  }

  @Override
  public Amenity clone() {
    return (Amenity) super.clone();
  }

}
