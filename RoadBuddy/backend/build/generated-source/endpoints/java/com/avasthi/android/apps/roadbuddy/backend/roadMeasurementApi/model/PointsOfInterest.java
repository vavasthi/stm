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
 * Model definition for PointsOfInterest.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the roadMeasurementApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PointsOfInterest extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Amenity> amenityList;

  static {
    // hack to force ProGuard to consider Amenity used, since otherwise it would be stripped out
    // see https://github.com/google/google-api-java-client/issues/543
    com.google.api.client.util.Data.nullOf(Amenity.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Checkpost> checkpostList;

  static {
    // hack to force ProGuard to consider Checkpost used, since otherwise it would be stripped out
    // see https://github.com/google/google-api-java-client/issues/543
    com.google.api.client.util.Data.nullOf(Checkpost.class);
  }

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Drive currentDrive;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<Toll> tollList;

  static {
    // hack to force ProGuard to consider Toll used, since otherwise it would be stripped out
    // see https://github.com/google/google-api-java-client/issues/543
    com.google.api.client.util.Data.nullOf(Toll.class);
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Amenity> getAmenityList() {
    return amenityList;
  }

  /**
   * @param amenityList amenityList or {@code null} for none
   */
  public PointsOfInterest setAmenityList(java.util.List<Amenity> amenityList) {
    this.amenityList = amenityList;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Checkpost> getCheckpostList() {
    return checkpostList;
  }

  /**
   * @param checkpostList checkpostList or {@code null} for none
   */
  public PointsOfInterest setCheckpostList(java.util.List<Checkpost> checkpostList) {
    this.checkpostList = checkpostList;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Drive getCurrentDrive() {
    return currentDrive;
  }

  /**
   * @param currentDrive currentDrive or {@code null} for none
   */
  public PointsOfInterest setCurrentDrive(Drive currentDrive) {
    this.currentDrive = currentDrive;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<Toll> getTollList() {
    return tollList;
  }

  /**
   * @param tollList tollList or {@code null} for none
   */
  public PointsOfInterest setTollList(java.util.List<Toll> tollList) {
    this.tollList = tollList;
    return this;
  }

  @Override
  public PointsOfInterest set(String fieldName, Object value) {
    return (PointsOfInterest) super.set(fieldName, value);
  }

  @Override
  public PointsOfInterest clone() {
    return (PointsOfInterest) super.clone();
  }

}
