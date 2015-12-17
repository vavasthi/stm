package com.avasthi.roadcompanion.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.PointsOfInterest;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Toll;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.adapters.RCGroupListAdapter;
import com.avasthi.roadcompanion.adapters.RCTollListAdapter;
import com.avasthi.roadcompanion.background.tasks.RCAddGroupTask;
import com.avasthi.roadcompanion.background.tasks.RCAddTollStopTask;
import com.avasthi.roadcompanion.background.tasks.RCListGroupTask;
import com.avasthi.roadcompanion.background.tasks.RCListNearbyTollsTask;
import com.avasthi.roadcompanion.utils.RCLocationManager;

import java.util.List;

/**
 * Created by vavasthi on 26/11/15.
 */
public class RCTollActivity extends RCAbstractActivity {

    private RecyclerView recyclerView;
    private RCTollListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<UserGroup> listOfGroups;
    private Dialog popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_toll_payment_popup);
        recyclerView = (RecyclerView) findViewById(R.id.list_tolls_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        populateListView();
    }

    public void populateListView() {

        if (RCLocationManager.getInstance() != null &&
                RCLocationManager.getInstance().getPointsOfInterest() != null &&
                RCLocationManager.getInstance().getPointsOfInterest().getTollList() != null &&
                RCLocationManager.getInstance().getPointsOfInterest().getTollList().size() > 0) {

            adapter = new RCTollListAdapter(RCLocationManager.getInstance().getPointsOfInterest().getTollList(), this);
            recyclerView.setAdapter(adapter);
        }
        hideProgressDialog();
    }


    public void enterPaidToll(View v) {

        float amount = Float.parseFloat(((EditText)findViewById(R.id.actual_amount)).getText().toString());
        boolean fasTagLane = ((CheckBox)findViewById(R.id.fasTagLane)).isChecked();
        if (adapter != null && adapter.getPosition() != -1) {
            Toll toll = adapter.getSelectedToll();
            new RCAddTollStopTask(this, toll, fasTagLane, amount).execute();
        }
        else {

            new RCAddTollStopTask(this, fasTagLane, amount).execute();
        }
    }

    public void updateActivityUI() {
        if (adapter.getPosition() != -1) {
            Toll toll = adapter.getSelectedToll();
            CheckBox fasTagLane = (CheckBox) findViewById(R.id.fasTagLane);
            fasTagLane.setChecked(toll.getFasTagLane());
        }
    }
}