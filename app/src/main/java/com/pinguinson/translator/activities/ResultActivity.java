package com.pinguinson.translator.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinguinson.translator.R;
import com.pinguinson.translator.models.Photo;
import com.pinguinson.translator.tasks.*;

import java.util.ArrayList;

/**
 * Created by pinguinson on 12.10.2014.
 */
public class ResultActivity extends Activity {
    private ArrayList<Photo> downloadedPhotos;
    private int currentImage;
    private int totalImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String query = getIntent().getStringExtra(SearchActivity.QUERY);
        TextView textView = (TextView) findViewById(R.id.query);
        textView.setText(query);
        downloadedPhotos = new ArrayList<Photo>();
        new TranslationTask(ResultActivity.this).execute(query);
    }

    public void onTranslationFinished(String translation) {
        TextView textView = (TextView) findViewById(R.id.translation);
        textView.setText(translation);
        new FiveHundredSearchTask(ResultActivity.this).execute(translation);
    }

    public void onSearchFinished(ArrayList<Photo> photos) {
        for (Photo photo : photos) {
            new DownloadImageTask(ResultActivity.this).execute(photo);
        }
    }


    public void onImageDownloaded(Photo photo) {
        downloadedPhotos.add(photo);
        totalImages = downloadedPhotos.size();
        if (downloadedPhotos.size() == 1) {
            ImageView image = (ImageView) findViewById(R.id.image);
            image.setImageDrawable(downloadedPhotos.get(0).getDrawable());
            currentImage = 0;
        }
        TextView counter = (TextView) findViewById(R.id.counter);
        counter.setText(Integer.toString(currentImage + 1) + "/" + Integer.toString(totalImages));
    }

    public void nextImage(View view) {
        ImageView image = (ImageView) findViewById(R.id.image);
        currentImage = (currentImage + 1) % downloadedPhotos.size();
        image.setImageDrawable(downloadedPhotos.get(currentImage).getDrawable());
        TextView counter = (TextView) findViewById(R.id.counter);
        counter.setText(Integer.toString(currentImage + 1) + "/" + Integer.toString(totalImages));
    }
}
