package com.example.pva701.lesson3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by pva701 on 26.09.14.
 */
public class ImageAdapter extends BaseAdapter {
    private ArrayList <Bitmap> images = new ArrayList<Bitmap>();
    private Context mContext;
    public ImageAdapter(Context context) {
        mContext = context;
    }

    public void addImage(Bitmap bm) {
        images.add(bm);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int i) {
        return images.get(i);
    }

    @Override
    public long getItemId(int i) {
        return -1;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ImageView imageView = null;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(220, 220));//
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        } else
            imageView = (ImageView)convertView;
        imageView.setImageBitmap(images.get(i));
        return imageView;
    }
}
