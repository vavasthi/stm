package com.avasthi.roadcompanion.utils;

import android.content.Intent;
import android.widget.Toast;

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

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacebookPublishInterface {
    private static FacebookPublishInterface INSTANCE;

    private final RoadCompanionMainActivity context;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker publishAccessTokenTracker;
    private AccessToken currentPublishAccessToken;
    private Profile currentProfile;
    private String facebookId;

    private Map<String, GroupHeader> nameToGroupMap = new HashMap<>();
    private Map<String, GroupHeader> idToGroupMap = new HashMap<>();
    public static void initialize(final RoadCompanionMainActivity context) {
        INSTANCE = new FacebookPublishInterface(context);
    }

    private FacebookPublishInterface(final RoadCompanionMainActivity context) {
        this.context = context;
        FacebookSdk.sdkInitialize(context);
        currentProfile = Profile.getCurrentProfile();
        currentPublishAccessToken = AccessToken.getCurrentAccessToken();
        publishAccessTokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                FacebookPublishInterface.this.currentPublishAccessToken = currentAccessToken;
            }
        };
        profileTracker = new ProfileTracker() {

            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                FacebookPublishInterface.this.currentProfile = currentProfile;
            }
        };
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        currentPublishAccessToken = AccessToken.getCurrentAccessToken();
                        currentProfile = Profile.getCurrentProfile();
                        facebookId = currentProfile.getId();
                        Toast.makeText(FacebookPublishInterface.this.context, "Successful login to facebook.", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookPublishInterface.this.context, "Cancel login to facebook.", Toast.LENGTH_LONG).show();
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(FacebookPublishInterface.this.context, "Error login to facebook.", Toast.LENGTH_LONG).show();
                        // App code
                    }
                });
        LoginManager.getInstance().logInWithPublishPermissions(FacebookPublishInterface.this.context, Arrays.asList("publish_actions"));
    }

    public void forceRelogin() {
        currentProfile = null;
        currentPublishAccessToken = null;
        LoginManager.getInstance().logOut();
        initialize(context);
    }
    public Profile getFacebookProfile() {
        return currentProfile;
    }

    public AccessToken getFacebookAccessToken() {
        return currentPublishAccessToken;
    }

    public static void fini() {
        if (INSTANCE != null) {

            INSTANCE.profileTracker.stopTracking();
            INSTANCE.publishAccessTokenTracker.stopTracking();
            INSTANCE.currentProfile = null;
            INSTANCE.currentPublishAccessToken = null;
        }
//        LoginManager.getInstance().logOut();
        INSTANCE = null;
    }

    public static boolean isInitialied() {
        return INSTANCE != null;
    }
    public static FacebookPublishInterface getInstance() {
        return INSTANCE;
    }

    public void processActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void updateGroupDetails(JSONObject obj) {
        List<GroupHeader> groupHeaderList = JsonObjectFactory.parseListOfGroupHeaders(obj);
        for (GroupHeader h : groupHeaderList) {
            nameToGroupMap.put(h.getName(), h);
            idToGroupMap.put(h.getId(), h);
        }
    }
    public GroupHeader[] getFacebookGroupHeaders() {
        return nameToGroupMap.values().toArray(new GroupHeader[nameToGroupMap.size()]);
    }
}