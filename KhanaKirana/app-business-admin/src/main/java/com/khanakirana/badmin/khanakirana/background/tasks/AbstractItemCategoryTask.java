package com.khanakirana.badmin.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.backend.businessApi.BusinessApi;
import com.khanakirana.backend.businessApi.model.ItemCategory;
import com.khanakirana.badmin.khanakirana.R;
import com.khanakirana.badmin.khanakirana.activities.KKManageItemCategoryActivity;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 28/9/15.
 */
abstract class AbstractItemCategoryTask extends AsyncTask<Void, Void, Integer> {

    final KKManageItemCategoryActivity context;
    final BusinessApi businessApiService;
    List<ItemCategory> itemCategoryList;

    private Logger logger = Logger.getLogger(AbstractItemCategoryTask.class.getName());


    public AbstractItemCategoryTask(KKManageItemCategoryActivity context,
                                    BusinessApi businessApiService) {
        this.context = context;
        this.businessApiService = businessApiService;
    }

    protected void onPostExecute (Integer result) {

        switch (result) {
            case ServerInteractionReturnStatus.SUCCESS:
                context.setItemCategories(itemCategoryList);
                break;
            default:
                Toast.makeText(context, R.string.kk_item_category_operation_failed, Toast.LENGTH_LONG);
        }
    }
}
