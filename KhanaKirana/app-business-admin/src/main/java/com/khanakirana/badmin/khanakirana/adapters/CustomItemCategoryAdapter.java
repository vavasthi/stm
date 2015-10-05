package com.khanakirana.badmin.khanakirana.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khanakirana.backend.businessApi.model.ItemCategory;
import com.khanakirana.badmin.khanakirana.R;

import java.util.List;

/**
 * Created by vavasthi on 27/9/15.
 */
public class CustomItemCategoryAdapter extends ArrayAdapter<ItemCategory>  {
    private ViewHolderItem viewHolderItem;

    static class ViewHolderItem {

        TextView title;
        TextView description;
        int position;
    }

    public CustomItemCategoryAdapter(Context context, List<ItemCategory> itemCategoryList) {
        super(context, 0, itemCategoryList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.kk_item_category_listview_item, parent, false);
            viewHolderItem = new ViewHolderItem();
            viewHolderItem.title = (TextView)convertView.findViewById(R.id.item_categ_title);
            viewHolderItem.description = (TextView)convertView.findViewById(R.id.item_categ_description);
            viewHolderItem.position = position;
            convertView.setTag(viewHolderItem);
        }
        else {
            viewHolderItem = (ViewHolderItem)convertView.getTag();
        }
        ItemCategory ic = getItem(position);
        if (ic != null) {
            viewHolderItem.title.setText(ic.getName());
            viewHolderItem.description.setText(ic.getDescription());
        }
        return convertView;
    }

}
