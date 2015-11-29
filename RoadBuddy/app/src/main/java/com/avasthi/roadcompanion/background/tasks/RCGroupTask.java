package com.avasthi.roadcompanion.background.tasks;

import android.os.AsyncTask;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Member;
import com.avasthi.roadcompanion.activities.RCGroupActivity;
import com.avasthi.roadcompanion.data.FacebookUser;
import com.avasthi.roadcompanion.data.GroupHeader;
import com.avasthi.roadcompanion.utils.JsonObjectFactory;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class RCGroupTask extends AsyncTask<Void, Void, Member> {

    private final RCGroupActivity context;
    private final GroupHeader groupHeader;
    private final List<FacebookUser> facebookUserList = new ArrayList<>();

    private Logger logger = Logger.getLogger(RCGroupTask.class.getName());

    public RCGroupTask(RCGroupActivity context, GroupHeader groupHeader) {
        this.context = context;
        this.groupHeader = groupHeader;
    }

    @Override
    protected Member doInBackground(Void... params) {

        try {
            GraphResponse response = new GraphRequest(
                    AccessToken.getCurrentAccessToken(),
                    "/" + groupHeader.getId() + "/members",
                    null,
                    HttpMethod.GET).executeAndWait();
            JsonObjectFactory.parseListOfFacebookUsersIntoList(response.getJSONObject(), facebookUserList);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return null;
    }
    protected void onPostExecute (Member member) {

        context.splashListGroupsScreen();
    }
}
