package com.khanakirana.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.backend.customerApi.CustomerApi;
import com.khanakirana.backend.customerApi.model.MeasurementCategory;
import com.khanakirana.khanakirana.R;
import com.khanakirana.khanakirana.activities.KKMeasurementCategoryReceivingActivity;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class ListMeasurementCategoryTask extends AsyncTask<Void, Void, Integer> {

    private final KKMeasurementCategoryReceivingActivity context;
    private final CustomerApi customerApi;
    private List<MeasurementCategory> categories;

    private Logger logger = Logger.getLogger(ListMeasurementCategoryTask.class.getName());


    public ListMeasurementCategoryTask(KKMeasurementCategoryReceivingActivity context,
                                       CustomerApi customerApi) {
        this.context = context;
        this.customerApi = customerApi;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            categories = customerApi.listMeasurementCategories().execute().getItems();
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
