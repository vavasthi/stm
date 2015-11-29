package com.avasthi.roadcompanion.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RoadCompanionMainActivity;
import com.avasthi.roadcompanion.data.GroupHeader;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacebookReadInterface {
    private static FacebookReadInterface INSTANCE;

    private final RoadCompanionMainActivity context;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker readAccessTokenTracker;
    private AccessToken currentReadAccessToken;
    private Profile currentProfile;
    private String facebookId;

    private Map<String, GroupHeader> nameToGroupMap = new HashMap<>();
    private Map<String, GroupHeader> idToGroupMap = new HashMap<>();
    public static void initialize(final RoadCompanionMainActivity context) {
        INSTANCE = new FacebookReadInterface(context);
    }

    private FacebookReadInterface(final RoadCompanionMainActivity context) {
        this.context = context;
        FacebookSdk.sdkInitialize(context);
        currentProfile = Profile.getCurrentProfile();
        currentReadAccessToken = AccessToken.getCurrentAccessToken();
        readAccessTokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                FacebookReadInterface.this.currentReadAccessToken = currentAccessToken;
            }
        };
        profileTracker = new ProfileTracker() {

            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                FacebookReadInterface.this.currentProfile = currentProfile;
            }
        };
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        currentReadAccessToken = AccessToken.getCurrentAccessToken();
                        currentProfile = Profile.getCurrentProfile();
                        facebookId = currentProfile.getId();
                        Toast.makeText(FacebookReadInterface.this.context, "Successful login to facebook.", Toast.LENGTH_LONG).show();
                        context.continueAuthentication();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookReadInterface.this.context, "Cancel login to facebook.", Toast.LENGTH_LONG).show();
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(FacebookReadInterface.this.context, "Error login to facebook.", Toast.LENGTH_LONG).show();
                        // App code
                    }
                });
        LoginManager.getInstance().logInWithReadPermissions(FacebookReadInterface.this.context, Arrays.asList("public_profile", "user_friends", "user_managed_groups"));
    }

    public void forceRelogin() {
        currentProfile = null;
        currentReadAccessToken = null;
        LoginManager.getInstance().logOut();
        initialize(context);
    }
    public Profile getFacebookProfile() {
        return currentProfile;
    }

    public AccessToken getFacebookAccessToken() {
        return currentReadAccessToken;
    }

    public static void fini() {
        if (INSTANCE != null) {

            INSTANCE.profileTracker.stopTracking();
            INSTANCE.readAccessTokenTracker.stopTracking();
            INSTANCE.currentProfile = null;
            INSTANCE.currentReadAccessToken = null;
        }
//        LoginManager.getInstance().logOut();
        INSTANCE = null;
    }

    public static boolean isInitialied() {
        return INSTANCE != null;
    }
    public static FacebookReadInterface getInstance() {
        return INSTANCE;
    }

    public void processActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void updateGroupDetails(JSONObject obj) {
        List<GroupHeader> groupHeaderList = JsonObjectFactory.parseListOfGroupHeaders(obj);
        for (GroupHeader h : groupHeaderList) {

            h.setIconBitmap(getBitmapFromUrl(h.getIconUrl()));
            nameToGroupMap.put(h.getName(), h);
            idToGroupMap.put(h.getId(), h);
        }
    }
    public GroupHeader[] getFacebookGroupHeaders() {
        return nameToGroupMap.values().toArray(new GroupHeader[nameToGroupMap.size()]);
    }
    private Bitmap getBitmapFromUrl(String iconUrl) {
        try {

            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder().url(iconUrl).get().build();
            Response response = httpClient.newCall(request).execute();
            Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
            return bitmap;
        }
        catch (IOException ex) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.broken);
        }
    }
}