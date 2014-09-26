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
    private int width;
    private int height;

    public ImageAdapter(Context context, int width, int height) {
        mContext = context;
        this.width = width;
        this.height = height;
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
        ImageView imageView;
        if (convertView == null) {
            int numColumns = 3;
            int numRows = 4;
            int indent = 20;
            int w = (width - (numColumns + 1) * indent) / numColumns;
            int h = (height - (numColumns + 1) * indent) / numRows;
            w = Math.min(w, h);

            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(w, w));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        } else
            imageView = (ImageView)convertView;
        int w = images.get(i).getWidth();
        int h = images.get(i).getHeight();
        Bitmap split = Bitmap.createBitmap(images.get(i), 0, 0, Math.min(w, h), Math.min(w, h), null, false);
        //imageView.setImageBitmap(Bitmap.createScaledBitmap(images.get(i), w, h, false));
        //imageView.setImageBitmap(images.get(i));
        imageView.setImageBitmap(split);
        return imageView;
    }
}
