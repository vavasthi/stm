package com.khanakirana.admin.khanakirana.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.khanakirana.admin.khanakirana.KhanaKiranaMainActivity;
import com.khanakirana.admin.khanakirana.R;
import com.khanakirana.admin.khanakirana.background.tasks.AddMeasurementUnitTask;
import com.khanakirana.backend.sysadminApi.model.MeasurementCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by vavasthi on 19/9/15.
 */
public class KKAddMeasurementUnitActivity extends KKMeasurementCategoryReceivingActivity {

    private Logger logger = Logger.getLogger(KKAddMeasurementUnitActivity.class.getName());
    private View layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    void createPopup () {


        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) this.findViewById(R.id.adding_measurement_unit);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflater.inflate(R.layout.adding_measurement_unit_layout, viewGroup);

        // Creating the PopupWindow
        dialog = new Dialog(this);
        dialog.setTitle(R.string.unit_dialog_title);
        dialog.setContentView(layout);



        dialog.setContentView(layout);
        final CheckBox primaryUnit = (CheckBox) dialog.findViewById(R.id.primaryUnit);
        primaryUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (primaryUnit.isChecked()) {
                    ((EditText) dialog.findViewById(R.id.factor)).setText("1.0");
                }
            }
        });
        // Displaying the dialog at the specified location, + offsets.
    }

    // The method that displays the dialog.
    void showPopup() {

        List<String> categoryNameList = new ArrayList<>();
        for (MeasurementCategory mc : mcList) {
            categoryNameList.add(mc.getName());
        }
        ArrayAdapter<String> categoryListAdapter = new ArrayAdapter<String>(this, R.layout.measurement_unit_item, categoryNameList);
        categoryListAdapter.setDropDownViewResource(R.layout.measurement_unit_item);
        Spinner categories = (Spinner)(dialog.findViewById(R.id.measurement_category));
        categories.setAdapter(categoryListAdapter);
/*        layout.post(new Runnable() {
            @Override
            public void run() {

                progressDialog.dismiss();
                dialog.show();
            }
        });*/
        progressDialog.dismiss();
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
    public void addMeasurementUnit(View v) {

        RelativeLayout viewGroup = (RelativeLayout) dialog.findViewById(R.id.adding_measurement_unit);
        String name = ((EditText)(dialog.findViewById(R.id.unit_name))).getText().toString();
        String acronym = ((EditText)(dialog.findViewById(R.id.unit_acronym))).getText().toString();
        Spinner categories = (Spinner)(dialog.findViewById(R.id.measurement_category));
        String measurementCategory = categories.getSelectedItem().toString();
        Boolean primaryUnit = ((CheckBox)(dialog.findViewById(R.id.primaryUnit))).isChecked();
        Double factor = Double.parseDouble(((EditText) (dialog.findViewById(R.id.factor))).getText().toString());
        logger.log(Level.INFO, "Adding measurement category for " + measurementCategory);
        ProgressDialog progressDialog = new ProgressDialog(this);
        dialog.dismiss();
        progressDialog.show();
        new AddMeasurementUnitTask(this, KhanaKiranaMainActivity.getEndpoints(), name, acronym, measurementCategory, primaryUnit, factor).execute();
        progressDialog.dismiss();
        finish();
    }

}