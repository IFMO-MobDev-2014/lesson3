package com.pinguinson.translator.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pinguinson.translator.activities.ResultActivity;
import com.pinguinson.translator.api.YandexQuery;

import org.json.JSONObject;

/**
 * Created by pinguinson on 12.10.2014.
 */
public class TranslationTask extends AsyncTask<String, Void, String> {
    ResultActivity activity;

    public static final String API_KEY = "trnsl.1.1.20141012T002112Z.ac5b7fb8c11602cb.e799fa01a22992f5fcbfe7e5b8f08726d4386a24";

    public TranslationTask(ResultActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        YandexQuery yandexQuery = new YandexQuery(API_KEY);
        String query = strings[0];
        yandexQuery.addParameter("lang", "en-ru");
        yandexQuery.addParameter("text", query);
        try {
            JSONObject searchResult = yandexQuery.get();
            String translation = searchResult.optString("text");
            translation = translation.substring(2, translation.length() - 2);
            return translation;
        } catch (Exception e) {
            Log.e("FlickrSeacrhTask", "Search failed.", e);
        }
        return "error";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        activity.onTranslationFinished(s);
    }
}
