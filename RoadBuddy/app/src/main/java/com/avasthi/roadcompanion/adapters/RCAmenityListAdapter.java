package com.avasthi.roadcompanion.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Amenity;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Toll;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RCAmenityActivity;

import java.util.List;


/**
 * Created by vavasthi on 26/11/15.
 */
public class RCAmenityListAdapter extends RecyclerView.Adapter<RCAmenityListAdapter.DataObjectHolder> {

    RCAmenityActivity activity;
    private final Amenity[] amenities;
    private int position = -1;

    public RCAmenityListAdapter(List<Amenity> dataset, RCAmenityActivity activity) {
        this.amenities = dataset.toArray(new Amenity[dataset.size()]);
        this.activity = activity;
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.amenity_item, parent, false);
        DataObjectHolder holder = new DataObjectHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.name.setText(amenities[position].getName());
        holder.food.setChecked(amenities[position].getHasRestaurant());
        holder.restroom.setChecked(amenities[position].getHasRestrooms());
        holder.fuel.setChecked(amenities[position].getHasPetrolStation());
        this.position = position;
//        activity.updateActivityUI();
    }

    @Override
    public int getItemCount() {
        return amenities.length;
    }

    public int getPosition() {
        return position;
    }

    public Amenity getSelectedAmenity() {
        return amenities[position];
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView name;
        private final CheckBox food;
        private final CheckBox fuel;
        private final CheckBox restroom;
        private int position;

        public DataObjectHolder(View item) {
            super(item);
            name = (TextView) item.findViewById(R.id.name);
            food = (CheckBox) item.findViewById(R.id.has_restaurant);
            restroom  = (CheckBox) item.findViewById(R.id.has_restroom);
            fuel = (CheckBox) item.findViewById(R.id.has_fuel);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            position = getAdapterPosition();
        }

    }

}
