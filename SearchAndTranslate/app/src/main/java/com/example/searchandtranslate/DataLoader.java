package com.example.searchandtranslate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Created by knah on 24.09.2014.
 */

public class DataLoader {

    public static class MyCallbackString implements MyCallback<String> {
        public MyCallbackString(){}
        public void run(String param) {
            OutputActivity.text.setText(param);
        }
    }

    public static class MyCallbackPicture implements  MyCallback<Bitmap> {
        Context context;
        ImageView image_view;

        public MyCallbackPicture(Context context, ImageView image_view) {
            this.image_view = image_view;
            this.context = context;
        }

        public void run(Bitmap param) {
            image_view.setImageBitmap(param);
        }
    }

    private static class TranslateTask extends AsyncTask<String, Void, String> {


        private MyCallback<String> callback;

        public TranslateTask(MyCallback<String> callback) {
            this.callback = callback;
        }

        private final static String apiKey = "trnsl.1.1.20140929T080024Z.97e719a764c788cc.55b5fe56ffdce794f905bdaa1a08175cc9e45d77";

        boolean success = false;
        @Override
        protected String doInBackground(String... words) {
            try {
                URL target = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?lang=en-ru&key=" + apiKey + "&text=" + URLEncoder.encode(words[0], "UTF-8"));
                HttpURLConnection uc = (HttpURLConnection) target.openConnection();
                uc.setConnectTimeout(1000);
                uc.setReadTimeout(1000);
                uc.connect();
                JSONObject res = new JSONObject(PictureLoaderThread.streamToString(uc.getInputStream()));
                uc.disconnect();
                String rv = res.getJSONArray("text").getString(0);
                success = true;
                return rv;
            } catch(IOException e) {

            } catch(JSONException e) {

            }

            return words[0];
        }

        @Override
        protected void onPostExecute(String result) {
            callback.run(result);
        }
    }

    private static class PictureLoaderThread extends Thread {
        private final String query;
        private final MyCallback<Bitmap> callback;

        public PictureLoaderThread(String query, MyCallback<Bitmap> callback) {
            super();
            this.query = query;
            this.callback = callback;
        }

        private static String streamToString(InputStream s) {
            Scanner scn = new Scanner(s);
            scn.useDelimiter("\\A");
            return scn.next();
        }

        @Override
        public void run() {
            Bitmap[] result = new Bitmap[10];

            Log.i("HTTP","Started loading");

            try {
                URL target = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&imgsz=medium&q=" + URLEncoder.encode(query, "UTF-8"));
                HttpURLConnection uc = (HttpURLConnection) target.openConnection();
                uc.setConnectTimeout(1000);
                uc.setReadTimeout(1000);
                uc.connect();
                JSONObject res = new JSONObject(streamToString(uc.getInputStream()));
                uc.disconnect();
                JSONArray arr = res.getJSONObject("responseData").getJSONArray("results");
                int j = 0;
                if(arr.length() != 0) {
                    for(int i = 0; i < arr.length(); i++) {
                        String url = arr.getJSONObject(i).getString("url");
                        try {
                            target = new URL(url);
                            uc = (HttpURLConnection) target.openConnection();
                            uc.setConnectTimeout(1000);
                            uc.setReadTimeout(1000);
                            uc.connect();
                            result[j] = BitmapFactory.decodeStream(uc.getInputStream());
                            if(result[j] != null)
                                j++;
                        } catch(IOException e) {
                            Log.e("HTTP", "IOE in pic", e);
                        }
                    }
                }
                target = new URL("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=" + Math.min(10 - j, 8) + "&start=8&imgsz=medium&q=" + URLEncoder.encode(query, "UTF-8"));
                uc = (HttpURLConnection) target.openConnection();
                uc.setConnectTimeout(1000);
                uc.setReadTimeout(1000);
                uc.connect();
                res = new JSONObject(streamToString(uc.getInputStream()));
                uc.disconnect();
                arr = res.getJSONObject("responseData").getJSONArray("results");
                if(arr.length() != 0) {
                    for(int i = 0; i < arr.length() && j < 10; i++) {
                        String url = arr.getJSONObject(i).getString("url");
                        try {
                            target = new URL(url);
                            uc = (HttpURLConnection) target.openConnection();
                            uc.setConnectTimeout(1000);
                            uc.setReadTimeout(1000);
                            uc.connect();
                            result[j] = BitmapFactory.decodeStream(uc.getInputStream());
                            if(result[j] != null)
                                j++;
                        } catch(IOException e) {
                            Log.e("HTTP", "IOE in pic 2", e);
                        }
                    }
                }
            } catch(IOException e) {
                Log.e("HTTP", "IOE in all", e);
            } catch(JSONException e) {
                Log.e("HTTP", "JSE in all", e);
            }

            Log.i("HTTP", "Done loading");

            final Bitmap[] finalResult = result;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.run(finalResult[0]);
                }
            });
        }
    }

    public static void asyncTranslate(String word, MyCallback<String> callback) {
        new TranslateTask(callback).execute(word);
    }

    public static void asyncLoadPictures(String word, int i, MyCallback<Bitmap> callback) {
        if(handler == null)
            handler = new Handler(Looper.getMainLooper());

        if(loaderThread != null) { // todo: do it better
            loaderThread.interrupt();
            try {
                loaderThread.join();
            } catch (InterruptedException ie) {}
        }

        loaderThread = new PictureLoaderThread(word, callback);
        loaderThread.start();

    }

    private static PictureLoaderThread loaderThread;
    private static Handler handler;
}
