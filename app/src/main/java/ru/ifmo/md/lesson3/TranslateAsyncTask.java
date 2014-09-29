package ru.ifmo.md.lesson3;

import android.os.AsyncTask;

/**
 * Created by Kirill on 29.09.2014.
 */
public class TranslateAsyncTask extends AsyncTask<String, Void, String> {

    MainActivity activity;

    public TranslateAsyncTask(MainActivity mainactivity) {
        activity = mainactivity;
    }

    protected String doInBackground(String... params) {
        String result = Translate.translate(params[0]);
        return result;
    }

    protected void onPostExecute(String result) {
        activity.onTranslateResponse(result);

    }


}
