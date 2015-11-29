package com.avasthi.roadcompanion.utils;

import android.hardware.camera2.params.Face;

import com.avasthi.roadcompanion.data.FacebookUser;
import com.avasthi.roadcompanion.data.GroupHeader;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vavasthi on 26/11/15.
 */
public class JsonObjectFactory {
    public static JsonObjectFactory INSTANCE = new JsonObjectFactory();
    private JsonObjectFactory() {

    }
    public static List<GroupHeader> parseListOfGroupHeaders(JSONObject obj) {
        List<GroupHeader> groupHeaderList = new ArrayList<>();
        try {
            JSONArray data = obj.getJSONArray("data");
            for (int i = 0; i < data.length(); ++i) {
                JSONObject o = data.getJSONObject(i);
                groupHeaderList.add(new GroupHeader(o.getString("id"), o.getString("name"), o.getString("privacy"), o.getString("icon")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return groupHeaderList;
    }
    public static List<FacebookUser> parseListOfFacebookUsersIntoList(JSONObject obj, List<FacebookUser> facebookUserList) {
        try {
            JSONArray data = obj.getJSONArray("data");
            for (int i = 0; i < data.length(); ++i) {
                JSONObject o = data.getJSONObject(i);
                facebookUserList.add(new FacebookUser(o.getString("id"), o.getString("name"), o.getBoolean("administrator")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return facebookUserList;
    }
}
