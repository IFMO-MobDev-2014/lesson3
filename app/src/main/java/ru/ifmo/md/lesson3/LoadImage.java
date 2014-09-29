package ru.ifmo.md.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by ComradeK on 29/09/2014.
 */
public class LoadImage extends AsyncTask<String, String, Bitmap> {
    public Bitmap doInBackground(String... args) {
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
