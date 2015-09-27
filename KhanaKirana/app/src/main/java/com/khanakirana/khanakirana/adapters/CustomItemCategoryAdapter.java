package com.khanakirana.khanakirana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khanakirana.backend.userRegistrationApi.model.ItemCategory;
import com.khanakirana.khanakirana.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vavasthi on 27/9/15.
 */
public class CustomItemCategoryAdapter extends ArrayAdapter<ItemCategory>  {

    public CustomItemCategoryAdapter(Context context, List<ItemCategory> itemCategoryList) {
        super(context, 0, itemCategoryList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        super.getView(position, convertView, parent);
        LayoutInflater inflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflator.inflate(R.layout.kk_item_category_listview_item, null);
        ItemCategory ic = getItem(position);
        TextView title = (TextView) convertView.findViewById(R.id.item_categ_title);
        TextView description = (TextView) convertView.findViewById(R.id.item_categ_description);
        title.setText(ic.getName());
        description.setText(ic.getDescription());
        return convertView;
    }

}
