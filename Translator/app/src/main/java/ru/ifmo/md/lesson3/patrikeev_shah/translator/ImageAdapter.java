package ru.ifmo.md.lesson3.patrikeev_shah.translator;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by sultan on 28.09.14.
 */
public class ImageAdapter extends BaseAdapter{

    private final Context mContext;
    private final ArrayList<Bitmap> mBitmaps;

    public ImageAdapter(Context mContext, ArrayList<Bitmap> mBitmaps) {
        this.mContext = mContext;
        this.mBitmaps = mBitmaps;
    }

    @Override
    public int getCount() {
        return (mBitmaps == null ? 0 : mBitmaps.size());
    }

    @Override
    public Object getItem(int i) {
        return mBitmaps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if (view == null) {
            imageView = new ImageView(mContext);
        } else {
            imageView = (ImageView) view;
        }
        imageView.setImageBitmap(mBitmaps.get(i));
        return imageView;
    }
}
