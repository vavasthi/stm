package com.sanjnan.vitarak.app.sadmin.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.admin.khanakirana.R;
import com.sanjnan.vitarak.app.sadmin.activities.KKAddMeasurementCategoryActivity;
import com.sanjnan.vitarak.app.sadmin.utils.EndpointManager;
import com.khanakirana.backend.sysadminApi.model.MeasurementCategory;
import com.sanjnan.vitarak.common.ServerInteractionReturnStatus;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AddMeasurementCategoryTask extends AsyncTask<Void, Void, Integer> {

    private final KKAddMeasurementCategoryActivity context;
    private final String measurementCategory;
    private final Boolean fractional;

    private Logger logger = Logger.getLogger(AddMeasurementCategoryTask.class.getName());


    public AddMeasurementCategoryTask(KKAddMeasurementCategoryActivity context,
                                      String measurementCategory,
                                      Boolean fractional) {
        this.context = context;
        this.measurementCategory = measurementCategory;
        this.fractional = fractional;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            MeasurementCategory mc = EndpointManager.getEndpoints(context).addMeasurementCategory(measurementCategory, fractional).execute();
            return ServerInteractionReturnStatus.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
    protected void onPostExecute (Integer result) {

        switch (result) {
            case ServerInteractionReturnStatus.SUCCESS:
                break;
            default:
                Toast.makeText(context, R.string.kk_measurement_category_addition_failed, Toast.LENGTH_LONG);
        }
    }
}
