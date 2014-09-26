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

    Bitmap bmpRes[] = null;

    public Bitmap[] search(String query) {

        bmpRes = new Bitmap[10];
        new DownloadImageTask().execute(query);
        //return result maybe not needed...
        return bmpRes;
    }

    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap[]> {

        protected Bitmap[] doInBackground(String... query) {
            Bitmap[] bmp = new Bitmap[10];

            try {
                // need modify query (check for spaces, and prohibited symbols)
                query[0] = URLEncoder.encode(query[0], "UTF-8"); // I think here are some problems with correct encoding

                // Google allows only 8 results per query - think about fix
                URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
                        "v=1.0&q=" + query[0] + "&imgsz=medium&rsz=5&userip=INSERT-USER-IP");


                URLConnection connection = url.openConnection();
                connection.addRequestProperty("Referer", "ITMO homework app");

                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }


                JSONObject json = new JSONObject(builder.toString());
                String moreResults = json.getJSONObject("responseData").getJSONObject("cursor").getString("moreResultsUrl");
                Log.i("ASYNC", builder.toString());
                JSONArray resultArray = json.getJSONObject("responseData").getJSONArray("results");
                String[] imageLinks = new String[10];

                InputStream input;

                for (int i = 0; i < 5; i++) {
                    json = resultArray.getJSONObject(i);
                    imageLinks[i] = json.getString("url");
                    url = new URL(imageLinks[i]);
                    connection = url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    input = connection.getInputStream();
                    bmp[i] = BitmapFactory.decodeStream(input);
                }


                ////------

                moreResults = moreResults.replace("&start=0","&start=4"); // not helps...
                url = new URL(moreResults);
                connection = url.openConnection();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                json = new JSONObject(builder.toString());
                resultArray = json.getJSONObject("responseData").getJSONArray("results");

                for (int i = 5; i < resultArray.length() + 5; i++) {
                    json = resultArray.getJSONObject(i - 5);
                    imageLinks[i] = json.getString("url");
                    url = new URL(imageLinks[i]);
                    connection = url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    input = connection.getInputStream();
                    bmp[i] = BitmapFactory.decodeStream(input);
                }

                ////------------

                Log.i("ASYNC", imageLinks[0] + " " + resultArray.length() + " results");




            } catch (Exception e) {
                Log.i("ASYNC", "AAA, SOMETHING GOES WRONG!!!!! AAAA");
            }

            Log.i("ASYNC", "Do Task OK");
            return bmp;
        }

        protected void onPostExecute(Bitmap[] result) {
            Log.i("ASYNC", "Done");
            SecondActivity.bmp = result;
            //---- Bad solution... fix it later
            for(int i = 0; i < 10; i++)
            {
                SecondActivity.imageView[i].setImageBitmap(result[i]);
                SecondActivity.imageView[i].invalidate();
            }
            //----
        }
    }
}
