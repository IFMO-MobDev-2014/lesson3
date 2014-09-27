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
import java.util.ArrayList;
import java.util.Map;

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
        textView.setText(intent.getStringExtra(MainActivity.TRANSLATOR_RESPONSE));
        new FindImagesTask(this, 10).execute();
    }

    protected void onImagesUPLsRecieved(ArrayList<URL> imageURLs) {
        for (URL a : imageURLs) {
            new ShowImageTask(adapter).execute(a);
        }
    }
}

