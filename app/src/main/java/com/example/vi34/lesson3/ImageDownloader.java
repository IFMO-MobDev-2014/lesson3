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


/**
 * Created by vi34 on 26.09.14.
 */
public class ImageDownloader {

    Bitmap bmp[] = null;

    public Bitmap[] search(String querry) {

        new DownloadImageTask().execute(querry);
        return bmp;
    }

    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap[]> {

        protected Bitmap[] doInBackground(String... query) {
            Bitmap[] bmp = new Bitmap[10];

            try {

                // Google allows only 8 results per query - think about fix
                URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
                        "v=1.0&q=" + "nyan%20cat" + "&imgsz=medium&rsz=8&userip=INSERT-USER-IP");


                URLConnection connection = url.openConnection();
                connection.addRequestProperty("Referer", "ITMO homework app");

                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }


                JSONObject json = new JSONObject(builder.toString());
                Log.i("ASYNC", builder.toString());
                JSONArray resultArray = json.getJSONObject("responseData").getJSONArray("results");
                String[] imageLinks = new String[resultArray.length()];

                InputStream input;

                for (int i = 0; i < resultArray.length(); i++) {
                    json = resultArray.getJSONObject(i);
                    imageLinks[i] = json.getString("url");
                    url = new URL(imageLinks[0]);
                    connection = url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    input = connection.getInputStream();
                    bmp[i] = BitmapFactory.decodeStream(input);
                }
                Log.i("ASYNC", imageLinks[0] + " " + resultArray.length() + " results");




            } catch (Exception e) {
                Log.i("ASYNC", "AAA, SOMETHING GOES WRONG!!!!! AAAA");
            }

            publishProgress(1);

            Log.i("ASYNC", "Do Task OK");
            return bmp;
        }

        protected void onPostExecute(Bitmap[] result) {
            Log.i("ASYNC", "Done");
            bmp = result;
        }
    }
}
