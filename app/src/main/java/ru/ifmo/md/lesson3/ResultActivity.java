package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

//TODO: add picture caching
public class ResultActivity extends Activity {
    TextView textView;
    GridView gridView;
    DoubleImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = (TextView) findViewById(R.id.textView);
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new DoubleImageAdapter(this);
        gridView.setAdapter(adapter);
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra(MainActivity.TRANSLATOR_RESPONSE));
        new FindImagesTask(this, 10).execute(intent.getStringExtra(MainActivity.MAIN_QUERY));
    }

    protected void onImagesUPLsRecieved(ArrayList<URL[]> imageURLs) {
        for (URL[] a : imageURLs) {
            Log.i("Loading picture:", a[0].toString());
            new ShowImageTask(adapter).execute(a[0], a[1]);
        }
    }

    protected void onFullRequest(String url) {
        Intent intent = new Intent(this, FullImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DoubleImageAdapter.FULL_PICTURE, url);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

