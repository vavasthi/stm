package com.khanakirana.khanakirana.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.khanakirana.khanakirana.KhanaKiranaMainActivity;
import com.khanakirana.khanakirana.R;
import com.khanakirana.khanakirana.background.tasks.AddMeasurementCategoryTask;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by vavasthi on 19/9/15.
 */
public class KKAddMeasurementCategoryActivity extends Activity {

    private Logger logger = Logger.getLogger(KKAddMeasurementCategoryActivity.class.getName());

    PopupWindow popup;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showPopup();
//        setContentView(R.layout.adding_items_master_list);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
    // The method that displays the popup.
    void showPopup() {
        int popupWidth = 400;
        int popupHeight = 350;

        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) this.findViewById(R.id.adding_measurement_category);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.adding_measurement_category_layout, viewGroup);

        // Creating the PopupWindow
        popup = new PopupWindow(this);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);


        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        popup.setContentView(layout);
        // Displaying the popup at the specified location, + offsets.
        layout.post(new Runnable() {
            @Override
            public void run() {

                popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });

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
    public void addMeasurementCategory(View v) {

        RelativeLayout viewGroup = (RelativeLayout) popup.getContentView().findViewById(R.id.adding_measurement_category);
        String measurementCategory = ((EditText)(popup.getContentView().findViewById(R.id.measurement_category))).getText().toString();
        Boolean fractional = ((CheckBox)(popup.getContentView().findViewById(R.id.fractional))).isChecked();
        logger.log(Level.INFO, "Adding measurement category for " + measurementCategory);
        new com.khanakirana.khanakirana.background.tasks.AddMeasurementCategoryTask(this, KhanaKiranaMainActivity.getEndpoints(), measurementCategory, fractional).execute();

    }
    public void dismiss() {
        popup.dismiss();
    }
}