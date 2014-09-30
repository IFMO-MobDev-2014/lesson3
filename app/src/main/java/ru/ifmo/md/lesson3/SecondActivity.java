package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


public class SecondActivity extends Activity {

    public static ArrayList<Bitmap> images;
    public static ImageView imageView;
    public static TextView textView;
    public static String urls[] = new String[10];
    public static int i = 0,
            loaded = 0;
    public static String string,
            ret;
    public static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = 0;
        string = getIntent().getStringExtra("text");
        setContentView(R.layout.layout2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setClickable(false);
        textView = (TextView) findViewById(R.id.textView);
        try {
            Thread thread = new Thread(new Translate(string));
            thread.start();
            Thread thread2 = new Thread(new ImageSearch(string));
            thread2.start();
            thread.join();
            textView.setText(ret);
            thread2.join();
            imageView.setClickable(true);
            images = new ArrayList<Bitmap>();
            new LoadImageTask().execute(SecondActivity.urls);
            imageView.setImageResource(R.drawable.img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nextImage(View view) {
        if (images.size() == 0)
            return;
        imageView.setImageBitmap(images.get(i));
        i = (i + 1) % images.size();
    }

    public void back(View view) {

    }
}