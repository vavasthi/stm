package com.avasthi.roadcompanion.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.adapters.FacebookGroupListAdapter;
import com.avasthi.roadcompanion.adapters.RCGroupListAdapter;
import com.avasthi.roadcompanion.background.tasks.GetFacebookGroupsTask;
import com.avasthi.roadcompanion.background.tasks.RCGroupTask;
import com.avasthi.roadcompanion.data.GroupHeader;
import com.avasthi.roadcompanion.utils.FacebookReadInterface;

import org.json.JSONException;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by vavasthi on 26/11/15.
 */
public class RCGroupActivity extends RCAbstractActivity {

    private static GroupHeader selectedGroupHeader;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        recyclerView = (RecyclerView)findViewById(R.id.group_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        showProgressDialog(this);
        ListView lv = (ListView) findViewById(R.id.fb_group_list);
        new GetFacebookGroupsTask(this).execute();
    }
    public void populateListView() {


        try {
            adapter = new RCGroupListAdapter(loadGroupMenuResources(R.raw.group_menu));
            recyclerView.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        hideProgressDialog();
    }

/*    public void populateListView() {

        ListView lv = (ListView) findViewById(R.id.fb_group_list);
        if (FacebookReadInterface.isInitialied()) {
            lv.setAdapter(new FacebookGroupListAdapter(this, Arrays.asList(FacebookReadInterface.getInstance().getFacebookGroupHeaders())));
            ((BaseAdapter) lv.getAdapter()).notifyDataSetChanged();
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    setSelectedGroupHeader((GroupHeader) parent.getAdapter().getItem(position));
                }
            });
        } else {
            lv.setEnabled(false);
        }
        hideProgressDialog();
    }*/
    public void setSelectedGroupHeader(GroupHeader selectedGroupHeader) {
        RCGroupActivity.selectedGroupHeader = selectedGroupHeader;
    }

    public void createGroup(View v) throws NoSuchAlgorithmException {
        if (selectedGroupHeader != null) {

            showProgressDialog(this);
            new RCGroupTask(this, selectedGroupHeader).execute();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.facebook_group_not_selected)
                    .setMessage(R.string.facebook_group_not_selected_description)
                    .setCancelable(false)
                    .create()
                    .show();
        }
    }

    public void splashListGroupsScreen() {

        setContentView(R.layout.list_groups);
    }
}