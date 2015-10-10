package com.khanakirana.admin.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.admin.khanakirana.R;
import com.khanakirana.admin.khanakirana.activities.KKMeasurementCategoryReceivingActivity;
import com.khanakirana.backend.sysadminApi.SysadminApi;
import com.khanakirana.backend.sysadminApi.model.MeasurementCategory;
import com.khanakirana.common.ServerInteractionReturnStatus;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class ListMeasurementCategoryTask extends AsyncTask<Void, Void, Integer> {

    private final KKMeasurementCategoryReceivingActivity context;
    private final SysadminApi sysadminApi;
    private List<MeasurementCategory> categories;

    private Logger logger = Logger.getLogger(ListMeasurementCategoryTask.class.getName());


    public ListMeasurementCategoryTask(KKMeasurementCategoryReceivingActivity context,
                                       SysadminApi sysadminApi) {
        this.context = context;
        this.sysadminApi = sysadminApi;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            categories = sysadminApi.listMeasurementCategories().execute().getItems();
            return ServerInteractionReturnStatus.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
    protected void onPostExecute (Integer result) {

        switch (result) {
            case ServerInteractionReturnStatus.SUCCESS:
                context.setCategories(categories);
                break;
            default:
                Toast.makeText(context, R.string.kk_measurement_category_addition_failed, Toast.LENGTH_LONG);
        }
    }
}
