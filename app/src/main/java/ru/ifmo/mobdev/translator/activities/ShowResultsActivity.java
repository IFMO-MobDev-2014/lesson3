package ru.ifmo.mobdev.translator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import ru.ifmo.mobdev.translator.ImageGridAdapter;
import ru.ifmo.mobdev.translator.R;
import ru.ifmo.mobdev.translator.models.Picture;
import ru.ifmo.mobdev.translator.tasks.DownloadImageTask;
import ru.ifmo.mobdev.translator.tasks.FindImagesTask;

/**
 * Created by sugakandrey on 19.09.14.
 */

public class ShowResultsActivity extends Activity {
    ArrayList<Picture> loadedPictures = new ArrayList<Picture>();
    public static final String FULLSCREEN_LINK = "ru.ifmo.mobdev.translator.fullscreenLink";
    private Intent fullscreen;
    private int loadedImageCounter;
    private int placeHoldersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.show_results_activity);
        fullscreen = new Intent(this, LoadFullscreenActivity.class);
        final Intent caller = getIntent();
        final TextView translationField = (TextView) findViewById(R.id.translationField);
        String translation = caller.getStringExtra(MainActivity.TRANSLATED_INPUT);
        if (translation == null) {
            translationField.setText(R.string.translationError);
        } else {
            translationField.setText(caller.getStringExtra(MainActivity.TRANSLATED_INPUT));
        }
        new FindImagesTask(this).execute(caller.getStringExtra(MainActivity.INPUT));
        GridView view = (GridView) findViewById(R.id.picsGv);
        ImageGridAdapter adapter = new ImageGridAdapter(this);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                URL link = null;
                try {
                    link = new URL(loadedPictures.get(i).getUrl());
                } catch (MalformedURLException ignore) {
                }
                fullscreen.putExtra(FULLSCREEN_LINK, link);
                startActivity(fullscreen);
            }
        });
    }

    public void onImagesFound(ArrayList<Picture> pictures) {
        loadedPictures = pictures;
        for (Picture picture : pictures) {
            DownloadImageTask downloadImageTask = new DownloadImageTask(this);
            GridView view = (GridView) findViewById(R.id.picsGv);
            ImageGridAdapter adapter = (ImageGridAdapter) view.getAdapter();
            Picture stub = new Picture();
            adapter.addPicture(stub, placeHoldersCount);
            placeHoldersCount++;
            downloadImageTask.execute(picture);
        }
    }

    public void onImageLoaded(Picture picture) {
        GridView view = (GridView) findViewById(R.id.picsGv);
        ImageGridAdapter adapter = (ImageGridAdapter) view.getAdapter();
        adapter.addPicture(picture, loadedImageCounter);
        loadedImageCounter++;
    }

    public void onImageLoadingError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ShowResultsActivity.this, "Error loading pictures. " +
                        "Please check your internet connection.", Toast.LENGTH_LONG).show();
                ShowResultsActivity.this.finish();
            }
        });
    }
}
