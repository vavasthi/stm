package com.khanakirana.admin.khanakirana.background.tasks;

import com.khanakirana.admin.khanakirana.activities.KKManageItemCategoryActivity;
import com.khanakirana.admin.khanakirana.utils.EndpointManager;
import com.khanakirana.backend.sysadminApi.SysadminApi;
import com.khanakirana.common.ServerInteractionReturnStatus;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class AddItemCategoryTask extends AbstractItemCategoryTask {

    private Long parentId;
    private String name;
    private String description;
    private Logger logger = Logger.getLogger(AddItemCategoryTask.class.getName());


    public AddItemCategoryTask(KKManageItemCategoryActivity context,
                               Long parentId,
                               String name,
                               String description) {
        super(context);
        this.parentId = parentId;
        this.name = name;
        this.description = description;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            if (parentId == null || parentId.equals(0L)) {

                this.itemCategoryList = EndpointManager.getEndpoints(context).createItemCategory(name, description).execute().getItems();
            }
            else {

                this.itemCategoryList = EndpointManager.getEndpoints(context).createChildItemCategory(parentId, name, description).execute().getItems();
            }
            return ServerInteractionReturnStatus.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
}
