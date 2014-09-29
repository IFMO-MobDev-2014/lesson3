package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class ResultActivity extends Activity {
    TextView textView;
    GridView gridView;
    DoubleImageAdapter adapter;
    ShowImageTask[] showImageTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        new FindImagesTask(this, 50).execute(intent.getStringExtra(MainActivity.MAIN_QUERY));
        textView = (TextView) findViewById(R.id.textView);
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new DoubleImageAdapter(this);
        gridView.setAdapter(adapter);
        adapter.setAll(50);
        updateGridColumns();
        textView.setText(intent.getStringExtra(MainActivity.TRANSLATOR_RESPONSE));
    }

    protected void onImagesUPLsRecieved(ArrayList<URL[]> imageURLs) {
        showImageTasks = new ShowImageTask[imageURLs.size()];
        for (int i = 0; i < imageURLs.size(); i++) {
            URL[] a = imageURLs.get(i);
            Log.i("Loading picture:", a[0].toString());
            showImageTasks[i] = new ShowImageTask(adapter);
            showImageTasks[i].execute(a[0], a[1]);
        }
    }

    protected void onFullRequest(String url) {
        Intent intent = new Intent(this, FullImageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(DoubleImageAdapter.FULL_PICTURE, url);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void updateGridColumns(){
        int k = adapter.updateCellHeight();
        gridView.setAdapter(adapter);
        gridView.setColumnWidth(adapter.cellHeight);
        gridView.setNumColumns(k);
        gridView.invalidateViews();
        gridView.invalidate();
        gridView.refreshDrawableState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            updateGridColumns();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            updateGridColumns();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        for (ShowImageTask task : showImageTasks) {
//            if (!task.isCancelled()) task.cancel(true);
//        }
    }
}

