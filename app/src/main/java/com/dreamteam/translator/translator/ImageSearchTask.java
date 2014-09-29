package com.dreamteam.translator.translator;

import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by flyingleafe on 25.09.14.
 */
public class ImageSearchTask extends AsyncTask<String, Void, ArrayList<String>> {
    static String API_KEY = "9c8dafc31de757b0ee0a40ca950a085b";

    SearchField screen;

    public ImageSearchTask(SearchField screen) {
        this.screen = screen;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        String uri = Uri.parse("https://www.flickr.com/services/rest")
                .buildUpon()
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("method", "flickr.photos.search")
                .appendQueryParameter("format", "json")
                .appendQueryParameter("per_page", "10")
                .appendQueryParameter("text", strings[0])
                .build().toString();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(uri);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseStr;
        JSONObject responseJson;
        ArrayList<String> urls = new ArrayList<String>();

        try {
            String jsonp = client.execute(request, responseHandler);
            try {
                responseStr = jsonp.substring(jsonp.indexOf("(") + 1, jsonp.lastIndexOf(")"));
                responseJson = new JSONObject(responseStr);
                JSONArray results = responseJson.getJSONObject("photos").getJSONArray("photo");
                int resultsCount = results.length();

                for (int i = 0; i < resultsCount; i++) {
                    JSONObject result = results.getJSONObject(i);
                    String farm = result.getString("farm");
                    String server = result.getString("server");
                    String id = result.getString("id");
                    String secret = result.getString("secret");
                    String photoUrl = "https://farm" + farm + ".staticflickr.com/" +
                            server + "/" + id + "_" + secret + "_q.jpg";
                    urls.add(photoUrl);
                }
            } catch (JSONException ignore) {
            } // Flickr JSON is a valid JSON
        } catch (IOException e) {
            // TODO: some error reporting would be nice
            e.printStackTrace();
        }

        return urls;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        screen.onImageSearchCancelled();
    }

    @Override
    protected void onPostExecute(ArrayList<String> urls) {
        super.onPostExecute(urls);
        screen.onImageSearchFinished(urls);
    }
}
