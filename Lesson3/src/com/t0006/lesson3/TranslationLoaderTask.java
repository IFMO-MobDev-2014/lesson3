package com.t0006.lesson3;

import android.os.AsyncTask;

/**
 * Created by dimatomp on 27.09.14.
 */
public class TranslationLoaderTask extends AsyncTask<String, WordTranslation, Void> {
    private TranslationsAdapter adapter;

    public TranslationLoaderTask(TranslationsAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            Thread.sleep(2000);
            publishProgress(new WordTranslation("ru", "интерполяция"));
            Thread.sleep(4000);
            publishProgress(new WordTranslation("en", "interpolation"));
        } catch (InterruptedException ignore) {
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
