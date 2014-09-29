package com.cococo.lesson3;

/**
 * Created by Freemahn on 28.09.2014.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DownloadingImagesTask extends AsyncTask<String, Void, Drawable[]> {
    Context context;
    String[] urls = new String[10];

    public DownloadingImagesTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Drawable[] doInBackground(String... urls) {
        Drawable[] d = new Drawable[10];
        for (int i = 0; i < urls.length; i++)
            d[i] = getImageFromURL(urls[i]);
        return d;

    }


    public Drawable getImageFromURL(String imageURL) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream((InputStream) new URL(imageURL).getContent(), "Picture");
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return drawable;
    }

    @Override
    protected void onPostExecute(Drawable[] d) {
        ((ResultActivity) context).setFoundedImages(d);
    }

}
