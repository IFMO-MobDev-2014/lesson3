package ru.ifmo.ctddev.soloveva.translator;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import ru.ifmo.ctddev.katunina.translator.TranslateActivity;
import ru.ifmo.ctddev.soloveva.translator.api.BingImageSearchService;


public class MainActivity extends Activity {
    private static final String ACCOUNT_KEY = "PLazKWUyzOiy9LUSy7t4RL/+DNueujtQ7HXri88/t0w";

    private void findViewsByIds() {
        tvResult = ((TextView) findViewById(R.id.tvResult));
        lytRoot = ((LinearLayout) findViewById(R.id.lytRoot));
    }

    TextView tvResult;
    LinearLayout lytRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsByIds();
        tvResult.setText(getIntent().getStringExtra(TranslateActivity.KEY_TO_SB));
        new SearchTask().execute(getIntent().getStringExtra(TranslateActivity.KEY_TO_WORD));
    }

    private class SearchTask extends AsyncTask<String, Void, List<? extends ImageData>> {
        @Override
        protected List<? extends ImageData> doInBackground(String... query) {
            try {
                BingImageSearchService service = new BingImageSearchService(ACCOUNT_KEY);
                try {
                    return service.search(query[0], 10);
                } finally {
                    service.close();
                }
            } catch (IOException e) {
                Log.e("TRNSLT", "Search failed", e);
                Toast.makeText(MainActivity.this, R.string.error_no_internet, Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<? extends ImageData> images) {
            ListView view = new ListView(MainActivity.this);
            view.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            view.setAdapter(new ImagesAdapter(MainActivity.this, images));
            lytRoot.addView(view);
        }
    }
}
