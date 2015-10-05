package com.khanakirana.badmin.khanakirana.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.khanakirana.backend.businessApi.model.ItemCategory;
import com.khanakirana.badmin.khanakirana.KhanaKiranaMainActivity;
import com.khanakirana.badmin.khanakirana.R;
import com.khanakirana.badmin.khanakirana.adapters.CustomItemCategoryAdapter;
import com.khanakirana.badmin.khanakirana.background.tasks.GetItemCategoryTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Created by vavasthi on 19/9/15.
 */
public class KKManageItemCategoryActivity extends Activity {

    private Logger logger = Logger.getLogger(KKManageItemCategoryActivity.class.getName());
    Map<Long, List<ItemCategory> > itemCategoryMap;
    private long currentParent = 0;
    private String currentParentName;
    ProgressDialog progressDialog;

    Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemCategoryMap = new HashMap<>();
        setContentView(R.layout.kk_item_category_listview);
        final ListView lv = (ListView)findViewById(R.id.kk_item_category_listview);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ItemCategory ic = (ItemCategory)parent.getAdapter().getItem(position);
                currentParent = ic.getId();
                currentParentName = ic.getName();
                repopulateListView();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        new GetItemCategoryTask(this, KhanaKiranaMainActivity.getEndpoints()).execute();

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
                createAndShowPopup();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setItemCategories(List<ItemCategory> itemCategories) {

        itemCategoryMap.clear();
        if (itemCategories != null && itemCategories.size() > 0) {

            for (ItemCategory ic : itemCategories) {
                if (ic.getId().equals(currentParent)) {
                    currentParentName = ic.getName();
                }
                List<ItemCategory> icList = itemCategoryMap.get(ic.getParentId());
                if (icList == null) {
                    icList = new ArrayList<>();
                    itemCategoryMap.put(ic.getParentId(), icList);
                }
                icList.add(ic);
            }
            repopulateListView();
        }
        progressDialog.dismiss();
    }
    private void repopulateListView() {

        List<ItemCategory> list = itemCategoryMap.get(currentParent);
        if (list == null) {
            list = new ArrayList<>();
        }
        CustomItemCategoryAdapter cica = new CustomItemCategoryAdapter(this, list);
        ListView listView = (ListView)findViewById(R.id.kk_item_category_listview);
        listView.setAdapter(cica);
        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
    }
    void createAndShowPopup() {

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) this.findViewById(R.id.add_item_category);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.adding_items_category, viewGroup);
        // Creating the PopupWindow
        dialog = new Dialog(this);
        dialog.setTitle(R.string.kk_add_item_category);
        dialog.setContentView(layout);
        if (currentParent == 0) {

            dialog.setTitle(R.string.kk_root);
        }
        else {
            dialog.setTitle(currentParentName);
        }
        dialog.show();
    }

}