package com.avasthi.roadcompanion.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.avasthi.roadcompanion.R;
import com.google.api.client.http.HttpResponse;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by vavasthi on 26/11/15.
 */
public class GroupHeader {

    public enum PRIVACY {
        OPEN("OPEN"),
        CLOSED("CLOSED"),
        SECRET("SECRET");

        private final String privacy;

         PRIVACY(String privacy) {
            this.privacy = privacy;
        }
        public static PRIVACY createFromString(String privacy) {
            for(PRIVACY p : PRIVACY.values()) {
                if (p.toString().equalsIgnoreCase(privacy)) {
                    return p;
                }
            }
            throw new IllegalArgumentException("Invalid privacy type.");
        }
    }

    public GroupHeader(String id, String name, PRIVACY privacy, String iconUrl) {
        this.id = id;
        this.name = name;
        this.privacy = privacy;
        this.iconUrl = iconUrl;
    }
    public GroupHeader(String id, String name, String privacy, String iconUrl) {
        this.id = id;
        this.name = name;
        this.privacy = PRIVACY.createFromString(privacy);
        this.iconUrl = iconUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PRIVACY getPrivacy() {
        return privacy;
    }

    public void setPrivacy(PRIVACY privacy) {
        this.privacy = privacy;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Bitmap getIconBitmap() {
        return iconBitmap;
    }

    public void setIconBitmap(Bitmap iconBitmap) {
        this.iconBitmap = iconBitmap;
    }

    private String name;
    private PRIVACY privacy;
    private String id;
    private String  iconUrl;
    private Bitmap iconBitmap;
}
