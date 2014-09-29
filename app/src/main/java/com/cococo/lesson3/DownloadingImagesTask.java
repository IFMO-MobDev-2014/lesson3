package com.cococo.lesson3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

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
        for (int i = 0; i < urlsMore.size(); i++) {
            d[i] = getImageFromURL(urlsMore.get(i));
        }
        return d;

    }


    public Drawable getImageFromURL(String imageURL) {
        Drawable drawable = null;
        try {
            drawable = Drawable.createFromStream((InputStream) new URL(imageURL).getContent(), "Picture");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
