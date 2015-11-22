package com.avasthi.test;


import android.app.Activity;
import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookBroadcastReceiver;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

/**
 * Created by vavasthi on 22/11/15.
 */
public class FacebookInterface {
    private static FacebookInterface INSTANCE;

    private Activity context;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken currentAccessToken;
    private Profile currentProfile;

    public static void initialize(Activity context) {
        INSTANCE = new FacebookInterface(context);
    }

    private FacebookInterface(Activity context) {
        this.context = context;
        FacebookSdk.sdkInitialize(context);
        currentProfile = Profile.getCurrentProfile();
        currentAccessToken = AccessToken.getCurrentAccessToken();
        accessTokenTracker = new AccessTokenTracker() {

            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                FacebookInterface.this.currentAccessToken = currentAccessToken;
            }
        };
        profileTracker = new ProfileTracker() {

            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                FacebookInterface.this.currentProfile = currentProfile;
            }
        };
        if (currentProfile == null || currentAccessToken == null) {

            callbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            // App code
                            currentAccessToken = AccessToken.getCurrentAccessToken();
                            currentProfile = Profile.getCurrentProfile();
                            Toast.makeText(FacebookInterface.this.context, "Successful login to facebook.", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(FacebookInterface.this.context, "Cancel login to facebook.", Toast.LENGTH_LONG).show();
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            Toast.makeText(FacebookInterface.this.context, "Error login to facebook.", Toast.LENGTH_LONG).show();
                            // App code
                        }
                    });
            LoginManager.getInstance().logInWithPublishPermissions(FacebookInterface.this.context, Arrays.asList("publish_actions"));
        }
    }

    public Profile getFacebookProfile() {
        return currentProfile;
    }

    public AccessToken getFacebookAccessToken() {
        return currentAccessToken;
    }

    public void fini() {
        profileTracker.stopTracking();
        accessTokenTracker.stopTracking();
    }

    public static FacebookInterface getInstance() {
        return INSTANCE;
    }

    public void processActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}