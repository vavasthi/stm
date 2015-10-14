package com.khanakirana.admin.khanakirana.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;


import com.khanakirana.admin.khanakirana.R;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by vavasthi on 19/9/15.
 */
public class KKAddMeasurementCategoryActivity extends KhanaKiranaAdminAbstractActivity {

    private Logger logger = Logger.getLogger(KKAddMeasurementCategoryActivity.class.getName());

    Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPopup();
//        setContentView(R.layout.adding_items_master_list);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
    // The method that displays the dialog.
    void showPopup() {
        int popupWidth = 400;
        int popupHeight = 350;

        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) this.findViewById(R.id.adding_measurement_category);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.adding_measurement_category_layout, viewGroup);

        // Creating the PopupWindow
        dialog = new Dialog(this);
        dialog.setTitle(R.string.measurement_category_dialog_title);
        dialog.setContentView(layout);

        dialog.setContentView(layout);
                dialog.show();

        // Getting a reference to Close button, and close the dialog when clicked.
/*        ((Button) layout.findViewById(R.id.register)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Getting a reference to Close button, and close the dialog when clicked.
        ((Button) layout.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
    }
    public void addMeasurementCategory(View v) {

        RelativeLayout viewGroup = (RelativeLayout) dialog.findViewById(R.id.adding_measurement_category);
        String measurementCategory = ((EditText)(dialog.findViewById(R.id.measurement_category))).getText().toString();
        Boolean fractional = ((CheckBox)(dialog.findViewById(R.id.fractional))).isChecked();
        logger.log(Level.INFO, "Adding measurement category for " + measurementCategory);
        dialog.dismiss();
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        new com.khanakirana.admin.khanakirana.background.tasks.AddMeasurementCategoryTask(this, measurementCategory, fractional).execute();
        progressDialog.dismiss();
        finish();
    }

}