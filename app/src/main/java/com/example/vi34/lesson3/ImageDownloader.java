package com.example.vi34.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/**
 * Created by vi34 on 26.09.14.
 */
public class ImageDownloader {

    public void search(String query) {
        new DownloadImageTask().execute(query);
    }

    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap[]> {

        protected Bitmap[] doInBackground(String... query) {
            Bitmap[] bmp = new Bitmap[10];
            URL url;
            URLConnection connection;
            String line;
            JSONObject json;
            JSONArray resultArray;
            String[] imageLinks = new String[10];
            InputStream input;

            try {
                // need modify query (check for spaces, and prohibited symbols)
                query[0] = URLEncoder.encode(query[0], "UTF-8"); // I think here are some problems with correct encoding

            } catch (Exception e) {
                Log.e("ASYNC", "Encode problems");
            }
            try {
                for (int k = 0; k < 2; k++) {

                    url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
                            "v=1.0&q=" + query[0] + "&imgsz=medium&rsz=5&userip=INSERT-USER-IP" + "&start=" + (k * 5));
                    connection = url.openConnection();
                    connection.addRequestProperty("Referer", "ITMO homework app");
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    json = new JSONObject(builder.toString());
                    resultArray = json.getJSONObject("responseData").getJSONArray("results");


                    try {
                        for (int i = 0; i < 5; i++) {
                            json = resultArray.getJSONObject(i);
                            imageLinks[i + k * 5] = json.getString("url");
                            url = new URL(imageLinks[i + k * 5]);
                            connection = url.openConnection();
                            connection.setDoInput(true);

                            connection.connect();

                            input = connection.getInputStream();
                            bmp[i + k * 5] = BitmapFactory.decodeStream(input);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {

            }

            return bmp;
        }

        protected void onPostExecute(Bitmap[] result) {
            Log.i("ASYNC", "Done");
            SecondActivity.bmp = result;
            //---- Bad solution... fix it later
            for (int i = 0; i < 10; i++) {
                SecondActivity.imageView[i].setImageBitmap(result[i]);
                SecondActivity.imageView[i].invalidate();
            }
            //----
        }
    }
}
