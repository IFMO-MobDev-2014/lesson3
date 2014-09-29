package ru.ifmo.md.lesson3.patrikeev_shah.translator;

import android.net.Uri;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sultan on 28.09.14.
 */
public class UrlsImagesReceiver extends AsyncTask<String, Void, ArrayList<String>>{

    private static final String apiKey = "02d7a16842665795fc048b6c6b3fab59";
    private static final int COUNT_IMAGES = 10;

    private class PhotoFormat {
        private String id;
        private String owner;
        private String secret;
        private String server;
        private int farm;
        private String title;
        private int ispublic;
        private int isfriend;
        private int isfamily;
    }

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        String uri = Uri.parse("https://www.flickr.com/services/rest")
                .buildUpon()
                .appendQueryParameter("api_key", apiKey)
                .appendQueryParameter("method", "flickr.photos.search")
                .appendQueryParameter("format", "json")
                .appendQueryParameter("privacy_filter", "1")
                .appendQueryParameter("media", "photos")
                .appendQueryParameter("per_page", String.valueOf(COUNT_IMAGES))
                .appendQueryParameter("text", strings[0])
                .build().toString();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(uri);
        Gson gson = new Gson();
        ArrayList<String> urls = new ArrayList<String>();
        try {
            String jsonp = client.execute(request, new BasicResponseHandler());
            String responseStr = jsonp.substring(jsonp.indexOf("["), jsonp.lastIndexOf("]") + 1);
            PhotoFormat[] photos = gson.fromJson(responseStr, PhotoFormat[].class);
            int photosCount = photos.length;

            for (int i = 0; i < photosCount; i++) {
                String photoUrl = "https://farm" + photos[i].farm + ".staticflickr.com/" +
                        photos[i].server + "/" + photos[i].id + "_" + photos[i].secret + "_q.jpg";
                urls.add(photoUrl);
            }
        } catch (IOException e) {
            return null;
        }
        return urls;
    }
}
