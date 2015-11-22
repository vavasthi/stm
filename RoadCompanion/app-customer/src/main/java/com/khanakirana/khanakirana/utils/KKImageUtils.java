package com.khanakirana.khanakirana.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.khanakirana.khanakirana.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by vavasthi on 24/9/15.
 */
public class KKImageUtils {

    private static final int PRODUCT_IMAGE_WIDTH = 640;
    public static Bitmap getBitmapFromByteArray(byte[] byteArray) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        return bmp;
    }

    public static  byte[] getByteArrayFromBitmap(Context context, File imageFile, boolean scale) {

        if (imageFile != null) {

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(imageFile));
                return getByteArrayFromBitmap(bitmap, scale);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] getByteArrayFromBitmap(Bitmap bitmap, boolean scale) {

        Bitmap scaledBitmap = bitmap;
        if (scale) {
            scaledBitmap = scale(bitmap);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    public static  Bitmap scale (Bitmap source){
        int w = source.getWidth();
        double factor = 1.0;
        if (w > PRODUCT_IMAGE_WIDTH) {
            factor = ((double)w) / ((double)PRODUCT_IMAGE_WIDTH);
        }
        else {

        }

        int dw = new Double((((double)factor) * ((double)(source.getWidth())))).intValue();
        int dh = new Double((((double)factor) * ((double)(source.getHeight())))).intValue();
        return Bitmap.createScaledBitmap(source, dw, dh, false);
    }

    public static File getImageURI(Context context) {
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getString(R.string.app_name));
        if (!dir.mkdir()) {
            return null;
        }
        else {
            try {
                File f = File.createTempFile("item", ".jpg", dir);
                f.delete();
                return f;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
