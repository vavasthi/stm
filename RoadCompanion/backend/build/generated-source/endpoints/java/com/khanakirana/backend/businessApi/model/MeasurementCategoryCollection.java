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
 * on 2015-11-03 at 12:52:24 UTC 
 * Modify at your own risk.
 */

package com.khanakirana.backend.businessApi.model;

/**
 * Model definition for MeasurementCategoryCollection.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the businessApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class MeasurementCategoryCollection extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<MeasurementCategory> items;

  static {
    // hack to force ProGuard to consider MeasurementCategory used, since otherwise it would be stripped out
    // see http://code.google.com/p/google-api-java-client/issues/detail?id=528
    com.google.api.client.util.Data.nullOf(MeasurementCategory.class);
  }

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<MeasurementCategory> getItems() {
    return items;
  }

  /**
   * @param items items or {@code null} for none
   */
  public MeasurementCategoryCollection setItems(java.util.List<MeasurementCategory> items) {
    this.items = items;
    return this;
  }

  @Override
  public MeasurementCategoryCollection set(String fieldName, Object value) {
    return (MeasurementCategoryCollection) super.set(fieldName, value);
  }

  @Override
  public MeasurementCategoryCollection clone() {
    return (MeasurementCategoryCollection) super.clone();
  }

}
