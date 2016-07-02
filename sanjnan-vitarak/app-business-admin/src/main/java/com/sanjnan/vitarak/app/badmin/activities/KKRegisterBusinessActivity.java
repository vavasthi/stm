package com.sanjnan.vitarak.app.badmin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.khanakirana.badmin.khanakirana.R;
import com.sanjnan.vitarak.app.badmin.utils.EndpointManager;


/**
 * Created by vavasthi on 19/9/15.
 */
public class KKRegisterBusinessActivity extends KhanaKiranaBusinessAbstractActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_business_user);
        populateView();
    }

    private void setEditText(int id, String value) {
        EditText field = (EditText)(findViewById(id));
        field.setText(value);
    }
    void populateView() {

        setEditText(R.id.email, EndpointManager.getAccountName());
        setEditText(R.id.mobile, KhanaKiranaBusinessMainActivity.getDetectedPhoneNumber());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

}