package com.khanakirana.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.backend.userRegistrationApi.UserRegistrationApi;
import com.khanakirana.backend.userRegistrationApi.model.MeasurementUnit;
import com.khanakirana.khanakirana.R;
import com.khanakirana.khanakirana.activities.KKAddMeasurementUnitActivity;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AddMeasurementUnitTask extends AsyncTask<Void, Void, Integer> {

    private final KKAddMeasurementUnitActivity context;
    private final UserRegistrationApi registrationApiService;
    private final String name;
    private final String acronym;
    private final String measurementCategory;
    private final Boolean primaryUnit;
    private final Double factor;

    private Logger logger = Logger.getLogger(AddMeasurementUnitTask.class.getName());


    public AddMeasurementUnitTask(KKAddMeasurementUnitActivity context,
                                  UserRegistrationApi registrationApiService,
                                  String name,
                                  String acronym,
                                  String measurementCategory,
                                  Boolean primaryUnit,
                                  Double factor) {
        this.context = context;
        this.registrationApiService = registrationApiService;
        this.name = name;
        this.acronym = acronym;
        this.measurementCategory = measurementCategory;
        this.primaryUnit = primaryUnit;
        this.factor = factor;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            MeasurementUnit unit = registrationApiService.addMeasurementUnit(name, acronym, measurementCategory, primaryUnit, factor).execute();
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
                Toast.makeText(context, R.string.kk_measurement_unit_addition_failed, Toast.LENGTH_LONG);
        }
    }
}
