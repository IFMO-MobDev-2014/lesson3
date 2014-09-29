package ru.ifmo.md.lesson3;

/**
 * Created by 107476 on 25.09.2014.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MySecondActivity extends Activity {
    public ArrayList<Bitmap> images;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        images = new ArrayList<Bitmap>();
        imageView = (ImageView) findViewById(R.id.imageView);
        FindAndDownloadImagesTask faditask = new FindAndDownloadImagesTask();
        faditask.execute("football");
        try {
            images = faditask.get();
        } catch (Exception e) {
        }
        imageView.setImageBitmap(images.get(0));
    }
}
