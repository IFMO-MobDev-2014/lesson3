package ru.ifmo.ctddev.soloveva.translator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maria on 27.09.14.
 */
public class ImagesAdapter extends BaseAdapter {
    private final List<ImageData> images;
    private final Context context;

    public ImagesAdapter(Context context, List<? extends ImageData> images) {
        this.context = context;
        this.images = new ArrayList<ImageData>(images);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public ImageData getItem(int index) {
        return images.get(index);
    }

    @Override
    public long getItemId(int index) {
        return 0;
    }

    @Override
    public View getView(int index, View cachedView, ViewGroup parentGroup) {
        ImageView view;
        if (cachedView == null) {
            view = new ImageView(context);
        } else {
            view = (ImageView) cachedView;
        }
        ImageDownloadTask previousTask = (ImageDownloadTask) view.getTag();
        if (previousTask != null) {
            previousTask.cancel(true);
        }
        ImageDownloadTask nextTask = new ImageDownloadTask(view);
        view.setTag(nextTask);
        nextTask.execute(images.get(index).getUrl());
        return view;
    }

    private static class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView view;

        private ImageDownloadTask(ImageView view) {
            this.view = view;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            try {
                return BitmapFactory.decodeStream(new URL(url[0]).openStream());
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            view.setImageBitmap(bitmap);
        }
    }
}
