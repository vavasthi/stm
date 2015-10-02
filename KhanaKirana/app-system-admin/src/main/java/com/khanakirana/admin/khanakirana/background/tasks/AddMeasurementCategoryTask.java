package com.khanakirana.admin.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.admin.khanakirana.R;
import com.khanakirana.admin.khanakirana.activities.KKAddMeasurementCategoryActivity;
import com.khanakirana.backend.userRegistrationApi.UserRegistrationApi;
import com.khanakirana.backend.userRegistrationApi.model.MeasurementCategory;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AddMeasurementCategoryTask extends AsyncTask<Void, Void, Integer> {

    private final KKAddMeasurementCategoryActivity context;
    private final UserRegistrationApi registrationApiService;
    private final String measurementCategory;
    private final Boolean fractional;

    private Logger logger = Logger.getLogger(AddMeasurementCategoryTask.class.getName());


    public AddMeasurementCategoryTask(KKAddMeasurementCategoryActivity context,
                                      UserRegistrationApi registrationApiService,
                                      String measurementCategory,
                                      Boolean fractional) {
        this.context = context;
        this.registrationApiService = registrationApiService;
        this.measurementCategory = measurementCategory;
        this.fractional = fractional;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            MeasurementCategory mc = registrationApiService.addMeasurementCategory(measurementCategory, fractional).execute();
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
