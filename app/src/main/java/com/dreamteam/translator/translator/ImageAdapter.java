package com.dreamteam.translator.translator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by flyingleafe on 25.09.14.
 */
public class ImageAdapter extends BaseAdapter {

    public static final long IMAGE_LOADING_TIMEOUT = 4000;

    private GridView gallery;
    private ArrayList<String> urls;
    private Drawable[] cachedImages;
    private Context context;

    public ImageAdapter(ArrayList<String> urls, GridView gallery, Drawable[] cachedImages, Context ctx) {
        this.gallery = gallery;
        this.urls = urls;
        this.cachedImages = cachedImages;
        context = ctx;
    }

    public int getCount() {
        return urls.size();
    }

    public Object getItem(int position) {
        return urls.get(position);
    }

    public long getItemId(int position) {
        return position / gallery.getNumColumns();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (SquareImageView) LayoutInflater.from(context).inflate(R.layout.grid_image, null);
        String url = urls.get(position);

        if (cachedImages[position] == null) {
            imageView.setBackgroundResource(R.drawable.loader);
            ImageLoadTask loader = new ImageLoadTask(imageView, cachedImages, position, context, url);
            TimeoutTaskRunner.runTask(loader, IMAGE_LOADING_TIMEOUT);
        } else {
            imageView.setImageDrawable(cachedImages[position]);
        }
        imageView.setOnClickListener(new ImageClickListener(context, url));

        return imageView;
    }
}
