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
 * on 2015-10-10 at 18:39:44 UTC 
 * Modify at your own risk.
 */

package com.khanakirana.backend.sysadminApi.model;

/**
 * Model definition for MeasurementUnit.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the sysadminApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class MeasurementUnit extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String acronym;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double factor;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

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
  private java.lang.Boolean primaryUnit;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getAcronym() {
    return acronym;
  }

  /**
   * @param acronym acronym or {@code null} for none
   */
  public MeasurementUnit setAcronym(java.lang.String acronym) {
    this.acronym = acronym;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getFactor() {
    return factor;
  }

  /**
   * @param factor factor or {@code null} for none
   */
  public MeasurementUnit setFactor(java.lang.Double factor) {
    this.factor = factor;
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
  public MeasurementUnit setId(java.lang.Long id) {
    this.id = id;
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
  public MeasurementUnit setMeasurementCategoryId(java.lang.Long measurementCategoryId) {
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
  public MeasurementUnit setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getPrimaryUnit() {
    return primaryUnit;
  }

  /**
   * @param primaryUnit primaryUnit or {@code null} for none
   */
  public MeasurementUnit setPrimaryUnit(java.lang.Boolean primaryUnit) {
    this.primaryUnit = primaryUnit;
    return this;
  }

  @Override
  public MeasurementUnit set(String fieldName, Object value) {
    return (MeasurementUnit) super.set(fieldName, value);
  }

  @Override
  public MeasurementUnit clone() {
    return (MeasurementUnit) super.clone();
  }

}
