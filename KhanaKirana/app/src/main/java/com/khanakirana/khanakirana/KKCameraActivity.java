package com.khanakirana.khanakirana;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
        showPopup();
//        setContentView(R.layout.adding_items_master_list);
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
    // The method that displays the popup.
    void showPopup() {
        final Point p = new Point(0, 0);
        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        final int OFFSET_X = 5;
        final int OFFSET_Y = 5;

        int popupWidth = 400;
        int popupHeight = 450;

        // Inflate the popup_layout.xml
        RelativeLayout viewGroup = (RelativeLayout) this.findViewById(R.id.add_item_master_list);
        LayoutInflater layoutInflater = (LayoutInflater) this
                .getSystemService(this.LAYOUT_INFLATER_SERVICE);
        final View layout = layoutInflater.inflate(R.layout.adding_items_master_list, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(this);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);
        this.imageView = (ImageView)layout.findViewById(R.id.itemImage);
        ImageButton photoButton = (ImageButton) layout.findViewById(R.id.takeItemPicture);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        this.scannedValue = (TextView)layout.findViewById(R.id.scannedBarcodeValue);
        final ImageButton scanButton = (ImageButton) layout.findViewById(R.id.scanBarcode);
        final KKCameraActivity cameraActivity = this;
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(cameraActivity);
                scanIntegrator.initiateScan();
            }
        });


        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        popup.setContentView(layout);
        // Displaying the popup at the specified location, + offsets.
        layout.post(new Runnable() {
            @Override
            public void run() {

                popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });

        // Getting a reference to Close button, and close the popup when clicked.
/*        ((Button) layout.findViewById(R.id.register)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        // Getting a reference to Close button, and close the popup when clicked.
        ((Button) layout.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });*/
    }
}