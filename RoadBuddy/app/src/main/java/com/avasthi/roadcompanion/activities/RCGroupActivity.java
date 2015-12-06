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
import android.widget.ListView;
import android.widget.ProgressBar;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.adapters.RCGroupListAdapter;
import com.avasthi.roadcompanion.background.tasks.RCAddGroupTask;
import com.avasthi.roadcompanion.background.tasks.RCListGroupTask;

import java.util.List;

/**
 * Created by vavasthi on 26/11/15.
 */
public class RCGroupActivity extends RCAbstractActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<UserGroup> listOfGroups;
    private Dialog popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        recyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        showProgressDialog(this);
        new RCListGroupTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.group_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.add_group:
                createAddGroupPopup();
//                createAndShowPopup(R.id.create_group_view, R.layout.add_group, R.string.adding_group);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void createAddGroupPopup() {

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) this.findViewById(R.id.create_group_view);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.add_group, viewGroup);
        // Creating the PopupWindow
        popup = new Dialog(this);
        popup.setContentView(layout);
        popup.setTitle(R.string.adding_group);
        popup.show();
    }

    public void populateListView() {


        adapter = new RCGroupListAdapter(listOfGroups);
        recyclerView.setAdapter(adapter);
        hideProgressDialog();
    }


    public void createGroup(View v) {
        if (popup != null && popup.isShowing()) {

            String name = ((EditText) popup.findViewById(R.id.groupname)).getText().toString();
            String description = ((EditText) popup.findViewById(R.id.groupdescription)).getText().toString();
            showProgressDialog(this);
            new RCAddGroupTask(this, name, description).execute();
        }

    }

    public void splashListGroupsScreen(List<UserGroup> groupsList) {

        listOfGroups = groupsList;
        populateListView();
    }
}