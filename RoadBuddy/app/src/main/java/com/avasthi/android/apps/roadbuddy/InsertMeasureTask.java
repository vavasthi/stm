package com.avasthi.android.apps.roadbuddy;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.appspot.myapplicationid.roadMeasurementBeanApi.RoadMeasurementBeanApi;
import com.appspot.myapplicationid.roadMeasurementBeanApi.model.RoadMeasurementBean;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;

/**
 * Created by vavasthi on 29/3/15.
 */
public class InsertMeasureTask extends AsyncTask<RoadMeasurementBean, Void, RoadMeasurementBean> {
    RoadConditionFullscreenActivity activity;
//        private ProgressDialog pd;

    public InsertMeasureTask(RoadConditionFullscreenActivity activity) {
        this.activity = activity;
    }


    protected void onPreExecute(){
/*            super.onPreExecute();
            String accountName = settings.getString("ACCOUNT_NAME", null);
            pd = new ProgressDialog(activity);
            pd.setMessage("[" + accountName + "]" + " Adding the Quote...");
            pd.show();*/
    }

    protected RoadMeasurementBean doInBackground(RoadMeasurementBean... params) {
        RoadMeasurementBean bean = null;
        try {
            RoadMeasurementBeanApi.Builder builder = new RoadMeasurementBeanApi.Builder(
                    AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
                    activity.getCredential());

            RoadMeasurementBeanApi api = builder.build();
            bean = api.insertRoadMeasurementBean(params[0]).execute();
            Log.d("Response from call", "no error");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("NETWORK_CALL_FAILED", "Could not make a call to cloud storage.");
        } catch (Exception e) {
            Log.d("Could not Add Quote", e.getMessage(), e);
        }
        return bean;
    }

    protected void onPostExecute() {
        //Clear the progress dialog and the fields
/*            pd.dismiss();
            editMessage.setText("");
            editAuthorName.setText("");

            //Display success message to user
            Toast.makeText(getBaseContext(), "Quote added succesfully", Toast.LENGTH_SHORT).show();*/
    }
}