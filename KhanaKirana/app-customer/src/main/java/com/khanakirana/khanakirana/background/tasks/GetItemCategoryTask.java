package com.khanakirana.khanakirana.background.tasks;

import com.khanakirana.backend.customerApi.CustomerApi;
import com.khanakirana.khanakirana.activities.KKManageItemCategoryActivity;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class GetItemCategoryTask extends AbstractItemCategoryTask {


    private Logger logger = Logger.getLogger(GetItemCategoryTask.class.getName());


    public GetItemCategoryTask(KKManageItemCategoryActivity context,
                               CustomerApi customerApi) {
        super(context, customerApi);
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            this.itemCategoryList= customerApi.getItemCategories().execute().getItems();
            return ServerInteractionReturnStatus.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
}
