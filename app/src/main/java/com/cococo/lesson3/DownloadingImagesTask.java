package com.cococo.lesson3;

/**
 * Created by Freemahn on 28.09.2014.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class DownloadingImagesTask extends AsyncTask<String, Void, Drawable[]> {
    Context context;
    List<String> urlsMore = null;

    public DownloadingImagesTask(Context context, List<String> list) {
        super();
        this.context = context;
        urlsMore = list;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Drawable[] doInBackground(String... urls) {
        Drawable[] d = new Drawable[10];
        Log.e("DOIN", "STARTED");
        for (int i = 0; i < urlsMore.size(); i++) {
            d[i] = getImageFromURL(urlsMore.get(i));
            Log.e("DOIN", d[i].toString());
        }
        return d;

    }


    public Drawable getImageFromURL(String imageURL) {
        Drawable drawable = null;
        Log.e("GETDRAWABLE", "1");
        try {
            drawable = Drawable.createFromStream((InputStream) new URL(imageURL).getContent(), "Picture");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR", e.toString());
        }
        return drawable;
    }

    @Override
    protected void onPostExecute(Drawable[] d) {
        ((ResultActivity) context).setFoundedImages(d);
    }

}
