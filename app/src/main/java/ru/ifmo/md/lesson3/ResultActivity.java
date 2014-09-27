package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class ResultActivity extends Activity {

    TextView textView;
    GridView gridView;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = (TextView) findViewById(R.id.textView);

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new ImageAdapter(this);
        gridView.setAdapter(adapter);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_QUERY);
        new FindTranslationTask().execute(message);
        new FindImagesTask().execute(message);
    }

    private class FindTranslationTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String[] strings) {
            String input = strings[0];
            Translator translator;
            if (input.matches("[А-я\\s\\d]+"))
                translator = new Translator(Translator.TranslateDirection.RussianToEnglish);
            else translator = new Translator(Translator.TranslateDirection.EnglishToRussian);
            return translator.translate(input);
        }

        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }

    private class FindImagesTask extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... strings) {
            String urlTemplate = "http://www.joomlaworks.net/images/demos/galleries/abstract/%d.jpg"; // dummy
            try {
                for (int i=1; i<=10; i++) {
                    URL url = new URL(String.format(urlTemplate, i));
                    new ShowImageTask().execute(url);
                }
            } catch (Exception e) {
                Log.e("error", "", e);
            }
            return null;
        }
    }


    private class ShowImageTask extends AsyncTask<URL, Void, Drawable> {
        protected Drawable doInBackground(URL... urls) {
            URL url = urls[0];
            try {
                InputStream content = (InputStream)url.getContent();
                Drawable picture = Drawable.createFromStream(content , "src");
                return picture;
            } catch (Exception e) {
                Log.e("error", "", e);
            }
            return null;
        }

        protected void onPostExecute(Drawable picture) {
            adapter.add(picture);
            adapter.notifyDataSetChanged();
        }
    }
}

