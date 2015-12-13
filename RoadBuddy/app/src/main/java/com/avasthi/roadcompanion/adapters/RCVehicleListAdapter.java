package com.avasthi.roadcompanion.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Vehicle;
import com.avasthi.roadcompanion.R;

import java.util.List;


/**
 * Created by vavasthi on 26/11/15.
 */
public class RCVehicleListAdapter extends ArrayAdapter<Vehicle> {

    Vehicle[] vehicles;

    private ViewHolderItem viewHolderItem;
    static class ViewHolderItem {

        TextView vehicleBrand;
        TextView vehicleRegistration;
        int position;
    }

    public RCVehicleListAdapter(Context context, List<Vehicle> dataset) {
        super(context, 0, dataset);
        this.vehicles = dataset.toArray(new Vehicle[dataset.size()]);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflator = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.vehicle_item, parent, false);
            viewHolderItem = new ViewHolderItem();
            viewHolderItem.vehicleBrand = (TextView)convertView.findViewById(R.id.vehicle_brand);
            viewHolderItem.vehicleRegistration= (TextView)convertView.findViewById(R.id.vehicle_registration);
            viewHolderItem.position = position;
            convertView.setTag(viewHolderItem);
        }
        else {
            viewHolderItem = (ViewHolderItem)convertView.getTag();
        }
        Vehicle v = getItem(position);
        if (v != null) {
            viewHolderItem.vehicleBrand.setText(v.getBrand());
            viewHolderItem.vehicleRegistration.setText(v.getRegistration());
        }
        return convertView;
    }

}
