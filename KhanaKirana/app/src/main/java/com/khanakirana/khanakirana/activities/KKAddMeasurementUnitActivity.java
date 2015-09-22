package com.khanakirana.khanakirana.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.khanakirana.backend.userRegistrationApi.model.MeasurementCategory;
import com.khanakirana.khanakirana.KhanaKiranaMainActivity;
import com.khanakirana.khanakirana.R;
import com.khanakirana.khanakirana.background.tasks.AddMeasurementCategoryTask;
import com.khanakirana.khanakirana.background.tasks.AddMeasurementUnitTask;
import com.khanakirana.khanakirana.background.tasks.ListMeasurementCategoryTask;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by vavasthi on 19/9/15.
 */
public class KKAddMeasurementUnitActivity extends Activity {

    private Logger logger = Logger.getLogger(KKAddMeasurementUnitActivity.class.getName());
    private Spinner categories;
    private View layout;
    private List<MeasurementCategory> mcList;

    PopupWindow popup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPopup();
        new ListMeasurementCategoryTask(this, KhanaKiranaMainActivity.getEndpoints()).execute();
//        setContentView(R.layout.adding_items_master_list);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    private void createPopup () {

        int popupWidth = 400;
        int popupHeight = 450;

        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) this.findViewById(R.id.adding_measurement_unit);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflater.inflate(R.layout.adding_measurement_unit_layout, viewGroup);

        // Creating the PopupWindow
        popup = new PopupWindow(this);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        categories = (Spinner)(popup.getContentView().findViewById(R.id.measurement_category));


        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        popup.setContentView(layout);
        final CheckBox primaryUnit = (CheckBox)popup.getContentView().findViewById(R.id.primaryUnit);
        primaryUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (primaryUnit.isChecked()) {
                    ((EditText)popup.getContentView().findViewById(R.id.factor)).setText("1.0");
                }
            }
        });
        // Displaying the popup at the specified location, + offsets.
    }
    // The method that displays the popup.
    void showPopup() {

        List<String> categoryNameList = new ArrayList<>();
        for (MeasurementCategory mc : mcList) {
            categoryNameList.add(mc.getName());
        }
        categories.setAdapter(new ArrayAdapter<String>(this, R.layout.measurement_unit_item, categoryNameList ));
        popup.showAtLocation(layout, Gravity.CENTER, 0, 0);

        // Getting a reference to Close button, and close the popup when clicked.
/*        ((Button) layout.findViewById(R.id.register)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        // Getting a reference to Close button, and close the popup when clicked.
        ((Button) layout.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });*/
    }
    public void addMeasurementUnit(View v) {

        RelativeLayout viewGroup = (RelativeLayout) popup.getContentView().findViewById(R.id.adding_measurement_unit);
        String name = ((EditText)(popup.getContentView().findViewById(R.id.unit_name))).getText().toString();
        String acronym = ((EditText)(popup.getContentView().findViewById(R.id.unit_acronym))).getText().toString();
        String measurementCategory = categories.getSelectedItem().toString();
        Boolean primaryUnit = ((CheckBox)(popup.getContentView().findViewById(R.id.primaryUnit))).isChecked();
        Double factor = Double.parseDouble(((EditText) (popup.getContentView().findViewById(R.id.factor))).getText().toString());
        logger.log(Level.INFO, "Adding measurement category for " + measurementCategory);
        new AddMeasurementUnitTask(this, KhanaKiranaMainActivity.getEndpoints(), name, acronym, measurementCategory, primaryUnit, factor).execute();

    }
    public void dismiss() {
        popup.dismiss();
    }

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