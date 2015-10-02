package com.khanakirana.badmin.khanakirana.background.tasks;

import com.khanakirana.backend.userRegistrationApi.UserRegistrationApi;
import com.khanakirana.badmin.khanakirana.activities.KKManageItemCategoryActivity;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class GetItemCategoryTask extends AbstractItemCategoryTask {


    private Logger logger = Logger.getLogger(GetItemCategoryTask.class.getName());


    public GetItemCategoryTask(KKManageItemCategoryActivity context,
                               UserRegistrationApi registrationApiService) {
        super(context, registrationApiService);
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
}
