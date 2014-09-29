package ru.ifmo.md.lesson3;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * @author volhovm
 *         Created on 9/27/14
 */

public class ShowImageTask extends AsyncTask<URL, Void, Drawable> {
    private DoubleImageAdapter adapter;
    private URL url;
    boolean canceled = false;

    public ShowImageTask(DoubleImageAdapter a) {
        adapter = a;
    }

    protected Drawable doInBackground(URL... urls) {
        URL url = urls[0];
        this.url = urls[1];
        try {
            InputStream content = (InputStream)url.getContent();
            Drawable picture = Drawable.createFromStream(content , "src");
            return picture;
        } catch (Exception e) {
            Log.e("error", "", e);
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        canceled = true;
    }

    @Override
    protected void onPostExecute(Drawable picture) {
        if (!canceled) {
            adapter.add(picture, url);
            adapter.notifyDataSetChanged();
        }
    }
}