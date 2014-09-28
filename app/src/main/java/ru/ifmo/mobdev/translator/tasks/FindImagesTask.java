package ru.ifmo.mobdev.translator.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.fivehundredpx.api.PxApi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ru.ifmo.mobdev.translator.Application;
import ru.ifmo.mobdev.translator.activities.ShowResultsActivity;
import ru.ifmo.mobdev.translator.models.Picture;

/**
 * @author nqy
 */
public class FindImagesTask extends AsyncTask<String, Void, ArrayList<Picture>> {
    private ShowResultsActivity activity;

    public FindImagesTask(ShowResultsActivity activity){
        this.activity = activity;
    }

    @Override
    protected ArrayList<Picture> doInBackground(String... strings) {
        PxApi api = new PxApi(Application.CONSUMER_KEY);
        String term = strings[0]; //TODO check if null
        ArrayList<Picture> result = new ArrayList<Picture>();
        try {
            JSONArray searchResults = api.get("/photos/search?rpp=10&image_size=4&term=" + term).getJSONArray("photos");
            for (int i = 0; i < searchResults.length(); ++i) {
                JSONObject current = searchResults.getJSONObject(i);
                String author = current.getJSONObject("user").getString("fullname");
                String name = current.getString("name");
                JSONArray images = current.getJSONArray("images");
                String url = images.getJSONObject(0).getString("https_url");
                result.add(new Picture(name, author, url));
            }
        } catch (Exception e) {
            Log.e("FindImagesTask", "Search failed.", e);
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<Picture> pictures) {
        super.onPostExecute(pictures);
        activity.onImagesFound(pictures);
    }
}