package ru.ifmo.md.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ComradeK on 29/09/2014.
 */
public class LoadImageTask extends AsyncTask<String, Integer, ArrayList<Bitmap>> {
    public ArrayList<Bitmap> doInBackground(String... params) {
        ArrayList<Bitmap> ret = new ArrayList<Bitmap>();
        for (int i = 0; i < params.length; i++) {
            if (isCancelled()) break;
            SecondActivity.images.add(downloadImage(params[i]));
            publishProgress(i);
        }
        return ret;
    }

    protected void onProgressUpdate(Integer... values) {
        SecondActivity.progressBar.setProgress(values[0]);
    }

    public Bitmap downloadImage(String url) {
        try {
            return BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        } catch (Exception e) {
            return null;
        }
    }
}
