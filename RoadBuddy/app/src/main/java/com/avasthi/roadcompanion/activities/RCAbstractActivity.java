package com.avasthi.roadcompanion.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.avasthi.roadcompanion.R;
import com.avasthi.roadcompanion.data.GroupMenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vavasthi on 26/11/15.
 */
abstract public class RCAbstractActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    public void showProgressDialog(Activity context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }
    public void splashCreateGroupScreen() {

        hideProgressDialog();
        setContentView(R.layout.create_group);
    }
    public List<GroupMenuItem> loadGroupMenuResources(int menuResource) throws IOException, JSONException {

        InputStream jsonStream = getResources().openRawResource(menuResource);
        JSONObject jsonObject = new JSONObject(convertStreamToString(jsonStream));
        JSONArray menu = jsonObject.getJSONArray("menu");
        List<GroupMenuItem> menuItems = new ArrayList<>();
        for (int i = 0; i < menu.length(); i++) {

            JSONObject jsonItem = menu.getJSONObject(i);
            GroupMenuItem item = new GroupMenuItem(jsonItem.getString("title"), jsonItem.getString("description"), jsonItem.getString("activity"));
            menuItems.add(item);
        }
        return menuItems;
    }
    public String convertStreamToString(InputStream is) throws IOException {
        //
        // To convert the InputStream to String we use the
        // Reader.read(char[] buffer) method. We iterate until the
        // Reader return -1 which means there's no more data to
        // read. We use the StringWriter class to produce the string.
        //
        if (is != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }
}
