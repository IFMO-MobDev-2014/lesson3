package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GalleryActivity extends Activity {
    public static final int N = 10;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);
        TranslateTask tt = new TranslateTask(query, (TextView)findViewById(R.id.translation));
        tt.execute();
        ImageAdapter ia = new ImageAdapter(this);
        ((GridView)findViewById(R.id.imageGrid)).setAdapter(ia);
        FetchTask ft = new FetchTask(query, ia);
        ft.execute();
        ia.notifyDataSetChanged();
    }

    class FetchTask extends AsyncTask<Void, Void, Void> {
        String query;
        ImageAdapter imageAdapter;
        LinearLayout linlaHeaderProgress = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        GridView imageGrid = (GridView) findViewById(R.id.imageGrid);

        public FetchTask(String q, ImageAdapter ia) {
            query = q;
            imageAdapter = ia;
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
            imageAdapter.setImages(images);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            linlaHeaderProgress.setVisibility(View.GONE);
            imageGrid.setVisibility(View.VISIBLE);

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
            output.setText(query + "\n");
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
            if (this.result != null)
                output.setText(query + " -> " + this.result + "\n " + getString(R.string.ad));
        }
    }
}
