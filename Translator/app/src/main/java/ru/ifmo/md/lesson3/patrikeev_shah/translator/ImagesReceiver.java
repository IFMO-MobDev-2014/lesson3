package ru.ifmo.md.lesson3.patrikeev_shah.translator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sultan on 28.09.14.
 */
public class ImagesReceiver extends AsyncTask<ArrayList<String>, Void, ArrayList<Bitmap> >
{
    private static final int COUNT_IMAGES = 10;

    @Override
    protected ArrayList<Bitmap> doInBackground (ArrayList<String>... urlsArray){
        ArrayList<Bitmap> images = new ArrayList<Bitmap>();
        ArrayList<String> urls = urlsArray[0];
        for (int i = 0; i < Math.min(urls.size(), COUNT_IMAGES); i++) {
            try {
                images.add(getImageByUrl(urls.get(i)));
            } catch (IOException e) {
            }
        }
        return images;
    }

    private Bitmap getImageByUrl(String url) throws IOException {
        Bitmap image = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        return image;
    }
}
