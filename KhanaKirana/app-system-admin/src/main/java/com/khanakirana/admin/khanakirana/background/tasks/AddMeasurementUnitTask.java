package com.khanakirana.admin.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.admin.khanakirana.R;
import com.khanakirana.admin.khanakirana.activities.KKAddMeasurementUnitActivity;
import com.khanakirana.backend.sysadminApi.SysadminApi;
import com.khanakirana.backend.sysadminApi.model.MeasurementUnit;
import com.khanakirana.common.ServerInteractionReturnStatus;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AddMeasurementUnitTask extends AsyncTask<Void, Void, Integer> {

    private final KKAddMeasurementUnitActivity context;
    private final SysadminApi sysadminApi;
    private final String name;
    private final String acronym;
    private final String measurementCategory;
    private final Boolean primaryUnit;
    private final Double factor;

    private Logger logger = Logger.getLogger(AddMeasurementUnitTask.class.getName());


    public AddMeasurementUnitTask(KKAddMeasurementUnitActivity context,
                                  SysadminApi sysadminApi,
                                  String name,
                                  String acronym,
                                  String measurementCategory,
                                  Boolean primaryUnit,
                                  Double factor) {
        this.context = context;
        this.sysadminApi = sysadminApi;
        this.name = name;
        this.acronym = acronym;
        this.measurementCategory = measurementCategory;
        this.primaryUnit = primaryUnit;
        this.factor = factor;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            MeasurementUnit unit = sysadminApi.addMeasurementUnit(name, acronym, measurementCategory, primaryUnit, factor).execute();
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
