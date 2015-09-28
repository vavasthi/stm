package com.khanakirana.khanakirana.background.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import com.khanakirana.backend.userRegistrationApi.UserRegistrationApi;
import com.khanakirana.backend.userRegistrationApi.model.ItemCategory;
import com.khanakirana.khanakirana.R;
import com.khanakirana.khanakirana.activities.KKManageItemCategoryActivity;

import java.io.IOException;
import java.util.List;
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
                               UserRegistrationApi registrationApiService,
                               Long parentId,
                               String name,
                               String description) {
        super(context, registrationApiService);
        this.parentId = parentId;
        this.name = name;
        this.description = description;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            if (parentId == null || parentId.equals(0L)) {

                this.itemCategoryList = registrationApiService.createItemCategory(name, description).execute().getItems();
            }
            else {

                this.itemCategoryList = registrationApiService.createChildItemCategory(parentId, name, description).execute().getItems();
            }
            return ServerInteractionReturnStatus.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerInteractionReturnStatus.FATAL_ERROR;
    }
}
