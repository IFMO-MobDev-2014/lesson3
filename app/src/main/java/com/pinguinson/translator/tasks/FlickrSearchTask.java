package com.pinguinson.translator.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pinguinson.translator.api.FlickrQuery;
import com.pinguinson.translator.models.Photo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pinguinson on 12.10.2014.
 */
public class FlickrSearchTask extends AsyncTask<String, Void, ArrayList<Photo>> {

    public static final String API_KEY = "4d32014a753fa0d08aeb426f23010f1d";

    @Override
    protected ArrayList<Photo> doInBackground(String... strings) {
        FlickrQuery flickrQuery = new FlickrQuery(API_KEY);
        String query = strings[0];
        flickrQuery.addParameter("per_page", "30");
        flickrQuery.addParameter("page", "1");
        flickrQuery.addParameter("format", "json");
        flickrQuery.addParameter("nojsoncallback", "1");
        flickrQuery.addParameter("extras", "url_m");
        flickrQuery.addParameter("tags", query);
        ArrayList<Photo> pictures = new ArrayList<Photo>();
        try {
            JSONArray searchResults = flickrQuery.get().getJSONObject("photos").getJSONArray("photo");
            for (int i = 0; i < searchResults.length(); ++i) {
                JSONObject current = searchResults.getJSONObject(i);
                if (current.has("width_m") && current.getString("width_m").equals("500")) {
                    String id = current.getString("id");
                    String url = current.getString("url_m");
                    pictures.add(new Photo(id, url));
                }
                if (pictures.size() == 10) {
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("FlickrSeacrhTask", "Search failed.", e);
        }
        return pictures;
    }
}