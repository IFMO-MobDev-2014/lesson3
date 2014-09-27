package com.t0006.lesson3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Created by dimatomp on 27.09.14.
 */
public class ImageLoaderTask extends AsyncTask<String, Bitmap, Void> {
    Context context;
    ImagesAdapter adapter;

    public ImageLoaderTask(Context context, ImagesAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            Thread.sleep(5000);
            publishProgress(BitmapFactory.decodeResource(context.getResources(), android.R.drawable.ic_delete));
            Thread.sleep(8000);
            publishProgress(BitmapFactory.decodeResource(context.getResources(), android.R.drawable.ic_dialog_email));
            Thread.sleep(1500);
        } catch (InterruptedException ignore) {
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Bitmap... values) {
        for (Bitmap bitmap : values)
            adapter.addItem(bitmap);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        adapter.onFinishedLoading();
    }
}
