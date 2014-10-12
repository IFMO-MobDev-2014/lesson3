package com.pinguinson.translator.tasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import com.pinguinson.translator.models.Photo;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by pinguinson on 12.10.2014.
 */
public class DownloadImageTask extends AsyncTask<Photo, Void, Photo> {

    @Override
    protected Photo doInBackground(Photo... photos) {
        Photo photo = photos[0];
        try {
            String url = photo.getUrl();
            InputStream is = (InputStream) new URL(url).getContent();
            String[] path = url.split("/");
            photo.setDrawable(Drawable.createFromStream(is, path[path.length - 2])); //TODO Why the fuck it's (length - 2) ???
            return photo;
        } catch (Exception e) {
            Log.e("DownloadImageTask", "Download Failed.", e);
            return null;
        }
    }
}
