package com.room.translator491;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemImageResult {

    private String fUrl;
    private String sUrl;

    public ItemImageResult(JSONObject json) {
        try {
            this.fUrl = json.getString("url");
            this.sUrl = json.getString("tbUrl");
        } catch (JSONException e) {
            this.fUrl = null;
            this.sUrl = null;
        }
    }

    public String getFUrl() {
        return fUrl;
    }

    public String getSUrl() {
        return sUrl;
    }

    public String toString() {
        return sUrl;
    }

    public static ArrayList<ItemImageResult> fromJSONArray(JSONArray imageJsonResults) {
        ArrayList<ItemImageResult> result = new ArrayList<ItemImageResult>();
        for (int x = 0; x < imageJsonResults.length(); x++) {
            try {
                result.add(new ItemImageResult(imageJsonResults.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
