package ru.ifmo.md.lesson3;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;

import java.io.InputStream;
import java.net.URL;

/**
 * @author volhovm
 *         Created on 9/27/14
 */

public class ShowImageTask extends AsyncTask<URL, Void, Drawable> {
    private ImageAdapter adapter;

    public ShowImageTask(ImageAdapter a) {
        adapter = a;
    }

    protected Drawable doInBackground(URL... urls) {
        URL url = urls[0];
        try {
            InputStream content = (InputStream)url.getContent();
            Drawable picture = Drawable.createFromStream(content , "src");
            return picture;
        } catch (Exception e) {
            Log.e("error", "", e);
        }
        return null;
    }

    protected void onPostExecute(Drawable picture) {
        adapter.add(picture);
        adapter.notifyDataSetChanged();
    }
}