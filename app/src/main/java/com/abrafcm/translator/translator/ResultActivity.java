package com.abrafcm.translator.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Nikita Yaschenko aka Nafanya on 27.09.14.
 * Modified by Aydar Gizatullin aka lightning95 on 28.09.14.
 */

public class ResultActivity extends Activity {
    private static final String TAG = "ResultActivity";

    private GridView mGridView;
    private TextView mTranslationTextView;
    private ArrayList<ImageItem> mItems = null;
    private IImagesProvider mImagesProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String translateString = intent.getStringExtra(Helper.EXTRA_TRANSLATE);

        setupViews();

        mImagesProvider = new FakeImagesProvider(this);
        mItems = mImagesProvider.getItems(translateString);

        String resString = "";
        Translator translator = new Translator();
        try {
            resString = translator.execute(translateString).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setupAdapter();
        mTranslationTextView.setText(resString);
    }

    private void setupViews() {
        mGridView = (GridView) findViewById(R.id.grid_view);
        mTranslationTextView = (TextView) findViewById(R.id.text_translation);
    }

    private void setupAdapter() {
        if (mItems == null) {
            mGridView.setAdapter(null);
        } else {
            mGridView.setAdapter(new ImageAdapter(this, mItems, mImagesProvider));
        }
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

    private class Translator extends AsyncTask<String, Void, String> {
        private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
        private static final String API_KEY = "trnsl.1.1.20140927T210341Z.e8c195ae06daeb13.b1522070ee0c890f7acfc10239c5fe0cb78399e1";
        private static final String LANGUAGES = "en-ru";

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

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                String returned = in.readLine();
                JSONObject object = new JSONObject(returned);
                JSONArray array = (JSONArray) object.get("text");

                return array.join(" ").replaceAll("\"", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}