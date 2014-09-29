package ru.ifmo.md.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ComradeK on 29/09/2014.
 */
public class LoadImage implements Runnable{
    public Bitmap load(String url) throws IOException {

            return BitmapFactory.decodeStream((InputStream) new URL(url).getContent());


    }
    public void run() {
        try {
            MainActivity.images[MainActivity.i] = load(MainActivity.urls[MainActivity.i]);
            MainActivity.imageView.setImageBitmap(MainActivity.images[MainActivity.i]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
