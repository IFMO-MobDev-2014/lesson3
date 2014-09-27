package com.t0006.lesson3;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by itegulov on 27.09.14.
 */
public class TranslationLoaderTask extends AsyncTask<String, WordTranslation, Void> {
    private final String LOG_TAG = TranslationLoaderTask.class.getSimpleName();
    private final String API_KEY = "trnsl.1.1.20140924T073719Z.e1524a5f158bbc5d.630ee347b319b5ca49a9d1923a1d3c12d818a58a";
    private final String[] languages = {"ru", "es", "fr", "uk", "be", "ka", "es", "it", "fi", "de", "ro", "pl", "tr", "cs", "sv"};
    private TranslationsAdapter adapter;

    public TranslationLoaderTask(TranslationsAdapter adapter) {
        this.adapter = adapter;
    }

    private String getTranslateFromJSON(String translateJSONString)
            throws JSONException {
        final String JSON_TEXT = "text";
        Log.d(LOG_TAG, translateJSONString);
        JSONObject translateJSON = new JSONObject(translateJSONString);
        JSONArray textArray = translateJSON.getJSONArray(JSON_TEXT);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < textArray.length(); i++) {
            sb.append(textArray.get(i)).append(" ");
        }
        return sb.toString();
    }

    @Override
    protected Void doInBackground(String... params) {
        publishProgress(new WordTranslation("en", params[0]));
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String translateJSONString = null;
        for (String language : languages) {
            try {
                final String TRANSLATE_BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
                final String TEXT_PARAM = "text";
                final String LANG_PARAM = "lang";
                final String API_KEY_PARAM = "key";
                Uri builtUri = Uri.parse(TRANSLATE_BASE_URL).buildUpon().appendQueryParameter(API_KEY_PARAM, API_KEY).appendQueryParameter(TEXT_PARAM, params[0]).appendQueryParameter(LANG_PARAM, language).build();
                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                translateJSONString = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error reading stream", e);
                translateJSONString = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                publishProgress(new WordTranslation(language, getTranslateFromJSON(translateJSONString)));
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        adapter.reset();
    }

    @Override
    protected void onProgressUpdate(WordTranslation... values) {
        for (WordTranslation translation : values)
            adapter.addItem(translation);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapter.onFinishedLoading();
    }
}
