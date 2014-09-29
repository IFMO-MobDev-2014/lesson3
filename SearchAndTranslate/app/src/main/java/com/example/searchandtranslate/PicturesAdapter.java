package com.example.searchandtranslate;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Яна on 26.09.2014.
 */
public class PicturesAdapter extends BaseAdapter {
    private Bitmap[] bitmaps = new Bitmap[10];
    private boolean[] loadingState = new boolean[10];

    private Context context;
    private String word;

    public PicturesAdapter(Context context, String word) {
        this.context = context;
        this.word = word;
    }

    public int getCount() {
        return 10;
    }

    public void setBitmap(int index, Bitmap bitmap) {
        bitmaps[index] = bitmap;
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
            result.setScaleType(ImageView.ScaleType.CENTER);
            result.setPadding(15, 15, 15, 15);
        } else {
            result = (ImageView) view;
        }

        if(bitmaps[a] != null)
            result.setImageBitmap(bitmaps[a]);
        else if(!loadingState[a]) {
            loadingState[a] = true;
            DataLoader.asyncLoadPictures(word, a, new DataLoader.MyCallbackPicture(context, this, a, result));
        }

        return result;
    }

}
