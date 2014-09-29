package com.example.searchandtranslate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by knah on 24.09.2014.
 */

public class DataLoader {

    public static class MyCallbackString extends MyCallback<String> {
        public MyCallbackString(Context context){
            super(context);
        }
        public void run(String param) {
            OutputActivity.text.setText(param);
        }
    }

    public static class MyCallbackPicture extends  MyCallback<Bitmap> {
        ImageView image_view;
        PicturesAdapter adapter;
        int index;

        public MyCallbackPicture(Context context, PicturesAdapter adapter, int index, ImageView image_view) {
            super(context);
            this.image_view = image_view;
            this.adapter = adapter;
            this.index = index;
        }

        public void run(Bitmap param) {
            adapter.setBitmap(index, param);
            ListView lv = (ListView) image_view.getParent();
            if(lv != null)
                lv.invalidateViews(); // probably not the best way to do it
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
                JSONObject res = new JSONObject(streamToString(uc.getInputStream()));
                uc.disconnect();
                String rv = res.getJSONArray("text").getString(0);
                success = true;
                return rv;
            } catch(IOException ignore) {
            } catch(JSONException ignore) {
            }

            return words[0];
        }

        @Override
        protected void onPostExecute(String result) {
            if(!success)
                Toast.makeText(callback.getContext(), R.string.toast_translate_error, Toast.LENGTH_SHORT).show();

            callback.run(result);
        }
    }

    private static class SearchQueryRunnable implements Runnable {

        private final String query;
        private final static String bingApiKey = "a2VTQnZrTWJtSERLRERJMkFMd3F0Z3gvQnd1RkRvSzlKZFdpQUR4Y3N0VT06a2VTQnZrTWJtSERLRERJMkFMd3F0Z3gvQnd1RkRvSzlKZFdpQUR4Y3N0VT0=";

        public SearchQueryRunnable(String query) {
            this.query = query;
        }

        @Override
        public void run() {

            searchLocks.get(query).lock();

            URL[] result = new URL[10];

            int j = 0;
            try {
                URL target = new URL("https://api.datamarket.azure.com/Bing/Search/Image?$format=json&$top=15&ImageFilters='Size:Medium'&Query=" + URLEncoder.encode("'" + query+ "'", "UTF-8"));
                HttpURLConnection uc = (HttpURLConnection) target.openConnection();
                uc.setConnectTimeout(5000);
                uc.setReadTimeout(5000);
                uc.addRequestProperty("Authorization", "Basic "+bingApiKey);
                uc.connect();
                String s = streamToString(uc.getInputStream());
                JSONObject res = new JSONObject(s);
                uc.disconnect();
                JSONArray arr = res.getJSONObject("d").getJSONArray("results");
                if(arr.length() != 0) {
                    for(int i = 0; i < arr.length() && i < 10; i++) {
                        String url = arr.getJSONObject(i).getString("MediaUrl");
                        result[j] = new URL(url);
                        j++;
                    }
                }
            } catch(IOException ignore) {
            } catch(JSONException ignore) {
            }

            searchResults.put(query, result);

            searchLocks.get(query).unlock();
            searchLocks.remove(query);

        }
    }

    private static class PictureDownloadRunnable implements Runnable {

        private final String word;
        private final int index;
        private final MyCallback<Bitmap> callback;

        public PictureDownloadRunnable(String word, int index, MyCallback<Bitmap> callback) {
            this.word = word;
            this.index = index;
            this.callback = callback;
        }

        @Override
        public void run() {
            try {
                ReentrantLock lock = searchLocks.get(word);
                if (lock != null) {
                    lock.lock();
                    lock.unlock();
                }

                URL url = searchResults.get(word)[index];

                if (url == null)
                    return; // we are a bad person

                HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                uc.setConnectTimeout(1000);
                uc.setReadTimeout(1000);
                uc.connect();
                final Bitmap rs = BitmapFactory.decodeStream(uc.getInputStream());
                uc.disconnect();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(rs == null)
                            Toast.makeText(callback.getContext(), R.string.toast_pictures_error, Toast.LENGTH_SHORT);
                        else
                            callback.run(rs);
                    }
                });
            } catch (IOException ignore) {
            }
        }
    }

    private static String streamToString(InputStream s) {
        Scanner scn = new Scanner(s);
        scn.useDelimiter("\\A");
        return scn.next();
    }

    public static void asyncTranslate(String word, MyCallback<String> callback) {
        new TranslateTask(callback).execute(word);
    }

    public static void asyncLoadPictures(String word, int i, MyCallback<Bitmap> callback) {
        if(searchResults.containsKey(word) || searchLocks.containsKey(word)) {
            pool.submit(new PictureDownloadRunnable(word, i, callback));
        } else {
            searchLocks.put(word, new ReentrantLock(true));
            searchLocks.get(word).lock();
            pool.submit(new SearchQueryRunnable(word));
            searchLocks.get(word).unlock();
            pool.submit(new PictureDownloadRunnable(word, i, callback));
        }
    }

    private static HashMap<String, ReentrantLock> searchLocks = new HashMap<String, ReentrantLock>();
    private static HashMap<String, URL[]> searchResults = new HashMap<String, URL[]>();
    private static ExecutorService pool = Executors.newFixedThreadPool(5);
    private static Handler handler = new Handler(Looper.getMainLooper());
}
