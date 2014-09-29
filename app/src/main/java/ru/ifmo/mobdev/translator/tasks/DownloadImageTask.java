package ru.ifmo.mobdev.translator.tasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

import ru.ifmo.mobdev.translator.activities.ShowResultsActivity;
import ru.ifmo.mobdev.translator.models.Picture;

/**
 * @author nqy
 */
public class DownloadImageTask extends AsyncTask<Picture, Void, Picture> {
    private ShowResultsActivity activity;

    public DownloadImageTask(ShowResultsActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Picture doInBackground(Picture... params) {
        final Picture picture = params[0];
        try {
            String url = picture.getThumbnailUrl();
            InputStream is = (InputStream) new URL(url).getContent();
            String[] path = url.split("/");
            picture.setDrawable(Drawable.createFromStream(is, path[path.length - 2]));
            return picture;
        } catch (Exception e) {
            Log.e("DownloadImageTask", "Download failed.", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Picture picture) {
        super.onPostExecute(picture);
        activity.onImageLoaded(picture);
    }
}
