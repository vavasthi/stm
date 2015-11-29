package com.avasthi.roadcompanion.background.tasks;

import android.os.AsyncTask;
import android.os.Bundle;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Member;
import com.avasthi.roadcompanion.activities.RCGroupActivity;
import com.avasthi.roadcompanion.utils.FacebookReadInterface;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class GetFacebookGroupsTask extends AsyncTask<Void, Void, Member> {

    private final RCGroupActivity context;

    private Logger logger = Logger.getLogger(GetFacebookGroupsTask.class.getName());

    public GetFacebookGroupsTask(RCGroupActivity context) {
        this.context = context;
    }

    @Override
    protected Member doInBackground(Void... params) {

        try {
            Bundle b = new Bundle();
            b.putString("fields", "name, privacy, id, icon");
            GraphResponse response = new GraphRequest(
                    FacebookReadInterface.getInstance().getFacebookAccessToken(),
                    "/me/groups",
                    b,
                    HttpMethod.GET).executeAndWait();
            FacebookReadInterface.getInstance().updateGroupDetails(response.getJSONObject());
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return null;
    }
    protected void onPostExecute (Member member) {
        context.populateListView();
    }
}
