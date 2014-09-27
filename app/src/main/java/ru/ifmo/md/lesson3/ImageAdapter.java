package ru.ifmo.md.lesson3;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Drawable> pictures = new ArrayList<Drawable>();

    public ImageAdapter(Context c) {
        context = c;

    }

    public int getCount() {
        return pictures.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void add(Drawable picture) {
        pictures.add(picture);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Drawable picture = pictures.get(position);
        imageView.setImageDrawable(picture);
        return imageView;
    }

}
