package com.avasthi.roadcompanion.utils;

import android.app.Activity;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpRequestInitializer;
import com.avasthi.roadcompanion.KKAndroidConstants;
import com.khanakirana.backend.sysadminApi.SysadminApi;
import com.avasthi.roadcompanion.common.KKConstants;

import java.io.IOException;

/**
 * Created by vavasthi on 14/10/15.
 */
public class EndpointManager {

    private static String accountName;

    protected static final String AUDIENCE = "server:client_id:" + KKConstants.WEB_CLIENT_ID;

    public static SysadminApi getEndpoints(Activity activity) {

        // Create API handler
        HttpRequestInitializer requestInitializer = getRequestInitializer(activity);
        SysadminApi.Builder builder = new SysadminApi.Builder(
                AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(),
                requestInitializer)
                .setRootUrl(KKAndroidConstants.ROOT_URL)
                .setGoogleClientRequestInitializer(
                        new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(
                                    final AbstractGoogleClientRequest<?>
                                            abstractGoogleClientRequest)
                                    throws IOException {
                                abstractGoogleClientRequest
                                        .setDisableGZipContent(true);
                            }
                        }
                );

        return builder.build();
    }

    /**
     * Returns appropriate HttpRequestInitializer depending whether the
     * application is configured to require users to be signed in or not.
     *
     * @return an appropriate HttpRequestInitializer.
     */
    public  static HttpRequestInitializer getRequestInitializer(Activity activity) {

        GoogleAccountCredential gac = GoogleAccountCredential.usingAudience(activity, AUDIENCE);
        gac.setSelectedAccountName(accountName);
        return gac;
    }
    public static void setAccountName(String accountName) {
        EndpointManager.accountName = accountName;
    }
    public static String getAccountName() {
        return accountName;
    }
}
