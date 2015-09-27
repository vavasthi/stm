package com.khanakirana.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.backend.userRegistrationApi.UserRegistrationApi;
import com.khanakirana.backend.userRegistrationApi.model.ItemCategory;
import com.khanakirana.backend.userRegistrationApi.model.MeasurementCategory;
import com.khanakirana.khanakirana.R;
import com.khanakirana.khanakirana.activities.KKManageItemCategory;
import com.khanakirana.khanakirana.activities.KKMeasurementCategoryReceivingActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class GetItemCategoryTask extends AsyncTask<Void, Void, Integer> {

    private final KKManageItemCategory context;
    private final UserRegistrationApi registrationApiService;
    private List<ItemCategory> itemCategoryList;

    private Logger logger = Logger.getLogger(GetItemCategoryTask.class.getName());


    public GetItemCategoryTask(KKManageItemCategory context,
                               UserRegistrationApi registrationApiService) {
        this.context = context;
        this.registrationApiService = registrationApiService;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            this.itemCategoryList= registrationApiService.getItemCategories().execute().getItems();
            return ServerInteractionReturnStatus.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
    protected void onPostExecute (Integer result) {

        switch (result) {
            case ServerInteractionReturnStatus.SUCCESS:
                context.setItemCategories(itemCategoryList);
                break;
            default:
                Toast.makeText(context, R.string.kk_measurement_category_addition_failed, Toast.LENGTH_LONG);
        }
    }
}
