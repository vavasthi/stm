package com.avasthi.roadcompanion.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.admin.khanakirana.R;
import com.avasthi.roadcompanion.activities.KKAddMeasurementUnitActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.khanakirana.backend.sysadminApi.model.MeasurementUnit;
import com.avasthi.roadcompanion.common.ServerInteractionReturnStatus;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AddMeasurementUnitTask extends AsyncTask<Void, Void, Integer> {

    private final KKAddMeasurementUnitActivity context;
    private final String name;
    private final String acronym;
    private final String measurementCategory;
    private final Boolean primaryUnit;
    private final Double factor;

    private Logger logger = Logger.getLogger(AddMeasurementUnitTask.class.getName());


    public AddMeasurementUnitTask(KKAddMeasurementUnitActivity context,
                                  String name,
                                  String acronym,
                                  String measurementCategory,
                                  Boolean primaryUnit,
                                  Double factor) {
        this.context = context;
        this.name = name;
        this.acronym = acronym;
        this.measurementCategory = measurementCategory;
        this.primaryUnit = primaryUnit;
        this.factor = factor;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            MeasurementUnit unit = EndpointManager.getEndpoints(context).addMeasurementUnit(name, acronym, measurementCategory, primaryUnit, factor).execute();
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
