package com.khanakirana.khanakirana.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.khanakirana.backend.userRegistrationApi.model.ItemCategory;
import com.khanakirana.khanakirana.KKAndroidConstants;
import com.khanakirana.khanakirana.KhanaKiranaMainActivity;
import com.khanakirana.khanakirana.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by vavasthi on 19/9/15.
 */
public class KKManageItemCategory extends Activity {

    private Logger logger = Logger.getLogger(KKManageItemCategory.class.getName());
    Map<Long, List<ItemCategory>> itemCategoryMap;

    Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemCategoryMap = new HashMap<>();
        setContentView(R.layout.kk_item_category_listview);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.kk_item_category_layout_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.add_item_category:
                startActivityForResult(new Intent(this, com.khanakirana.khanakirana.activities.KKAddMeasurementUnitActivity.class), KKAndroidConstants.ADD_MEASUREMENT_UNIT_REQUEST);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addMeasurementCategory(View v) {

    }

    public void setItemCategories(List<ItemCategory> itemCategories) {
        for (ItemCategory ic : itemCategories) {
            List<ItemCategory> icList = itemCategoryMap.get(ic.getParentId());
            if (icList == null) {
                icList = new ArrayList<>();
                itemCategoryMap.put(ic.getParentId(), icList);
            }
            icList.add(ic);
        }
    }
}