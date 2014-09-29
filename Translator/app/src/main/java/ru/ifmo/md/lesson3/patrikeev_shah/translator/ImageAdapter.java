package ru.ifmo.md.lesson3.patrikeev_shah.translator;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by sultan on 28.09.14.
 */
public class ImageAdapter extends BaseAdapter{

    private final Context mContext;
    private ArrayList<Bitmap> mBitmaps;

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
        this.mBitmaps = new ArrayList<>();
    }

    public void add(Bitmap bmp) {
        mBitmaps.add(Bitmap.createScaledBitmap(bmp, 450, 450, false));
    }

    public void clear() {
        mBitmaps.clear();
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
