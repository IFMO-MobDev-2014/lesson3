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
    //private Bitmap[] bitmaps;

    private Context context;
    private String word;

    public PicturesAdapter(Context context, String word) {//} Bitmap[] bitmaps) {
        this.context = context;
        this.word = word;
      //  this.bitmaps = bitmaps;
    }

    public int getCount() {
        return 10;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int a, View view, ViewGroup vg) {
        ImageView result;
        if (view == null) {
            result = new ImageView(context);
            result.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            result.setPadding(15, 15, 15, 15);
        } else {
            result = (ImageView) view;
        }
        DataLoader.asyncLoadPictures(word, a, new DataLoader.MyCallbackPicture(context, result));
        return result;
    }


}
