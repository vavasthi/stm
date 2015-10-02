package com.khanakirana.khanakirana.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.khanakirana.backend.userRegistrationApi.model.MeasurementCategory;
import com.khanakirana.khanakirana.IntentIntegrator;
import com.khanakirana.khanakirana.IntentResult;
import com.khanakirana.khanakirana.KKAndroidConstants;
import com.khanakirana.khanakirana.KhanaKiranaMainActivity;
import com.khanakirana.khanakirana.R;
import com.khanakirana.khanakirana.background.tasks.AddMasterItemTask;
import com.khanakirana.khanakirana.utils.KKImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by vavasthi on 19/9/15.
 */
public class KKAddProductInMasterListActivity extends KKMeasurementCategoryReceivingActivity  {

    private ImageView imageView;
    private TextView scannedValue;
    File imageFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPopup();
        imageFile = KKImageUtils.getImageURI(this);

//        setContentView(R.layout.adding_items_master_list);
    }

    void createPopup() {

        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) this.findViewById(R.id.add_item_master_list);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.adding_items_master_list, viewGroup);

        // Creating the PopupWindow
        dialog = new Dialog(this);
        dialog.setTitle(R.string.add_item_to_master_list);
        dialog.setContentView(layout);
        this.imageView = (ImageView)layout.findViewById(R.id.itemImage);
        ImageButton photoButton = (ImageButton) layout.findViewById(R.id.takeItemPicture);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                /*Uri uri = Uri.fromFile(KKImageUtils.getImageURI(KKAddProductInMasterListActivity.this));
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);*/
                startActivityForResult(cameraIntent, KKAndroidConstants.CAMERA_REQUEST);
            }
        });
        this.scannedValue = (TextView)layout.findViewById(R.id.scannedBarcodeValue);
        final ImageButton scanButton = (ImageButton) layout.findViewById(R.id.scanBarcode);
        final KKAddProductInMasterListActivity cameraActivity = this;
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(cameraActivity);
                scanIntegrator.initiateScan();
            }
        });



        dialog.setContentView(layout);
    }
    // The method that displays the dialog.
    void showPopup() {

        List<String> categoryNameList = new ArrayList<>();
        for (MeasurementCategory mc : mcList) {
            categoryNameList.add(mc.getName());
        }
        ArrayAdapter<String> categoryListAdapter = new ArrayAdapter<String>(this, R.layout.measurement_unit_item, categoryNameList);
        categoryListAdapter.setDropDownViewResource(R.layout.measurement_unit_item);
        Spinner categories = (Spinner)(dialog.findViewById(R.id.measurement_category));
        categories.setAdapter(categoryListAdapter);
        progressDialog.dismiss();
        dialog.show();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == KKAndroidConstants.CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            imageView.setTag(R.id.item_bitmap, photo);
        }
        else if (requestCode == KKAndroidConstants.BARCODE_SCAN_REQUEST && resultCode == RESULT_OK) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                scannedValue.setText(result.getContents());
            }
        }
    }

    public void setCategories(List<MeasurementCategory> categories) {
        this.mcList = categories;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                showPopup();
            }
        });
    }

    public void addMasterItem(View v) {

        LinearLayout viewGroup = (LinearLayout) dialog.findViewById(R.id.add_item_master_list);
        ImageView imageView = (ImageView)(dialog.findViewById(R.id.itemImage));
        String barCode = ((TextView)(dialog.findViewById(R.id.scannedBarcodeValue))).getText().toString();
        String itemTitle = ((EditText)(dialog.findViewById(R.id.item_title))).getText().toString();
        String itemDescription = ((EditText)(dialog.findViewById(R.id.item_description))).getText().toString();
        Spinner categories = (Spinner)(dialog.findViewById(R.id.measurement_category));
        String measurementCategory = categories.getSelectedItem().toString();
        byte[] imageBytes = KKImageUtils.getByteArrayFromBitmap((Bitmap) imageView.getTag(R.id.item_bitmap), true);

        /*Blob content = new Blob();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        InputStream
        content.setBinaryStream(bis);
        Spinner categories = (Spinner)(dialog.findViewById(R.id.measurement_category));
        String unitCategory = categories.getSelectedItem().toString();*/
        dialog.dismiss();
        progressDialog.show();
        new AddMasterItemTask(this,
                KhanaKiranaMainActivity.getEndpoints(),
                itemTitle,
                itemDescription,
                barCode,
                "image/png",
                KhanaKiranaMainActivity.getSelectedAccountName(),
                measurementCategory,
                imageBytes).execute();
    }
    public void dismisProgressIndicator() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                KKAddProductInMasterListActivity.this.finish();
            }
        });
    }
}