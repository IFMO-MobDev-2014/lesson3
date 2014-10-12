package com.pinguinson.translator.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pinguinson.translator.activities.ResultActivity;
import com.pinguinson.translator.api.FiveHundredQuery;
import com.pinguinson.translator.models.Photo;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by pinguinson on 12.10.2014.
 */
public class FiveHundredSearchTask extends AsyncTask<String, Void, ArrayList<Photo>> {
    ResultActivity activity;

    public static final String API_KEY = "I9xyEPPXQKl7cyuhps5UCQr8AmHcne2rOi6f1TOa";

    public FiveHundredSearchTask(ResultActivity activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Photo> doInBackground(String... strings) {
        FiveHundredQuery fiveHundredQuery = new FiveHundredQuery(API_KEY);
        String query = strings[0];
        fiveHundredQuery.addParameter("image_size", "4");
        fiveHundredQuery.addParameter("term", query);
        ArrayList<Photo> pictures = new ArrayList<Photo>();
        try {
            JSONArray searchResults = fiveHundredQuery.get().getJSONArray("photos");
            for (int i = 0; i < searchResults.length(); ++i) {
                JSONArray current = searchResults.getJSONObject(i).getJSONArray("images");
                String url = current.getJSONObject(0).getString("https_url");
                Log.i("url", url);
                pictures.add(new Photo(url));
                if (pictures.size() == 10) {
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("FlickrSeacrhTask", "Search failed.", e);
        }
        return pictures;
    }

    @Override
    protected void onPostExecute(ArrayList<Photo> photos) {
        super.onPostExecute(photos);
        activity.onSearchFinished(photos);
    }
}