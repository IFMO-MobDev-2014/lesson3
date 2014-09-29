package com.abrafcm.translator.translator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Nikita Yaschenko aka Nafanya on 27.09.14.
 * Modified by Aydar Gizatullin aka lightning95 on 28.09.14.
 */

public class ResultActivity extends Activity {
    private static final String TAG = "ResultActivity";

    private GridView mGridView;
    private TextView mTranslationTextView;
    private ArrayList<String> mItems = null;
    private LruCache<String, Bitmap> mCache = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String translateString = intent.getStringExtra(Helper.EXTRA_TRANSLATE);

        setupViews();
        setupAdapter();
        setupCache();

        translate(translateString);
        loadImages(translateString);

        /*
        ArrayList<String> urls = null;
        try {
            urls = new FetchUrlsTask().execute(translateString).get();
            mItems = new LoadImageTask().execute(urls).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        setupAdapter();
        */
    }

    private void translate(String string) {
        new TranslateTask(this, mTranslationTextView).execute(string);
    }

    private void loadImages(String string) {
        new FetchUrlsTask().execute(string);
    }

    private void setupViews() {
        mGridView = (GridView) findViewById(R.id.grid_view);
        mTranslationTextView = (TextView) findViewById(R.id.text_translation);
    }

    private void setupAdapter() {
        if (mItems == null) {
            mGridView.setAdapter(null);
        } else {
            mGridView.setAdapter(new ImageAdapter(this, mItems));
        }
    }

    private void setupCache() {
        final int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;
        final int cacheSize = maxMemory / 8;

        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return (bitmap.getRowBytes() * bitmap.getHeight()) / 1024;
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (getCachedBitmap(url) == null) {
            mCache.put(url, bitmap);
            //Log.i(TAG, "Bitmap size in kb: " +
            //        (bitmap.getRowBytes() * bitmap.getHeight()) / 1024);
        }
    }

    private Bitmap getCachedBitmap(String url) {
        if (url != null) {
            return mCache.get(url);
        }
        return null;
    }

    private void setImage(ImageView imageView, String url) {
        Bitmap bitmap = getCachedBitmap(url);
        if (bitmap == null) {
            new LoadImageTask(getApplicationContext(), imageView).execute(url);
        } else {
            imageView.setImageBitmap(bitmap);
        }
    }

    private class FetchUrlsTask extends AsyncTask<String, Void, ArrayList<String>> {
        private static final String API_KEY = "e4f23d12ecff2f809c62c1f68365f437";

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> ret = new ArrayList<String>();
            String uri = Uri.parse("https://www.flickr.com/services/rest")
                    .buildUpon()
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("method", "flickr.photos.search")
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("text", params[0])
                    .appendQueryParameter("per_page", "10")
                    .build().toString();
            String response = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(uri);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                response = EntityUtils.toString(httpEntity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String jsonData = response.substring(response.indexOf('{'), response.length() - 1);
            try {
                JSONObject root = new JSONObject(jsonData);
                JSONObject tmp = root.getJSONObject("photos");
                JSONArray photos = tmp.getJSONArray("photo");
                for (int i = 0; i < photos.length(); i++) {
                    String id = photos.getJSONObject(i).getString("id");
                    String server = photos.getJSONObject(i).getString("server");
                    String secret = photos.getJSONObject(i).getString("secret");
                    String farm = photos.getJSONObject(i).getString("farm");
                    String url = String.format(
                            "https://farm%s.staticflickr.com/%s/%s_%s_t.jpg",
                            farm, server, id, secret
                    );
                    Log.d("TAG", url);
                    ret.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ret;
        }

        @Override
        protected void onPostExecute(ArrayList<String> urls) {
            mItems = urls;
            setupAdapter();
        }
    }

    private class TranslateTask extends AsyncTask<String, Void, String> {
        private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
        private static final String API_KEY = "trnsl.1.1.20140927T210341Z.e8c195ae06daeb13.b1522070ee0c890f7acfc10239c5fe0cb78399e1";
        private static final String LANGUAGES = "en-ru";

        private final TextView textView;
        private final Context context;

        public TranslateTask(Context context, TextView textView) {
            this.textView = textView;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                StringBuilder sb = new StringBuilder(params[0]);
                for (int i = 1; i < params.length;++i){
                    sb.append("+").append(params[i]);
                }
                String text = sb.toString();

                URL requestUrl = new URL(API_URL + "key=" + API_KEY + "&text=" + text + "&lang=" + LANGUAGES);

                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));

                    String returned = in.readLine();
                    Log.d("response code", returned);
                    JSONObject object = new JSONObject(returned);
                    JSONArray array = (JSONArray) object.get("text");

                    return array.join(" ").replaceAll("\"", "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result == null) {
                Toast.makeText(
                        context,
                        Helper.ERROR_TRANSLATION,
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                textView.setText(result);
            }
        }
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final Context context;
        private final ImageView imageView;
        private String imageUrl;

        public LoadImageTask(Context context, ImageView imageView) {
            this.context = context;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            URL url = null;
            try {
                url = new URL(imageUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                addBitmapToCache(imageUrl, bitmap);
            }
            imageView.setImageBitmap(bitmap);
        }
    }

    private class ImageAdapter extends ArrayAdapter {
        private ArrayList<String> mItems;

        public ImageAdapter(Context context, ArrayList<String> items) {
            super(context, 0, items);
            mItems = items;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_image, parent, false);

            }

            String url = mItems.get(position);
            ImageView imageView = (ImageView) view.findViewById(R.id.item_imageView);
            setImage(imageView, url);
            //new LoadImageTask(getContext(), imageView).execute(url);

            return view;
        }
    }

}