package com.t0006.lesson3;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by dimatomp on 27.09.14.
 */
public class ImagesAdapter extends StdListAdapter<Bitmap> {

    public ImagesAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap content = (Bitmap) getItem(position);
        if (convertView == null) {
            if (content == null) {
                ProgressBar progressBar = new ProgressBar(inflater.getContext());
                progressBar.setIndeterminate(true);
                return progressBar;
            }
            convertView = new ImageView(inflater.getContext());
        }
        if (content != null)
            ((ImageView) convertView).setImageBitmap(content);
        return convertView;
    }
}
