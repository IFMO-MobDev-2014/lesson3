package com.example.vi34.lesson3;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.net.URL;

/**
 * Created by vi34 on 26.09.14.
 */
public class ImageDownloader {

    Bitmap bmp = null;
    public Bitmap search(String querry){

        new DownloadImageTask().execute("s");
        return bmp;
    }

    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
        protected Bitmap doInBackground(String... querry) {
            Bitmap bmp = null;

            publishProgress(1);

            Log.i("ASYNC", "Do Task OK");
            return bmp;
        }

        protected void onProgressUpdate(Integer... progress) {
           Log.i("ASYNC", "Update OK");
        }

        protected void onPostExecute(Bitmap result) {
            Log.i("ASYNC", "Done");
            bmp = result;
        }
    }
}
