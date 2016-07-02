package com.sanjnan.vitarak.app.badmin.tasks;

import com.sanjnan.vitarak.app.badmin.activities.KKManageItemCategoryActivity;
import com.sanjnan.vitarak.app.badmin.utils.EndpointManager;
import com.sanjnan.vitarak.common.ServerInteractionReturnStatus;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class GetItemCategoryTask extends AbstractItemCategoryTask {


    private Logger logger = Logger.getLogger(GetItemCategoryTask.class.getName());


    public GetItemCategoryTask(KKManageItemCategoryActivity context) {
        super(context);
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            this.itemCategoryList= EndpointManager.getEndpoints(context).getItemCategories().execute().getItems();
            return ServerInteractionReturnStatus.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
}
