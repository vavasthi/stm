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
 * on 2015-09-23 at 16:26:53 UTC 
 * Modify at your own risk.
 */

package com.khanakirana.backend.userRegistrationApi.model;

/**
 * Model definition for MasterItem.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the userRegistrationApi. For a detailed explanation see:
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
  private java.lang.String id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private Blob image;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String imageType;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private MeasurementUnit unit;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String upc;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String user;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public MasterItem setId(java.lang.String id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public Blob getImage() {
    return image;
  }

  /**
   * @param image image or {@code null} for none
   */
  public MasterItem setImage(Blob image) {
    this.image = image;
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
  public MeasurementUnit getUnit() {
    return unit;
  }

  /**
   * @param unit unit or {@code null} for none
   */
  public MasterItem setUnit(MeasurementUnit unit) {
    this.unit = unit;
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
  public java.lang.String getUser() {
    return user;
  }

  /**
   * @param user user or {@code null} for none
   */
  public MasterItem setUser(java.lang.String user) {
    this.user = user;
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
