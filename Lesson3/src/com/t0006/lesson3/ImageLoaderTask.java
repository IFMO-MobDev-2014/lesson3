package com.t0006.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by AlekseiLatyshev on 29.09.14.
 */
public class ImageLoaderTask extends AsyncTask<String, Bitmap, Void> {
    private final static String apiKey = "BpCqo8d2szIezEAR1SA5uI95zFSvZdbHdVhxPySlzOs";
    private static int count = 0;
    private static String lastWord = null;
    private final String LOG_TAG = ImageLoaderTask.class.getSimpleName();
    private AsyncTaskFragment fragment;
    private ImagesAdapter adapter;

    public ImageLoaderTask(AsyncTaskFragment fragment, ImagesAdapter adapter) {
        this.fragment = fragment;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... params) {
        String word = params[0];

        if (!word.equals(lastWord)) {
            count = 0;
            lastWord = word;
        }

        final String mainURLString = "https://api.datamarket.azure.com/Bing/Search/Image?$format=json&Query=%27" + word + "%27&ImageFilters=%27Size%3AMedium%27";
        byte[] apiKeyBytes = Base64.encode((":" + apiKey).getBytes(), Base64.DEFAULT);
        String apiEncryptedKey = new String(apiKeyBytes);

        int needCount = 10;
        while (needCount > 0) {
            try {
                URL mainUrl = new URL(mainURLString + "&$skip=" + (count) + "&$top=" + (needCount));

                URLConnection connection = mainUrl.openConnection();
                connection.setRequestProperty("Authorization", "Basic " + apiEncryptedKey);

                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }
                JSONObject json;
                try {
                    json = new JSONObject(builder.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
                int ok = 0;
                for (int i = 0; i < needCount; i++) {
                    count++;
                    String imageURLString;
                    try {
                        imageURLString = json.getJSONObject("d").getJSONArray("results").getJSONObject(i).getString("MediaUrl");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return null;
                    }
                    Log.v(LOG_TAG, "download " + imageURLString);
                    URL imageURL = new URL(imageURLString);
                    URLConnection imageConnection = imageURL.openConnection();
                    InputStream imageInputStream = imageConnection.getInputStream();
                    Bitmap bmp = BitmapFactory.decodeStream(imageInputStream);
                    if (bmp != null) {
                        publishProgress(bmp);
                        ok++;
                    }
                }
                needCount -= ok;
            } catch (IOException e) {
                Log.e(LOG_TAG, "connection problem");
                fragment.showMessage(R.string.error_internet_connection);
                return null;
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Bitmap... values) {
        for (Bitmap bitmap : values)
            adapter.addItem(bitmap);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapter.onFinishedLoading();
    }
}
