package com.avasthi.roadcompanion.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Toll;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.UserGroup;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RCTollActivity;

import java.util.List;


/**
 * Created by vavasthi on 26/11/15.
 */
public class RCTollListAdapter extends RecyclerView.Adapter<RCTollListAdapter.DataObjectHolder> {

    RCTollActivity activity;
    private final Toll[] tolls;
    private int position = -1;

    public RCTollListAdapter(List<Toll> dataset, RCTollActivity activity) {
        this.tolls = dataset.toArray(new Toll[dataset.size()]);
        this.activity = activity;
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.toll_item, parent, false);
        DataObjectHolder holder = new DataObjectHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.city.setText(tolls[position].getCity());
        holder.state.setText(tolls[position].getState());
        holder.country.setText(tolls[position].getCountry());
        holder.amount.setText(tolls[position].getAmount().toString());
        this.position = position;
        activity.updateActivityUI();
    }

    @Override
    public int getItemCount() {
        return tolls.length;
    }

    public int getPosition() {
        return position;
    }

    public Toll getSelectedToll() {
        return tolls[position];
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView city;
        private final TextView state;
        private final TextView country;
        private final TextView amount;
        private int position;

        public DataObjectHolder(View item) {
            super(item);
            city = (TextView) item.findViewById(R.id.city);
            state = (TextView) item.findViewById(R.id.state);
            country  = (TextView) item.findViewById(R.id.country);
            amount = (TextView) item.findViewById(R.id.amount);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
        }

    }

}
