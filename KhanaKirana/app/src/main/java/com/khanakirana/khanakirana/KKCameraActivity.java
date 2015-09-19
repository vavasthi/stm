package com.khanakirana.khanakirana;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by vavasthi on 19/9/15.
 */
public class KKCameraActivity extends Activity {

    static final int CAMERA_REQUEST = 1888;
    static final int SCAN_REQUEST = 1999;
    private ImageView imageView;
    private TextView scannedValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_items_master_inventory);
        this.imageView = (ImageView)this.findViewById(R.id.itemImage);
        ImageButton photoButton = (ImageButton) this.findViewById(R.id.takeItemPicture);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        this.scannedValue = (TextView)this.findViewById(R.id.scannedBarcodeValue);
        final ImageButton scanButton = (ImageButton) this.findViewById(R.id.scanBarcode);
        final KKCameraActivity cameraActivity = this;
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(cameraActivity);
                scanIntegrator.initiateScan();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
        else if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                scannedValue.setText(result.getContents());
            }
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}