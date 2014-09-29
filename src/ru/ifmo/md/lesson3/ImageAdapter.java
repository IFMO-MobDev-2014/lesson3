package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> images = new ArrayList<Bitmap>();
    private final int width, height;

    public ImageAdapter(Activity act) {
        mContext = act;
        width = MainActivity.getScreenSize(act).x;
        height = MainActivity.getScreenSize(act).y;
    }

    public void addImage(Bitmap img) {
        images.add(img);
    }

    public int getCount() {
        if (images == null)
            return 0;
        return images.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(width / 2, height / 6));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(images.get(position));
        return imageView;
    }
}