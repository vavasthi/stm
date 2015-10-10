package com.khanakirana.admin.khanakirana.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.khanakirana.admin.khanakirana.background.tasks.ListMeasurementCategoryTask;
import com.khanakirana.backend.sysadminApi.model.MeasurementCategory;

import java.util.List;

/**
 * Created by vavasthi on 24/9/15.
 */
public abstract class KKMeasurementCategoryReceivingActivity  extends KhanaKiranaAdminAbstractActivity {
    protected Dialog dialog;
    protected ProgressDialog progressDialog;
    List<MeasurementCategory> mcList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPopup();
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        new ListMeasurementCategoryTask(this, KhanaKiranaMainAdminActivity.getEndpoints()).execute();

//        setContentView(R.layout.adding_items_master_list);
    }
    abstract void createPopup ();
    abstract void showPopup();

    public void setCategories(List<MeasurementCategory> categories) {
        this.mcList = categories;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                showPopup();
            }
        });
    }

}
