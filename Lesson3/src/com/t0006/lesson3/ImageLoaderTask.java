package com.t0006.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

/**
 * Created by dimatomp on 27.09.14.
 */
public class ImageLoaderTask extends AsyncTask<String, Bitmap, Void> {
    private AsyncTaskFragment fragment;
    private ImagesAdapter adapter;

    public ImageLoaderTask(AsyncTaskFragment fragment, ImagesAdapter adapter) {
        this.fragment = fragment;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            // Please do not use fragment.getActivity() as a context
            // since the fragment may have been detached.
            Thread.sleep(5000);
            publishProgress(BitmapFactory.decodeResource(fragment.getActivity().getResources(), android.R.drawable.ic_delete));
            Thread.sleep(8000);
            publishProgress(BitmapFactory.decodeResource(fragment.getActivity().getResources(), android.R.drawable.ic_dialog_email));
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
