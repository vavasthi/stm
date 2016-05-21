package com.avasthi.roadcompanion.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Amenity;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.PointsOfInterest;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Toll;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.adapters.RCAmenityListAdapter;
import com.avasthi.roadcompanion.background.tasks.RCAddAmenityStopTask;
import com.avasthi.roadcompanion.utils.RCLocationManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vavasthi on 26/11/15.
 */
public class RCAmenityActivity extends RCAbstractActivity {

    private RecyclerView recyclerView;
    private RCAmenityListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Dialog popup;
    private PointsOfInterest pointsOfInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.dummy_layout_popup);
        pointsOfInterest = RCLocationManager.getInstance().getPointsOfInterest();
        createAndShowPopup();
    }

    public void populateListView() {

        List<Amenity> amenityList = new ArrayList<>();
        if (pointsOfInterest != null &&
                pointsOfInterest.getAmenityList() != null &&
                pointsOfInterest.getAmenityList().size() > 0) {

            amenityList = pointsOfInterest.getAmenityList();
        }
        adapter = new RCAmenityListAdapter(amenityList, this);
        recyclerView.setAdapter(adapter);
        hideProgressDialog();
    }
/*    recyclerView = (RecyclerView) findViewById(R.id.list_restaurants_view);
    layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
*/
    void createAndShowPopup() {

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) this.findViewById(R.id.main_layout);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.rc_amenity_popup, viewGroup);
        // Creating the PopupWindow
        popup = new Dialog(this);
        popup.setTitle(R.string.rc_amenity_details);
        popup.setContentView(layout);
        recyclerView = (RecyclerView) popup.findViewById(R.id.list_restaurants_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        populateListView();
        popup.show();
    }

    public void enterAmenityDetail(View v) {

        Amenity amenity = null;
        showProgressDialog(this);
        if (adapter != null && adapter.getPosition() != -1) {
            amenity = adapter.getSelectedAmenity();
            new RCAddAmenityStopTask(this, amenity, popup.findViewById(R.id.amenity_view), true).execute();
        }
        else {

            new RCAddAmenityStopTask(this, amenity, popup.findViewById(R.id.amenity_view), false).execute();
        }
    }

    public void updateActivityUI() {
        if (adapter.getPosition() != -1) {
            Amenity amenity = adapter.getSelectedAmenity();
            ((TextView)findViewById(R.id.amenity_name)).setText(amenity.getName());
            ((CheckBox) findViewById(R.id.has_restaurant)).setChecked(amenity.getHasRestaurant());
            ((CheckBox) findViewById(R.id.has_restaurant_cc)).setChecked(amenity.getRestaurantCreditCardAccepted());
            ((CheckBox) findViewById(R.id.has_fuel)).setChecked(amenity.getHasPetrolStation());
            ((CheckBox) findViewById(R.id.has_fuel_cc)).setChecked(amenity.getFuelCreditCardAccepted());
            ((CheckBox) findViewById(R.id.has_restroom)).setChecked(amenity.getHasRestrooms());
        }
    }
}