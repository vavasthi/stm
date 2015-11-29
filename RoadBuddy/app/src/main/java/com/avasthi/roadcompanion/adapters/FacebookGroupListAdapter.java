package com.avasthi.roadcompanion.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.data.GroupHeader;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Collection;
import java.util.List;


/**
 * Created by vavasthi on 26/11/15.
 */
public class FacebookGroupListAdapter extends ArrayAdapter<GroupHeader> {
    private ViewHolderItem viewHolderItem;

    static class ViewHolderItem {

        TextView name;
        TextView id;
        TextView privacy;
        ImageView icon;
        int position;
    }

    public FacebookGroupListAdapter(Context context, List<GroupHeader> itemCategoryList) {
        super(context, 0, itemCategoryList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.facebook_group_list_item_layout, parent, false);
            viewHolderItem = new ViewHolderItem();
            viewHolderItem.name = (TextView)convertView.findViewById(R.id.fb_group_name);
            viewHolderItem.id = (TextView)convertView.findViewById(R.id.fb_group_id);
            viewHolderItem.privacy = (TextView)convertView.findViewById(R.id.fb_group_privacy);
            viewHolderItem.icon = (ImageView)convertView.findViewById(R.id.fb_group_icon);
            viewHolderItem.position = position;
            convertView.setTag(viewHolderItem);
        }
        else {
            viewHolderItem = (ViewHolderItem)convertView.getTag();
        }
        GroupHeader gh = getItem(position);
        if (gh != null) {
            viewHolderItem.name.setText(gh.getName());
            viewHolderItem.id.setText(gh.getId());
            viewHolderItem.privacy.setText(gh.getPrivacy().toString());
            viewHolderItem.icon.setImageBitmap(gh.getIconBitmap());
        }
        return convertView;
    }

}
