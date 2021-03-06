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
 * on 2016-07-06 at 14:00:19 UTC 
 * Modify at your own risk.
 */

package com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model;

/**
 * Model definition for Drive.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the roadMeasurementApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Drive extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime completedAt;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime createdOn;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double distanceCovered;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean done;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long eventId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long groupId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double lastLatitude;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double lastLongitude;

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
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long memberId;

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getCompletedAt() {
    return completedAt;
  }

  /**
   * @param completedAt completedAt or {@code null} for none
   */
  public Drive setCompletedAt(com.google.api.client.util.DateTime completedAt) {
    this.completedAt = completedAt;
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
  public Drive setCreatedOn(com.google.api.client.util.DateTime createdOn) {
    this.createdOn = createdOn;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getDistanceCovered() {
    return distanceCovered;
  }

  /**
   * @param distanceCovered distanceCovered or {@code null} for none
   */
  public Drive setDistanceCovered(java.lang.Double distanceCovered) {
    this.distanceCovered = distanceCovered;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getDone() {
    return done;
  }

  /**
   * @param done done or {@code null} for none
   */
  public Drive setDone(java.lang.Boolean done) {
    this.done = done;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getEventId() {
    return eventId;
  }

  /**
   * @param eventId eventId or {@code null} for none
   */
  public Drive setEventId(java.lang.Long eventId) {
    this.eventId = eventId;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getGroupId() {
    return groupId;
  }

  /**
   * @param groupId groupId or {@code null} for none
   */
  public Drive setGroupId(java.lang.Long groupId) {
    this.groupId = groupId;
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
  public Drive setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLastLatitude() {
    return lastLatitude;
  }

  /**
   * @param lastLatitude lastLatitude or {@code null} for none
   */
  public Drive setLastLatitude(java.lang.Double lastLatitude) {
    this.lastLatitude = lastLatitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLastLongitude() {
    return lastLongitude;
  }

  /**
   * @param lastLongitude lastLongitude or {@code null} for none
   */
  public Drive setLastLongitude(java.lang.Double lastLongitude) {
    this.lastLongitude = lastLongitude;
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
  public Drive setLatitude(java.lang.Double latitude) {
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
  public Drive setLongitude(java.lang.Double longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMemberId() {
    return memberId;
  }

  /**
   * @param memberId memberId or {@code null} for none
   */
  public Drive setMemberId(java.lang.Long memberId) {
    this.memberId = memberId;
    return this;
  }

  @Override
  public Drive set(String fieldName, Object value) {
    return (Drive) super.set(fieldName, value);
  }

  @Override
  public Drive clone() {
    return (Drive) super.clone();
  }

}
