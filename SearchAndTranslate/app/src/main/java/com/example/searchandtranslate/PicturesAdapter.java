package com.example.searchandtranslate;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Яна on 26.09.2014.
 */
public class PicturesAdapter extends BaseAdapter {
    private Bitmap[] bitmaps;
    private Context context;

    public PicturesAdapter(Context context, Bitmap[] bitmaps) {
        this.context = context;
        this.bitmaps = bitmaps;
    }

    public int getCount() {
        return bitmaps.length;
    }

    public Object getItem(int position) {
        return bitmaps[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int a, View view, ViewGroup vg) {
        ImageView result;
        if (view == null) {
            result = new ImageView(context);
            result.setLayoutParams(new GridView.LayoutParams(85, 85));
            result.setScaleType(ImageView.ScaleType.CENTER_CROP);
            result.setPadding(8, 8, 8, 8);
        } else {
            result = (ImageView) view;
        }
        result.setImageBitmap(bitmaps[a]);
        return result;
    }


}
