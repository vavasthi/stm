package com.avasthi.roadcompanion.background.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Amenity;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.PointsOfInterest;
import com.avasthi.android.apps.roadbuddy.backend.roadMeasurementApi.model.Toll;
import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.activities.RCAmenityActivity;
import com.avasthi.roadcompanion.activities.RCTollActivity;
import com.avasthi.roadcompanion.utils.EndpointManager;
import com.avasthi.roadcompanion.utils.RCLocationManager;
import com.google.api.client.util.DateTime;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vavasthi on 14/9/15.
 */
public class RCAddAmenityStopTask extends AsyncTask<Void, Void, PointsOfInterest> {

    private final RCAmenityActivity context;

    private final Amenity amenity;
    private final String name;
    private final Double latitude;
    private final Double longitude;
    private final Boolean hasRestaurant;
    private final Boolean restaurantCreditCardAccepted;
    private final Float foodAmount;
    private final Integer restaurantRating;
    private final Boolean hasRestrooms;
    private final Integer restroomRating;
    private final Boolean hasPetrolStation;
    private final Boolean fuelCreditCardAccepted;
    private final Integer petrolStationRating;
    private final Float fuelAmount;
    private final Float fuelQuantity;
    private final String city;
    private final String state;
    private final String country;
    private final Boolean onlyAmenityStop;

    private Logger logger = Logger.getLogger(RCAddAmenityStopTask.class.getName());

    public RCAddAmenityStopTask(RCAmenityActivity context, Amenity amenity, View view, Boolean onlyAmenityStop) {
        this.context = context;
        this.amenity = amenity;
        this.onlyAmenityStop = onlyAmenityStop;
        this.name = ((EditText)view.findViewById(R.id.amenity_name)).getText().toString();
        this.latitude = RCLocationManager.getInstance().getLastLocation().getLatitude();
        this.longitude = RCLocationManager.getInstance().getLastLocation().getLongitude();
        this.hasRestaurant = ((CheckBox) view.findViewById(R.id.has_restaurant)).isChecked();
        this.restaurantCreditCardAccepted = ((CheckBox) view.findViewById(R.id.has_restaurant_cc)).isChecked();
        this.foodAmount = getFloatValueFromEditText(R.id.food_amount, view);
        this.restaurantRating = new Float(((RatingBar)view.findViewById(R.id.restaurant_rating)).getRating()).intValue();
        this.hasRestrooms = ((CheckBox) view.findViewById(R.id.has_restroom)).isChecked();
        this.restroomRating = new Float(((RatingBar)view.findViewById(R.id.restroom_rating)).getRating()).intValue();
        this.hasPetrolStation = ((CheckBox) view.findViewById(R.id.has_fuel)).isChecked();
        this.fuelCreditCardAccepted = ((CheckBox) view.findViewById(R.id.has_fuel_cc)).isChecked();
        this.petrolStationRating = new Float(((RatingBar)view.findViewById(R.id.fuel_rating)).getRating()).intValue();
        this.fuelAmount = getFloatValueFromEditText(R.id.fuel_amount, view);
        this.fuelQuantity = getFloatValueFromEditText(R.id.fuel_quantity, view);
        this.city = RCLocationManager.getInstance().getLastAddress().getLocality();
        this.state = RCLocationManager.getInstance().getLastAddress().getAdminArea();
        this.country = RCLocationManager.getInstance().getLastAddress().getCountryCode();
    }

    private float  getFloatValueFromEditText(int id, View view) {
        EditText et = (EditText) view.findViewById(id);
        String str = et.getText().toString();
        if (!str.trim().isEmpty()) {

            return Float.parseFloat(str);
        }
        return 0.0F;
    }
    @Override
    protected PointsOfInterest doInBackground(Void... params) {

        PointsOfInterest pointsOfInterest = null;
        try {
            if (onlyAmenityStop) {

                pointsOfInterest = EndpointManager.getRoadMeasurementEndpoint(context).addAmenityStop(amenity.getId(),
                        new DateTime(new Date()),
                        restaurantRating,
                        foodAmount,
                        restroomRating,
                        petrolStationRating,
                        fuelAmount,
                        fuelQuantity).execute();
            }
            else {

                pointsOfInterest = EndpointManager.getRoadMeasurementEndpoint(context).addAmenity(new DateTime(new Date()),
                        name,
                        latitude,
                        longitude,
                        hasRestaurant,
                        restaurantCreditCardAccepted,
                        foodAmount,
                        restaurantRating,
                        hasRestrooms,
                        restroomRating,
                        hasPetrolStation,
                        fuelCreditCardAccepted,
                        petrolStationRating,
                        fuelAmount,
                        fuelQuantity,
                        city,
                        state,
                        country).execute();
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Remote call register failed", e);
        }
        return pointsOfInterest;
    }
    protected void onPostExecute (PointsOfInterest pointsOfInterest) {

        if (pointsOfInterest != null) {

            RCLocationManager.getInstance().setPointsOfInterest(pointsOfInterest);
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                context.hideProgressDialog();
                context.finish();
            }
        });
    }
}
