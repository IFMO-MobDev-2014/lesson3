package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.view.ViewPager;

public class GalleryActivity extends Activity {
    public static final int N = 10;
    public static final int imgPerPage = 6;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);
        TranslateTask tt = new TranslateTask(query, (TextView)findViewById(R.id.translation));
        tt.execute();
        FetchTask ft = new FetchTask(query, (ViewPager)findViewById(R.id.myGalleryView), this);
        ft.execute();
    }

    class FetchTask extends AsyncTask<Void, Void, Void> {
        String result;
        String query;
        ViewPager output;
        Activity activity;
        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);

        public FetchTask(String q, ViewPager out, Activity a) {
            query = q;
            output = out;
            activity = a;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            linlaHeaderProgress.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            ImageFetcher fetcher = new ImageFetcher(N, getResources());
            Bitmap[] images = fetcher.fetch(query);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            linlaHeaderProgress.setVisibility(View.GONE);
        }
    }

    class TranslateTask extends AsyncTask<Void, Void, Void> {
        String result;
        String query;
        TextView output;

        public TranslateTask(String q, TextView out) {
            query = q;
            output = out;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            output.setText(query + "\n" + R.string.ad);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Translator translator = new Translator();
            result = translator.translate(query);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            output.setText(query + " -> " + result + "\n " + R.string.ad);
        }
    }
}
