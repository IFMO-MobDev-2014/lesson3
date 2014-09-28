package ru.ifmo.mobdev.translator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_results_activity);
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
    }

    public void onImagesFound(ArrayList<Picture> pictures) {
        for (Picture picture : pictures) {
            DownloadImageTask downloadImageTask = new DownloadImageTask(this);
            downloadImageTask.execute(picture);
        }
    }

    public void onImageLoaded(Picture picture) {
        GridView view = (GridView) findViewById(R.id.picsGv);
        ImageGridAdapter adapter = (ImageGridAdapter) view.getAdapter();
        adapter.addPicture(picture);
    }
}
