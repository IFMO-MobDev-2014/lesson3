package ru.ifmo.md.lesson3.patrikeev_shah.translator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sultan on 28.09.14.
 */
public class ImagesReceiver extends AsyncTask<ArrayList<String>, Bitmap, Void>
{
    private ImageAdapter mAdapter;

    public ImagesReceiver(ImageAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mAdapter.clear();
    }

    private Bitmap getImageByUrl(String url) throws IOException {
        Bitmap image = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        return image;
    }

    @Override
    protected Void doInBackground (ArrayList<String>... urlsArray){
        ArrayList<String> urls = urlsArray[0];
        for (int i = 0; i < urls.size(); i++) {
            try {
                publishProgress(getImageByUrl(urls.get(i)));
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Bitmap... bitmaps) {
        mAdapter.add(bitmaps[0]);
        mAdapter.notifyDataSetChanged();
        super.onProgressUpdate(bitmaps);
    }

}
