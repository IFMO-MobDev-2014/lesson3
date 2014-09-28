package com.example.home.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Base64;

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

/**
 * Created by Snopi on 27.09.2014.
 */
public class PicturesDownloader extends AsyncTask<String, Void, String[]> {

    private Picture picture;

    public PicturesDownloader(Picture p) {
        picture = p;
    }

    private static final String apiKey = "PXaJFgnFYAKfNdr3Gir/NfxjKVEOvt4kkQWAd9KMusM=";
    private static String readStream(InputStream data) {
        String ans = "";
        try {
            StringBuilder inStr = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(data));
            String line = br.readLine();
            while (line != null) {
                inStr.append(line);
                inStr.append('\n');
                line = br.readLine();
            }
            ans = inStr.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ans;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        for (String url : strings) {
            new SingleImageDownloader().execute(url);
        }
    }

    @Override
    protected String[] doInBackground(String... strings) {
        int numOfPictures = 10;
        String[] picUrls = new String[10];
        String bingUrl = "https://api.datamarket.azure.com/Bing/Search/Image?$format=json&$top=" +
                numOfPictures + "&Query=%27" + strings[0].replaceAll(" ", "+") + "%27";
        byte[] apiKeyBytes = Base64.encode((":" + apiKey).getBytes(), Base64.DEFAULT);
        String apiEnc = new String(apiKeyBytes);
        try {
            URL url = new URL(bingUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Basic " + apiEnc);
            JSONObject response = new JSONObject(readStream(urlConnection.getInputStream()));
            JSONArray results = response.getJSONObject("d").getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                picUrls[i] = results.getJSONObject(i).getJSONObject("Thumbnail").getString("MediaUrl");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return picUrls;
    }

    private class SingleImageDownloader extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            picture.addBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                return BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
