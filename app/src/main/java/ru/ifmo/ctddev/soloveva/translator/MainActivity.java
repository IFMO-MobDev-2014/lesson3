package ru.ifmo.ctddev.soloveva.translator;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.List;

import ru.ifmo.ctddev.soloveva.translator.api.BingImageSearchService;


public class MainActivity extends Activity {
    private static final String ACCOUNT_KEY = "PLazKWUyzOiy9LUSy7t4RL/+DNueujtQ7HXri88/t0w";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProgressBar progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(true);
        setContentView(progressBar);
        new SearchTask().execute("wolf");
    }

    private class SearchTask extends AsyncTask<String, Void, List<? extends ImageData>> {
        @Override
        protected List<? extends ImageData> doInBackground(String... query) {
            try {
                BingImageSearchService service = new BingImageSearchService(ACCOUNT_KEY);
                try {
                    return service.search(query[0], 10, 0, 0);
                } finally {
                    service.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<? extends ImageData> images) {
            ListView view = new ListView(MainActivity.this);
            view.setAdapter(new ImagesAdapter(MainActivity.this, images));
            setContentView(view);
        }
    }
}
