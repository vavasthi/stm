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
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-08-03 17:34:38 UTC)
 * on 2015-10-13 at 17:16:59 UTC 
 * Modify at your own risk.
 */

package com.khanakirana.backend.businessApi.model;

/**
 * Model definition for MasterItem.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the businessApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class MasterItem extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String description;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String imageCloudKey;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String imageType;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long measurementCategoryId;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String upc;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String userEmailId;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * @param description description or {@code null} for none
   */
  public MasterItem setDescription(java.lang.String description) {
    this.description = description;
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
  public MasterItem setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getImageCloudKey() {
    return imageCloudKey;
  }

  /**
   * @param imageCloudKey imageCloudKey or {@code null} for none
   */
  public MasterItem setImageCloudKey(java.lang.String imageCloudKey) {
    this.imageCloudKey = imageCloudKey;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getImageType() {
    return imageType;
  }

  /**
   * @param imageType imageType or {@code null} for none
   */
  public MasterItem setImageType(java.lang.String imageType) {
    this.imageType = imageType;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getMeasurementCategoryId() {
    return measurementCategoryId;
  }

  /**
   * @param measurementCategoryId measurementCategoryId or {@code null} for none
   */
  public MasterItem setMeasurementCategoryId(java.lang.Long measurementCategoryId) {
    this.measurementCategoryId = measurementCategoryId;
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
  public MasterItem setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUpc() {
    return upc;
  }

  /**
   * @param upc upc or {@code null} for none
   */
  public MasterItem setUpc(java.lang.String upc) {
    this.upc = upc;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getUserEmailId() {
    return userEmailId;
  }

  /**
   * @param userEmailId userEmailId or {@code null} for none
   */
  public MasterItem setUserEmailId(java.lang.String userEmailId) {
    this.userEmailId = userEmailId;
    return this;
  }

  @Override
  public MasterItem set(String fieldName, Object value) {
    return (MasterItem) super.set(fieldName, value);
  }

  @Override
  public MasterItem clone() {
    return (MasterItem) super.clone();
  }

}
