package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryActivity extends Activity {
    public static final int N = 10;
    private int width, height;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        width = MainActivity.getScreenSize(this).x;
        height = MainActivity.getScreenSize(this).y;

        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_QUERY);
        TextView translationView = (TextView) findViewById(R.id.translation);
        translationView.setText(query + "\n");
        translationView.setMovementMethod(LinkMovementMethod.getInstance());
        GridView imageGrid = ((GridView) findViewById(R.id.imageGrid));
        imageGrid.setLayoutParams(new LinearLayout.LayoutParams(width, 3 * height / 4));

        if (InternetChecker.isOnline(this)) {
            TranslateTask tt = new TranslateTask(query, (TextView) findViewById(R.id.translation));
            tt.execute();
            ImageAdapter ia = new ImageAdapter(this);
            imageGrid.setAdapter(ia);
            FetchTask ft = new FetchTask(query, ia);
            ft.execute();
            ia.notifyDataSetChanged();
        }
        else {
            Toast t = Toast.makeText(this, getString(R.string.noInternet), Toast.LENGTH_LONG);
            t.show();
        }
    }

    class FetchTask extends AsyncTask<Void, Void, Void> {
        String query;
        ImageAdapter imageAdapter;

        public FetchTask(String q, ImageAdapter ia) {
            query = q;
            imageAdapter = ia;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ImageFetcher fetcher = new ImageFetcher(N, getResources());
            fetcher.setImages(query, imageAdapter);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
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
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                result = Translator.translate(query);
            }
            catch (Exception e) {
                e.printStackTrace();
                result = null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (this.result != null)
                output.setText(Html.fromHtml(query + " -> " + this.result + "<br> " + getString(R.string.ad)));
        }
    }
}
