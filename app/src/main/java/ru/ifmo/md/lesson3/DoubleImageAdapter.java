package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

public class DoubleImageAdapter extends BaseAdapter {
    static final String FULL_PICTURE = "fullPicture";
    private ResultActivity context;
    private ArrayList<Drawable> pictures = new ArrayList<Drawable>();
    private ArrayList<URL> fullURLs = new ArrayList<URL>();

    public DoubleImageAdapter(ResultActivity c) {
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

    public void add(Drawable picture, URL url) {
        fullURLs.add(url); // non thread safe
        pictures.add(picture);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(230, 230)); // TODO Mate it relative somehow
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.onFullRequest(String.valueOf(fullURLs.get(position)));
            }
        });
        Drawable picture = pictures.get(position);
        imageView.setImageDrawable(picture);
        return imageView;
    }

}
