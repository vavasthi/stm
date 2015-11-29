package com.avasthi.roadcompanion.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.adapters.FacebookGroupListAdapter;
import com.avasthi.roadcompanion.data.GroupHeader;
import com.avasthi.roadcompanion.utils.FacebookReadInterface;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by vavasthi on 26/11/15.
 */
public class RCUnusedFacebookGroupActivity extends RCAbstractActivity {
    private static GroupHeader selectedGroupHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_groups);
        showProgressDialog(this);
        ListView lv = (ListView) findViewById(R.id.fb_group_list);
        populateListView();
        //new GetFacebookGroupsTask(this).execute();
    }

    public void populateListView() {

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
    }
    public void setSelectedGroupHeader(GroupHeader selectedGroupHeader) {
        RCUnusedFacebookGroupActivity.selectedGroupHeader = selectedGroupHeader;
    }

    public void createGroup(View v) throws NoSuchAlgorithmException {
        if (selectedGroupHeader != null) {

            showProgressDialog(this);
            //new RCGroupTask(this, selectedGroupHeader).execute();
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