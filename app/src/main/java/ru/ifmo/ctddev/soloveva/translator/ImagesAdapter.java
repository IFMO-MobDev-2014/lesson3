package ru.ifmo.ctddev.soloveva.translator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maria on 27.09.14.
 */
public class ImagesAdapter extends BaseAdapter {
    private final List<ImageData> images;
    private final Context context;
    private final ImageDownloader downloader = new ImageDownloader();

    public ImagesAdapter(Context context, List<? extends ImageData> images) {
        this.context = context;
        this.images = new ArrayList<>(images);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public ImageData getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (holder.previousTask != null) {
            holder.previousTask.cancel(false);
        }
        ImageData imageData = images.get(position);
        Drawable dummyImage = createDummyDrawable(imageData.getWidth(), imageData.getHeight());
        holder.imageView.setImageDrawable(dummyImage);
        holder.previousTask = new ImageDownloadTask(holder.progressBar, holder.imageView);
        holder.previousTask.execute(imageData.getUrl());
        return convertView;
    }

    private Drawable createDummyDrawable(int width, int height) {
        ShapeDrawable dummyImage = new ShapeDrawable(new RectShape());
        dummyImage.setIntrinsicWidth(width);
        dummyImage.setIntrinsicHeight(height);
        return dummyImage;
    }

    private class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
        private final ProgressBar progressBar;
        private final ImageView imageView;
        private IOException exception;

        private ImageDownloadTask(ProgressBar progressBar, ImageView imageView) {
            this.progressBar = progressBar;
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            imageView.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            try {
                return downloader.download(url[0]);
            } catch (IOException e) {
                exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressBar.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
            if (bitmap == null) {
                Log.e("TRNSLT", "Image download failed", exception);
                imageView.setImageResource(R.drawable.not_available);
            } else {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    private static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
        ImageDownloadTask previousTask;
    }
}
