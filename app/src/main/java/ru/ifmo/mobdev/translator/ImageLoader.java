package ru.ifmo.mobdev.translator;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class ImageLoader extends AsyncTask<String, Void, Drawable> {

    @Override
    protected Drawable doInBackground(String... links) {
        return null;
    }

    @Override
    protected void onPostExecute(Drawable pic) {
        super.onPostExecute(pic);
    }
}
