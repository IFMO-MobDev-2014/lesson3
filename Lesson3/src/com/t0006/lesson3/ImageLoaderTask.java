package com.t0006.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;

/**
 * Created by dimatomp on 27.09.14.
 */
public class ImageLoaderTask extends AsyncTask<String, Bitmap, Void> {
    private AsyncTaskFragment fragment;
    private ImagesAdapter adapter;

    public ImageLoaderTask(AsyncTaskFragment fragment, ImagesAdapter adapter) {
        this.fragment = fragment;
        this.adapter = adapter;
    }
    static int count = 0;
    private void returnImage(String word, int index) {
        try {
            URL url = new URL("http://ajax.googleapis.com/ajax/services/search/images?v=1.0&imgsz=medium&rsz=8&start="
                    + 8 * index + "&q="+ word);
            Log.i("URL","create start URL");
            URLConnection connection = url.openConnection();
            Log.i("URL","create start connection");
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());
            for (int i = 0; i < 8; i++) {
                String imURL;
                try {
                    imURL = (String) ((JSONObject) ((JSONArray) ((JSONObject) json.get("responseData")).get("results")).get(i)).get("url");
                } catch (Exception e) {

                    continue;
                }
                Log.i("URL", imURL);
                URL imageURL = new URL(imURL);
                Log.i("URL", "create URL");
                URLConnection imageConnection = imageURL.openConnection();
                Log.i("URL", "create connection " + (imageConnection != null));
                InputStream inp = imageConnection.getInputStream();
                Log.i("URL", "create InputStream " + (inp != null));
                Bitmap bmp = BitmapFactory.decodeStream(inp);
                Log.i("URL", "create BitMap " + (bmp != null));
                if (bmp != null) {
                    count ++;
                    publishProgress(bmp);
                }
                if (count == 10) {
                    break;
                }
            }
        } catch (JSONException e) {
            Log.i("URL","ALL BAD in JSON");
            return;
        } catch (MalformedURLException e) {
            Log.i("URL","ALL BAD in URL");
            return;
        } catch (IOException e) {
            Log.i("URL", "ALL BAD in IO");
            return;
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        int i = 0;
        count = 0;
        while (count < 10) {
            returnImage(params[0], i);
            i++;
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
