package com.khanakirana.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.backend.customerApi.CustomerApi;
import com.khanakirana.backend.customerApi.model.ItemCategory;
import com.khanakirana.khanakirana.R;
import com.khanakirana.khanakirana.activities.KKManageItemCategoryActivity;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 28/9/15.
 */
abstract class AbstractItemCategoryTask extends AsyncTask<Void, Void, Integer> {

    final KKManageItemCategoryActivity context;
    List<ItemCategory> itemCategoryList;

    private Logger logger = Logger.getLogger(AbstractItemCategoryTask.class.getName());


    public AbstractItemCategoryTask(KKManageItemCategoryActivity context) {
        this.context = context;
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
