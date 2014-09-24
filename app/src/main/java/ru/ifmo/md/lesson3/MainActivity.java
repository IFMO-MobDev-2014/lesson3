package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.Void;
import java.net.URLEncoder;


public class MainActivity extends Activity {

    private final static String translatorPrefix = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20140924T073928Z.75f4072f7ba0940a.0e25b6e1b08d1c03dfb34d22a4055d8118e44d77&lang=en-ru&text=";
    private final static String imageLoaderPrefix = "http://api.bing.net/json.aspx?AppId=Dg5LmYsY18A3Dy+pAb+LISIlcwfo2G2NOjkAR1+CdYw&Query=";
    private final static String imageLoaderPostfix = "&Sources=Image";
    private EditText editText;
    private TranslationTask translationTask;
    private ImageLoadingTask imageLoadingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
    }

    public void onClick(View view) {
        translationTask = new TranslationTask();
        translationTask.execute(editText.getText().toString());
        try {
            imageLoadingTask = new ImageLoadingTask();
            imageLoadingTask.execute(translationTask.get());
            //get the result URLs using imageLoadingTask.get()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class TranslationTask extends AsyncTask<String, Void, String> {
        @Override
        protected Void doInBackground(String... word) {
            try {
                String url = translatorPrefix + URLEncoder.encode(word[0], "utf-8");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
                String response = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject = new JSONObject(response);
                //Log.i("lal", jsonObject.getJSONArray("text").getString(0));
                return jsonObject.getJSONArray("text").getString(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class ImageLoadingTask extends AsyncTask<String, Void, String> {
        @Override
        protected Void doInBackground(String... word) {
            try {
                String url = imageLoaderPrefix + URLEncoder.encode(word[0], "utf-8");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
                String response = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject = new JSONObject(response);
                JSONObject[] responses = jsonObject.getJSONArray("SearchResponse").getJSONArray("Image").getJSONArray("Results");
                String[] results = new String[responses.length];
                for (int i = 0; i < responses.length; i++) {
                    results[i] = responses[i].getJSONArray("Url").getString(0);
                }
                return results;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
