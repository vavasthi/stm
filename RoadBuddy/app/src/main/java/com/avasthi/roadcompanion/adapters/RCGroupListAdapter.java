package com.avasthi.roadcompanion.adapters;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avasthi.roadcompanion.Constants;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RCGroupActivity;
import com.avasthi.roadcompanion.data.GroupHeader;
import com.avasthi.roadcompanion.data.GroupMenuItem;

import java.util.List;


/**
 * Created by vavasthi on 26/11/15.
 */
public class RCGroupListAdapter extends RecyclerView.Adapter<RCGroupListAdapter.DataObjectHolder> {

    GroupMenuItem[] dataset;

    public RCGroupListAdapter(List<GroupMenuItem> dataset) {
        this.dataset = dataset.toArray(new GroupMenuItem[dataset.size()]);
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        DataObjectHolder holder = new DataObjectHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.title.setText(dataset[position].getTitle());
        holder.description.setText(dataset[position].getDescription());
        holder.activity.setText(dataset[position].getActivity());
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView activity;

        public DataObjectHolder(View item) {
            super(item);
            title = (TextView)item.findViewById(R.id.title);
            description = (TextView)item.findViewById(R.id.description);
            activity = (TextView)item.findViewById(R.id.activity);
            item.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {

            String activity = ((TextView)v.findViewById(R.id.activity)).getText().toString();
            try {
                Class<?>  c = Class.forName(activity);
                Context context = v.getContext();
                context.startActivity(new Intent(context, c));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
