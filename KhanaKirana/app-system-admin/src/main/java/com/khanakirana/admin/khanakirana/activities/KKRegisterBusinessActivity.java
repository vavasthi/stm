package com.khanakirana.admin.khanakirana.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.khanakirana.admin.khanakirana.R;


/**
 * Created by vavasthi on 19/9/15.
 */
public class KKRegisterBusinessActivity extends KhanaKiranaAdminAbstractActivity {


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

        setEditText(R.id.email, KhanaKiranaMainAdminActivity.getSelectedAccountName());
        setEditText(R.id.mobile, KhanaKiranaMainAdminActivity.getDetectedPhoneNumber());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

}