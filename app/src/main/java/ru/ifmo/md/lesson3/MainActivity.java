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

import java.net.URLEncoder;


public class MainActivity extends Activity {

    private final static String apiPrefix="https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20140924T073928Z.75f4072f7ba0940a.0e25b6e1b08d1c03dfb34d22a4055d8118e44d77&lang=en-ru&text=";
    private EditText editText;
    private TranslationTask translationTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
    }

    public void onClick(View view) {
        translationTask = new TranslationTask();
        translationTask.execute(editText.getText().toString());
    }

    class TranslationTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... word) {
            try {
                String url = apiPrefix + URLEncoder.encode(word[0], "utf-8");
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
                String response = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject = new JSONObject(response);
                Log.i("lal", jsonObject.getJSONArray("text").getString(0));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
