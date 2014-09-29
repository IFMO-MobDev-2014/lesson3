package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Лоскутов Игнат (2538), Некрасов Дмитрий (2538), Шолохов Алексей (2536)
 */
public class DisplayActivity extends Activity {

    private static int imagesDrawn = 0;
    private GridLayout layout;
    private GetImageTask getImageTask;
    private ImageLoadingTask imageLoadingTask;
    private boolean destroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);
        layout = (GridLayout)findViewById(R.id.display_activity);
        Intent thisIntent = getIntent();
        String translation = thisIntent.getStringExtra("translation");
        String word = thisIntent.getStringExtra("word");
        TextView translationView = (TextView)findViewById(R.id.translationView);
        translationView.setText(translation);
        imageLoadingTask = new ImageLoadingTask();
        imageLoadingTask.execute(word);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyed = true;
        if(imageLoadingTask != null) {
            imageLoadingTask.abort();
        }
        if(getImageTask != null) {
            getImageTask.abort();
        }
    }

    //Get images from Flickr
    private class ImageLoadingTask extends AsyncTask<String, Void, String[]> {
        private HttpGet httpGet = null;
        void abort() {
            if(httpGet != null)
                httpGet.abort();
            this.cancel(true);
        }
        @Override
        protected String[] doInBackground(String... word) {
            if(isCancelled())
                return null;

            try {
                String url = ("https://api.flickr.com/services/rest/?method=flickr.photos.search&per_page=10&format=json&api_key=5c7c3f396db79009d2baecd139398d98&text=" + URLEncoder.encode(word[0], "utf-8"));
                DefaultHttpClient httpClient = new DefaultHttpClient();
                httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                String response = EntityUtils.toString(httpResponse.getEntity());
                response = response.substring(response.indexOf('{'), response.lastIndexOf(')'));
                JSONObject jsonObject = new JSONObject(response);

                JSONArray responses = jsonObject.getJSONObject("photos").getJSONArray("photo");
                String[] results = new String[responses.length()];
                for (int i = 0; i < results.length; i++) {
                    JSONObject picture = responses.getJSONObject(i);
                    results[i] = String.format("https://farm%d.staticflickr.com/%s/%s_%s_z.jpg",
                            picture.getInt("farm"),
                            picture.getString("server"),
                            picture.getString("id"),
                            picture.getString("secret")
                    );
                }
                return results;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String[] imageUrls) {
            if(imageUrls != null) {
                for (String imageUrl : imageUrls) {
                    if(destroyed)
                        break;
                    getImageTask = new GetImageTask();
                    getImageTask.execute(imageUrl);
                }
            } else {
                new AlertDialog.Builder(DisplayActivity.this)
                    .setTitle("Ошибка")
                    .setMessage("Возникли проблемы с поиском изображений")
                    .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
                return;
            }
        }
    }

    private class GetImageTask extends AsyncTask<String, Void, Drawable> {
        private InputStream is = null;
        void abort() {
            try {
                is.close();
            } catch(Exception e) {}
            this.cancel(true);
        }
        @Override
        protected Drawable doInBackground(String... word) {
            if(isCancelled() || destroyed)
                return null;

            try {
                is = (InputStream) new URL(word[0]).getContent();
                Drawable img = Drawable.createFromStream(is, "");
                is.close();
                return img;
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(Drawable d) {
            if (!destroyed && d == null) {
                new AlertDialog.Builder(DisplayActivity.this)
                    .setTitle("Ошибка")
                    .setMessage("Возникли проблемы с загрузкой изображений")
                    .setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
                return;
            }
            ImageView imgView = new ImageView(getApplicationContext());
            imgView.setScaleType(ImageView.ScaleType.CENTER);
            imgView.setImageDrawable(d);
            GridLayout.Spec rowSpec = GridLayout.spec(imagesDrawn / 2, GridLayout.CENTER);
            GridLayout.Spec colSpec = GridLayout.spec(imagesDrawn % 2, GridLayout.CENTER);
            layout.addView(imgView, new GridLayout.LayoutParams(rowSpec, colSpec));
            imagesDrawn++;
        }
    }
}
