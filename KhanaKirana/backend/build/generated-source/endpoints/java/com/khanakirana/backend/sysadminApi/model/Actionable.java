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
 * on 2015-10-13 at 17:17:02 UTC 
 * Modify at your own risk.
 */

package com.khanakirana.backend.sysadminApi.model;

/**
 * Model definition for Actionable.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the sysadminApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Actionable extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer actionTitle;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer actionType;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer description;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String details;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean done;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer title;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getActionTitle() {
    return actionTitle;
  }

  /**
   * @param actionTitle actionTitle or {@code null} for none
   */
  public Actionable setActionTitle(java.lang.Integer actionTitle) {
    this.actionTitle = actionTitle;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getActionType() {
    return actionType;
  }

  /**
   * @param actionType actionType or {@code null} for none
   */
  public Actionable setActionType(java.lang.Integer actionType) {
    this.actionType = actionType;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getDescription() {
    return description;
  }

  /**
   * @param description description or {@code null} for none
   */
  public Actionable setDescription(java.lang.Integer description) {
    this.description = description;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDetails() {
    return details;
  }

  /**
   * @param details details or {@code null} for none
   */
  public Actionable setDetails(java.lang.String details) {
    this.details = details;
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
  public Actionable setDone(java.lang.Boolean done) {
    this.done = done;
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
  public Actionable setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getTitle() {
    return title;
  }

  /**
   * @param title title or {@code null} for none
   */
  public Actionable setTitle(java.lang.Integer title) {
    this.title = title;
    return this;
  }

  @Override
  public Actionable set(String fieldName, Object value) {
    return (Actionable) super.set(fieldName, value);
  }

  @Override
  public Actionable clone() {
    return (Actionable) super.clone();
  }

}
