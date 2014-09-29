package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.wifi.WifiConfiguration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

public class ResultActivity extends Activity {
    public static final int PICTURES_TO_LOAD = 10; // must be > 0
    private static class Pair<A, B> {
        private A a;
        private B b;
        private Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }
    }

    TextView textView;
    GridView gridView;
    DoubleImageAdapter adapter;
    ArrayList<Pair<ShowImageTask, URL[]>> showImageTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        new FindImagesTask(this, PICTURES_TO_LOAD).execute(intent.getStringExtra(MainActivity.MAIN_QUERY));
        textView = (TextView) findViewById(R.id.textView);
        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new DoubleImageAdapter(this);
        gridView.setAdapter(adapter);
        adapter.setAll(PICTURES_TO_LOAD);
        updateGridColumns();
        textView.setText(intent.getStringExtra(MainActivity.TRANSLATOR_RESPONSE));
    }

    protected void onImagesUPLsRecieved(ArrayList<URL[]> imageURLs) {
        showImageTasks = new ArrayList<Pair<ShowImageTask, URL[]>>(imageURLs.size());
        for (int i = 0; i < imageURLs.size(); i++) {
            URL[] a = imageURLs.get(i);
            Log.i("Loading picture:", a[0].toString());
            showImageTasks.add(i, new Pair<ShowImageTask, URL[]>(new ShowImageTask(adapter), a));
            showImageTasks.get(i).a.execute(a[0], a[1]);
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
        if (showImageTasks != null && showImageTasks.size() > 0) {
            ArrayList<Pair<ShowImageTask, URL[]>> tasksCurr = new ArrayList<Pair<ShowImageTask, URL[]>>();
            for (Pair<ShowImageTask, URL[]> showImageTask : showImageTasks) {
                if (showImageTask.a.getStatus() != AsyncTask.Status.FINISHED) {
                    showImageTask.a.cancel(true);
                    tasksCurr.add(new Pair<ShowImageTask, URL[]>(new ShowImageTask(adapter), showImageTask.b));
                }
            }
            showImageTasks = tasksCurr;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (showImageTasks != null && showImageTasks.size() > 0) {
            for (Pair<ShowImageTask, URL[]> showImageTask : showImageTasks) {
                if (showImageTask.a.getStatus() != AsyncTask.Status.FINISHED) showImageTask.a.execute(showImageTask.b);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

