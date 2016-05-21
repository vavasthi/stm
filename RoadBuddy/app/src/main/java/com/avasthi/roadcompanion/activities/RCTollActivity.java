package com.avasthi.roadcompanion.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Toll;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.adapters.RCTollListAdapter;
import com.avasthi.roadcompanion.background.tasks.RCAddTollStopTask;
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
        createAndShowPopup();
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

    void createAndShowPopup() {

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) this.findViewById(R.id.main_layout);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.rc_toll_payment_popup, viewGroup);
        // Creating the PopupWindow
        popup = new Dialog(this);
        popup.setTitle(R.string.rc_enter_toll);
        popup.setContentView(layout);
        recyclerView = (RecyclerView) popup.findViewById(R.id.list_tolls_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        populateListView();
        popup.show();
    }

    public void enterPaidToll(View v) {

        float amount = Float.parseFloat(((EditText)popup.findViewById(R.id.actual_amount)).getText().toString());
        boolean fasTagLane = ((CheckBox)popup.findViewById(R.id.fasTagLane)).isChecked();
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