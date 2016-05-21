package com.avasthi.roadcompanion.background.tasks;

import android.os.AsyncTask;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.FamilyMemberAndPointsOfInterest;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.PointsOfInterest;
import com.avasthi.roadcompanion.activities.RCAbstractActivity;
import com.avasthi.roadcompanion.activities.RCFamilyMapActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.google.api.client.util.DateTime;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class RCGetFamilyDriveTask extends AsyncTask<Void, Void, List<FamilyMemberAndPointsOfInterest>> {

    private final RCFamilyMapActivity context;
    private final Boolean mapAlreadyCreated;

    private Logger logger = Logger.getLogger(RCGetFamilyDriveTask.class.getName());

    public RCGetFamilyDriveTask(RCFamilyMapActivity context, boolean mapAlreadyCreated) {
        this.context = context;
        this.mapAlreadyCreated = mapAlreadyCreated;
    }


    @Override
    protected List<FamilyMemberAndPointsOfInterest> doInBackground(Void... params) {

        try {
                List<FamilyMemberAndPointsOfInterest>
                        familyMemberAndPointsOfInterestList = EndpointManager.getRoadMeasurementEndpoint(context).getFamilyDrives().execute().getItems();
            if (familyMemberAndPointsOfInterestList != null) {
              return familyMemberAndPointsOfInterestList;
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return null;
    }
    protected void onPostExecute (List<FamilyMemberAndPointsOfInterest> familyMemberAndPointsOfInterestList) {

        if (mapAlreadyCreated) {

            context.updateFamilyMemberAndPointsOfInterestList(familyMemberAndPointsOfInterestList);
        }
        else {

            context.updateFamilyMemberAndPointsOfInterestListAndInitializeMap(familyMemberAndPointsOfInterestList);
        }
    }
}
