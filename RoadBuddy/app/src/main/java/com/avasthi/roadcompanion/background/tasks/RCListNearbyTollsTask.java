package com.avasthi.roadcompanion.background.tasks;

import android.os.AsyncTask;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.activities.RCGroupActivity;
import com.avasthi.roadcompanion.activities.RCTollActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class RCListNearbyTollsTask extends AsyncTask<Void, Void, List<UserGroup> > {

    private final RCTollActivity context;

    private Logger logger = Logger.getLogger(RCListNearbyTollsTask.class.getName());

    public RCListNearbyTollsTask(RCTollActivity context) {
        this.context = context;
    }

    @Override
    protected List<UserGroup> doInBackground(Void... params) {

        try {
            List<UserGroup> groups =  EndpointManager.getRoadMeasurementEndpoint(context).listGroups().execute().getItems();
            if (groups != null) {
              return groups;
            }
            else {
                return new ArrayList<UserGroup>();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return null;
    }
//    protected void onPostExecute (List<UserGroup> groupsList) {

  //      context.splashListGroupsScreen(groupsList);
    //}
}
