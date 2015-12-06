package com.avasthi.roadcompanion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.R;

import java.util.List;


/**
 * Created by vavasthi on 26/11/15.
 */
public class RCGroupListAdapter extends RecyclerView.Adapter<RCGroupListAdapter.DataObjectHolder> {

    UserGroup[] memberships;

    public RCGroupListAdapter(List<UserGroup> dataset) {
        this.memberships = dataset.toArray(new UserGroup[dataset.size()]);
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        DataObjectHolder holder = new DataObjectHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.title.setText(memberships[position].getName());
        holder.description.setText(memberships[position].getDescription());
        holder.role.setText(memberships[position].getRole());
    }

    @Override
    public int getItemCount() {
        return memberships.length;
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView description;
        TextView role;

        public DataObjectHolder(View item) {
            super(item);
            title = (TextView) item.findViewById(R.id.name);
            description = (TextView) item.findViewById(R.id.description);
            role = (TextView) item.findViewById(R.id.role);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
