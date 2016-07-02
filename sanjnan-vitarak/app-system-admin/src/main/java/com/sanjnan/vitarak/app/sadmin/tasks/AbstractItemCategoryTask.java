package com.sanjnan.vitarak.app.sadmin.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.admin.khanakirana.R;
import com.sanjnan.vitarak.app.sadmin.activities.KKManageItemCategoryActivity;
import com.khanakirana.backend.sysadminApi.model.ItemCategory;
import com.sanjnan.vitarak.common.ServerInteractionReturnStatus;

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
