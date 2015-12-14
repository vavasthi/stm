package com.avasthi.roadcompanion.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.PointsOfInterest;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.adapters.RCGroupListAdapter;
import com.avasthi.roadcompanion.adapters.RCTollListAdapter;
import com.avasthi.roadcompanion.background.tasks.RCAddGroupTask;
import com.avasthi.roadcompanion.background.tasks.RCListGroupTask;
import com.avasthi.roadcompanion.background.tasks.RCListNearbyTollsTask;
import com.avasthi.roadcompanion.utils.RCLocationManager;

import java.util.List;

/**
 * Created by vavasthi on 26/11/15.
 */
public class RCTollActivity extends RCAbstractActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<UserGroup> listOfGroups;
    private Dialog popup;
    private PointsOfInterest pointsOfInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rc_toll_payment_popup);
        recyclerView = (RecyclerView) findViewById(R.id.list_tolls_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        pointsOfInterest = RCLocationManager.getInstance().getPointsOfInterest();
    }

    public void populateListView() {

        if (pointsOfInterest != null &&
                pointsOfInterest.getTollList() != null &&
                pointsOfInterest.getTollList().size() > 0) {

            adapter = new RCTollListAdapter(pointsOfInterest.getTollList());
            recyclerView.setAdapter(adapter);
        }
        hideProgressDialog();
    }


    public void enterPaidToll(View v) {
        if (popup != null && popup.isShowing()) {

            String name = ((EditText) popup.findViewById(R.id.groupname)).getText().toString();
            String description = ((EditText) popup.findViewById(R.id.groupdescription)).getText().toString();
            popup.dismiss();
            showProgressDialog(this);
            //new RCAddGroupTask(this, name, description).execute();
        }

    }

//    public void splashListGroupsScreen(List<UserGroup> groupsList) {

  //      listOfGroups = groupsList;
    //    populateListView();
    //}
}