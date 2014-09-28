package ru.ifmo.ctddev.soloveva.translator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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
            RelativeLayout layout = new RelativeLayout(context);
            layout.setLayoutParams(new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            holder = new ViewHolder();
            holder.imageView = new ImageView(context);
            {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                holder.imageView.setLayoutParams(params);

                holder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                holder.imageView.setAdjustViewBounds(true);
            }
            holder.progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
            {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                holder.progressBar.setLayoutParams(params);
            }
            layout.addView(holder.imageView);
            layout.addView(holder.progressBar);
            layout.setTag(holder);
            convertView = layout;
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
