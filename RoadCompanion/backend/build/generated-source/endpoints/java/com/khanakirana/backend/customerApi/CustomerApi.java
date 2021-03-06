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
 * on 2015-11-03 at 12:52:21 UTC 
 * Modify at your own risk.
 */

package com.khanakirana.backend.customerApi;

/**
 * Service definition for CustomerApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link CustomerApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class CustomerApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the customerApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "customerApi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public CustomerApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  CustomerApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "authenticate".
   *
   * This request holds the parameters needed by the customerApi server.  After setting any optional
   * parameters, call the {@link Authenticate#execute()} method to invoke the remote operation.
   *
   * @param email
   * @param password
   * @param googleUser
   * @return the request
   */
  public Authenticate authenticate(java.lang.String email, java.lang.String password, java.lang.Boolean googleUser) throws java.io.IOException {
    Authenticate result = new Authenticate(email, password, googleUser);
    initialize(result);
    return result;
  }

  public class Authenticate extends CustomerApiRequest<com.khanakirana.backend.customerApi.model.UserAccount> {

    private static final String REST_PATH = "authenticate/{email}/{password}/{googleUser}";

    /**
     * Create a request for the method "authenticate".
     *
     * This request holds the parameters needed by the the customerApi server.  After setting any
     * optional parameters, call the {@link Authenticate#execute()} method to invoke the remote
     * operation. <p> {@link
     * Authenticate#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param email
     * @param password
     * @param googleUser
     * @since 1.13
     */
    protected Authenticate(java.lang.String email, java.lang.String password, java.lang.Boolean googleUser) {
      super(CustomerApi.this, "POST", REST_PATH, null, com.khanakirana.backend.customerApi.model.UserAccount.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.password = com.google.api.client.util.Preconditions.checkNotNull(password, "Required parameter password must be specified.");
      this.googleUser = com.google.api.client.util.Preconditions.checkNotNull(googleUser, "Required parameter googleUser must be specified.");
    }

    @Override
    public Authenticate setAlt(java.lang.String alt) {
      return (Authenticate) super.setAlt(alt);
    }

    @Override
    public Authenticate setFields(java.lang.String fields) {
      return (Authenticate) super.setFields(fields);
    }

    @Override
    public Authenticate setKey(java.lang.String key) {
      return (Authenticate) super.setKey(key);
    }

    @Override
    public Authenticate setOauthToken(java.lang.String oauthToken) {
      return (Authenticate) super.setOauthToken(oauthToken);
    }

    @Override
    public Authenticate setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Authenticate) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Authenticate setQuotaUser(java.lang.String quotaUser) {
      return (Authenticate) super.setQuotaUser(quotaUser);
    }

    @Override
    public Authenticate setUserIp(java.lang.String userIp) {
      return (Authenticate) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public Authenticate setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String password;

    /**

     */
    public java.lang.String getPassword() {
      return password;
    }

    public Authenticate setPassword(java.lang.String password) {
      this.password = password;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Boolean googleUser;

    /**

     */
    public java.lang.Boolean getGoogleUser() {
      return googleUser;
    }

    public Authenticate setGoogleUser(java.lang.Boolean googleUser) {
      this.googleUser = googleUser;
      return this;
    }

    @Override
    public Authenticate set(String parameterName, Object value) {
      return (Authenticate) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getItemCategories".
   *
   * This request holds the parameters needed by the customerApi server.  After setting any optional
   * parameters, call the {@link GetItemCategories#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public GetItemCategories getItemCategories() throws java.io.IOException {
    GetItemCategories result = new GetItemCategories();
    initialize(result);
    return result;
  }

  public class GetItemCategories extends CustomerApiRequest<com.khanakirana.backend.customerApi.model.ItemCategoryCollection> {

    private static final String REST_PATH = "itemcategorycollection";

    /**
     * Create a request for the method "getItemCategories".
     *
     * This request holds the parameters needed by the the customerApi server.  After setting any
     * optional parameters, call the {@link GetItemCategories#execute()} method to invoke the remote
     * operation. <p> {@link GetItemCategories#initialize(com.google.api.client.googleapis.services.Ab
     * stractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected GetItemCategories() {
      super(CustomerApi.this, "GET", REST_PATH, null, com.khanakirana.backend.customerApi.model.ItemCategoryCollection.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetItemCategories setAlt(java.lang.String alt) {
      return (GetItemCategories) super.setAlt(alt);
    }

    @Override
    public GetItemCategories setFields(java.lang.String fields) {
      return (GetItemCategories) super.setFields(fields);
    }

    @Override
    public GetItemCategories setKey(java.lang.String key) {
      return (GetItemCategories) super.setKey(key);
    }

    @Override
    public GetItemCategories setOauthToken(java.lang.String oauthToken) {
      return (GetItemCategories) super.setOauthToken(oauthToken);
    }

    @Override
    public GetItemCategories setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetItemCategories) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetItemCategories setQuotaUser(java.lang.String quotaUser) {
      return (GetItemCategories) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetItemCategories setUserIp(java.lang.String userIp) {
      return (GetItemCategories) super.setUserIp(userIp);
    }

    @Override
    public GetItemCategories set(String parameterName, Object value) {
      return (GetItemCategories) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "getUnitsForCategory".
   *
   * This request holds the parameters needed by the customerApi server.  After setting any optional
   * parameters, call the {@link GetUnitsForCategory#execute()} method to invoke the remote operation.
   *
   * @param measurementCategory
   * @return the request
   */
  public GetUnitsForCategory getUnitsForCategory(java.lang.String measurementCategory) throws java.io.IOException {
    GetUnitsForCategory result = new GetUnitsForCategory(measurementCategory);
    initialize(result);
    return result;
  }

  public class GetUnitsForCategory extends CustomerApiRequest<com.khanakirana.backend.customerApi.model.MeasurementUnitCollection> {

    private static final String REST_PATH = "measurementunitcollection/{measurementCategory}";

    /**
     * Create a request for the method "getUnitsForCategory".
     *
     * This request holds the parameters needed by the the customerApi server.  After setting any
     * optional parameters, call the {@link GetUnitsForCategory#execute()} method to invoke the remote
     * operation. <p> {@link GetUnitsForCategory#initialize(com.google.api.client.googleapis.services.
     * AbstractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @param measurementCategory
     * @since 1.13
     */
    protected GetUnitsForCategory(java.lang.String measurementCategory) {
      super(CustomerApi.this, "GET", REST_PATH, null, com.khanakirana.backend.customerApi.model.MeasurementUnitCollection.class);
      this.measurementCategory = com.google.api.client.util.Preconditions.checkNotNull(measurementCategory, "Required parameter measurementCategory must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public GetUnitsForCategory setAlt(java.lang.String alt) {
      return (GetUnitsForCategory) super.setAlt(alt);
    }

    @Override
    public GetUnitsForCategory setFields(java.lang.String fields) {
      return (GetUnitsForCategory) super.setFields(fields);
    }

    @Override
    public GetUnitsForCategory setKey(java.lang.String key) {
      return (GetUnitsForCategory) super.setKey(key);
    }

    @Override
    public GetUnitsForCategory setOauthToken(java.lang.String oauthToken) {
      return (GetUnitsForCategory) super.setOauthToken(oauthToken);
    }

    @Override
    public GetUnitsForCategory setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (GetUnitsForCategory) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public GetUnitsForCategory setQuotaUser(java.lang.String quotaUser) {
      return (GetUnitsForCategory) super.setQuotaUser(quotaUser);
    }

    @Override
    public GetUnitsForCategory setUserIp(java.lang.String userIp) {
      return (GetUnitsForCategory) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String measurementCategory;

    /**

     */
    public java.lang.String getMeasurementCategory() {
      return measurementCategory;
    }

    public GetUnitsForCategory setMeasurementCategory(java.lang.String measurementCategory) {
      this.measurementCategory = measurementCategory;
      return this;
    }

    @Override
    public GetUnitsForCategory set(String parameterName, Object value) {
      return (GetUnitsForCategory) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "isRegisteredUser".
   *
   * This request holds the parameters needed by the customerApi server.  After setting any optional
   * parameters, call the {@link IsRegisteredUser#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public IsRegisteredUser isRegisteredUser() throws java.io.IOException {
    IsRegisteredUser result = new IsRegisteredUser();
    initialize(result);
    return result;
  }

  public class IsRegisteredUser extends CustomerApiRequest<com.khanakirana.backend.customerApi.model.UserAccount> {

    private static final String REST_PATH = "isRegisteredUser";

    /**
     * Create a request for the method "isRegisteredUser".
     *
     * This request holds the parameters needed by the the customerApi server.  After setting any
     * optional parameters, call the {@link IsRegisteredUser#execute()} method to invoke the remote
     * operation. <p> {@link IsRegisteredUser#initialize(com.google.api.client.googleapis.services.Abs
     * tractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected IsRegisteredUser() {
      super(CustomerApi.this, "POST", REST_PATH, null, com.khanakirana.backend.customerApi.model.UserAccount.class);
    }

    @Override
    public IsRegisteredUser setAlt(java.lang.String alt) {
      return (IsRegisteredUser) super.setAlt(alt);
    }

    @Override
    public IsRegisteredUser setFields(java.lang.String fields) {
      return (IsRegisteredUser) super.setFields(fields);
    }

    @Override
    public IsRegisteredUser setKey(java.lang.String key) {
      return (IsRegisteredUser) super.setKey(key);
    }

    @Override
    public IsRegisteredUser setOauthToken(java.lang.String oauthToken) {
      return (IsRegisteredUser) super.setOauthToken(oauthToken);
    }

    @Override
    public IsRegisteredUser setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (IsRegisteredUser) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public IsRegisteredUser setQuotaUser(java.lang.String quotaUser) {
      return (IsRegisteredUser) super.setQuotaUser(quotaUser);
    }

    @Override
    public IsRegisteredUser setUserIp(java.lang.String userIp) {
      return (IsRegisteredUser) super.setUserIp(userIp);
    }

    @Override
    public IsRegisteredUser set(String parameterName, Object value) {
      return (IsRegisteredUser) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "listMeasurementCategories".
   *
   * This request holds the parameters needed by the customerApi server.  After setting any optional
   * parameters, call the {@link ListMeasurementCategories#execute()} method to invoke the remote
   * operation.
   *
   * @return the request
   */
  public ListMeasurementCategories listMeasurementCategories() throws java.io.IOException {
    ListMeasurementCategories result = new ListMeasurementCategories();
    initialize(result);
    return result;
  }

  public class ListMeasurementCategories extends CustomerApiRequest<com.khanakirana.backend.customerApi.model.MeasurementCategoryCollection> {

    private static final String REST_PATH = "lisMeasurementCategories";

    /**
     * Create a request for the method "listMeasurementCategories".
     *
     * This request holds the parameters needed by the the customerApi server.  After setting any
     * optional parameters, call the {@link ListMeasurementCategories#execute()} method to invoke the
     * remote operation. <p> {@link ListMeasurementCategories#initialize(com.google.api.client.googlea
     * pis.services.AbstractGoogleClientRequest)} must be called to initialize this instance
     * immediately after invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected ListMeasurementCategories() {
      super(CustomerApi.this, "POST", REST_PATH, null, com.khanakirana.backend.customerApi.model.MeasurementCategoryCollection.class);
    }

    @Override
    public ListMeasurementCategories setAlt(java.lang.String alt) {
      return (ListMeasurementCategories) super.setAlt(alt);
    }

    @Override
    public ListMeasurementCategories setFields(java.lang.String fields) {
      return (ListMeasurementCategories) super.setFields(fields);
    }

    @Override
    public ListMeasurementCategories setKey(java.lang.String key) {
      return (ListMeasurementCategories) super.setKey(key);
    }

    @Override
    public ListMeasurementCategories setOauthToken(java.lang.String oauthToken) {
      return (ListMeasurementCategories) super.setOauthToken(oauthToken);
    }

    @Override
    public ListMeasurementCategories setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ListMeasurementCategories) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ListMeasurementCategories setQuotaUser(java.lang.String quotaUser) {
      return (ListMeasurementCategories) super.setQuotaUser(quotaUser);
    }

    @Override
    public ListMeasurementCategories setUserIp(java.lang.String userIp) {
      return (ListMeasurementCategories) super.setUserIp(userIp);
    }

    @Override
    public ListMeasurementCategories set(String parameterName, Object value) {
      return (ListMeasurementCategories) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "register".
   *
   * This request holds the parameters needed by the customerApi server.  After setting any optional
   * parameters, call the {@link Register#execute()} method to invoke the remote operation.
   *
   * @param name
   * @param address
   * @param mobile
   * @param city
   * @param state
   * @param latitude
   * @param longitude
   * @return the request
   */
  public Register register(java.lang.String name, java.lang.String address, java.lang.String mobile, java.lang.String city, java.lang.String state, java.lang.Double latitude, java.lang.Double longitude) throws java.io.IOException {
    Register result = new Register(name, address, mobile, city, state, latitude, longitude);
    initialize(result);
    return result;
  }

  public class Register extends CustomerApiRequest<com.khanakirana.backend.customerApi.model.UserAccount> {

    private static final String REST_PATH = "register/{name}/{address}/{mobile}/{city}/{state}/{latitude}/{longitude}";

    /**
     * Create a request for the method "register".
     *
     * This request holds the parameters needed by the the customerApi server.  After setting any
     * optional parameters, call the {@link Register#execute()} method to invoke the remote operation.
     * <p> {@link
     * Register#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param name
     * @param address
     * @param mobile
     * @param city
     * @param state
     * @param latitude
     * @param longitude
     * @since 1.13
     */
    protected Register(java.lang.String name, java.lang.String address, java.lang.String mobile, java.lang.String city, java.lang.String state, java.lang.Double latitude, java.lang.Double longitude) {
      super(CustomerApi.this, "POST", REST_PATH, null, com.khanakirana.backend.customerApi.model.UserAccount.class);
      this.name = com.google.api.client.util.Preconditions.checkNotNull(name, "Required parameter name must be specified.");
      this.address = com.google.api.client.util.Preconditions.checkNotNull(address, "Required parameter address must be specified.");
      this.mobile = com.google.api.client.util.Preconditions.checkNotNull(mobile, "Required parameter mobile must be specified.");
      this.city = com.google.api.client.util.Preconditions.checkNotNull(city, "Required parameter city must be specified.");
      this.state = com.google.api.client.util.Preconditions.checkNotNull(state, "Required parameter state must be specified.");
      this.latitude = com.google.api.client.util.Preconditions.checkNotNull(latitude, "Required parameter latitude must be specified.");
      this.longitude = com.google.api.client.util.Preconditions.checkNotNull(longitude, "Required parameter longitude must be specified.");
    }

    @Override
    public Register setAlt(java.lang.String alt) {
      return (Register) super.setAlt(alt);
    }

    @Override
    public Register setFields(java.lang.String fields) {
      return (Register) super.setFields(fields);
    }

    @Override
    public Register setKey(java.lang.String key) {
      return (Register) super.setKey(key);
    }

    @Override
    public Register setOauthToken(java.lang.String oauthToken) {
      return (Register) super.setOauthToken(oauthToken);
    }

    @Override
    public Register setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (Register) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public Register setQuotaUser(java.lang.String quotaUser) {
      return (Register) super.setQuotaUser(quotaUser);
    }

    @Override
    public Register setUserIp(java.lang.String userIp) {
      return (Register) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String name;

    /**

     */
    public java.lang.String getName() {
      return name;
    }

    public Register setName(java.lang.String name) {
      this.name = name;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String address;

    /**

     */
    public java.lang.String getAddress() {
      return address;
    }

    public Register setAddress(java.lang.String address) {
      this.address = address;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String mobile;

    /**

     */
    public java.lang.String getMobile() {
      return mobile;
    }

    public Register setMobile(java.lang.String mobile) {
      this.mobile = mobile;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String city;

    /**

     */
    public java.lang.String getCity() {
      return city;
    }

    public Register setCity(java.lang.String city) {
      this.city = city;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String state;

    /**

     */
    public java.lang.String getState() {
      return state;
    }

    public Register setState(java.lang.String state) {
      this.state = state;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double latitude;

    /**

     */
    public java.lang.Double getLatitude() {
      return latitude;
    }

    public Register setLatitude(java.lang.Double latitude) {
      this.latitude = latitude;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double longitude;

    /**

     */
    public java.lang.Double getLongitude() {
      return longitude;
    }

    public Register setLongitude(java.lang.Double longitude) {
      this.longitude = longitude;
      return this;
    }

    @Override
    public Register set(String parameterName, Object value) {
      return (Register) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "supportedRegions".
   *
   * This request holds the parameters needed by the customerApi server.  After setting any optional
   * parameters, call the {@link SupportedRegions#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public SupportedRegions supportedRegions() throws java.io.IOException {
    SupportedRegions result = new SupportedRegions();
    initialize(result);
    return result;
  }

  public class SupportedRegions extends CustomerApiRequest<com.khanakirana.backend.customerApi.model.UserAccountRegionCollection> {

    private static final String REST_PATH = "supportedRegions";

    /**
     * Create a request for the method "supportedRegions".
     *
     * This request holds the parameters needed by the the customerApi server.  After setting any
     * optional parameters, call the {@link SupportedRegions#execute()} method to invoke the remote
     * operation. <p> {@link SupportedRegions#initialize(com.google.api.client.googleapis.services.Abs
     * tractGoogleClientRequest)} must be called to initialize this instance immediately after
     * invoking the constructor. </p>
     *
     * @since 1.13
     */
    protected SupportedRegions() {
      super(CustomerApi.this, "POST", REST_PATH, null, com.khanakirana.backend.customerApi.model.UserAccountRegionCollection.class);
    }

    @Override
    public SupportedRegions setAlt(java.lang.String alt) {
      return (SupportedRegions) super.setAlt(alt);
    }

    @Override
    public SupportedRegions setFields(java.lang.String fields) {
      return (SupportedRegions) super.setFields(fields);
    }

    @Override
    public SupportedRegions setKey(java.lang.String key) {
      return (SupportedRegions) super.setKey(key);
    }

    @Override
    public SupportedRegions setOauthToken(java.lang.String oauthToken) {
      return (SupportedRegions) super.setOauthToken(oauthToken);
    }

    @Override
    public SupportedRegions setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (SupportedRegions) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public SupportedRegions setQuotaUser(java.lang.String quotaUser) {
      return (SupportedRegions) super.setQuotaUser(quotaUser);
    }

    @Override
    public SupportedRegions setUserIp(java.lang.String userIp) {
      return (SupportedRegions) super.setUserIp(userIp);
    }

    @Override
    public SupportedRegions set(String parameterName, Object value) {
      return (SupportedRegions) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link CustomerApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link CustomerApi}. */
    @Override
    public CustomerApi build() {
      return new CustomerApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link CustomerApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setCustomerApiRequestInitializer(
        CustomerApiRequestInitializer customerapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(customerapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
