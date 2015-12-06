package com.avasthi.roadcompanion.background.tasks;

import android.os.AsyncTask;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.activities.RCGroupActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class RCAddGroupTask extends AsyncTask<Void, Void, List<UserGroup> > {

    private final RCGroupActivity context;
    private final String name;
    private final String description;

    private Logger logger = Logger.getLogger(RCAddGroupTask.class.getName());

    public RCAddGroupTask(RCGroupActivity context, String name, String description) {
        this.context = context;
        this.name = name;
        this.description = description;
    }

    @Override
    protected List<UserGroup> doInBackground(Void... params) {

        try {
            List<UserGroup> groups =  EndpointManager.getEndpoints(context).createGroup(name, description).execute().getItems();
            if (groups != null) {
              return groups;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return null;
    }
    protected void onPostExecute (List<UserGroup> groupsList) {

        context.splashListGroupsScreen(groupsList);
    }
}
