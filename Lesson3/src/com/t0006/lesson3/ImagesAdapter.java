package com.t0006.lesson3;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by dimatomp on 27.09.14.
 */
public class ImagesAdapter extends StdListAdapter<Bitmap> {

    public ImagesAdapter(Fragment fragment) {
        super(fragment);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap content = (Bitmap) getItem(position);
        if (convertView == null) {
            if (content == null)
                return createLastItem(parent);
            convertView = new ImageView(getLayoutInflater().getContext());
        }
        if (content != null)
            ((ImageView) convertView).setImageBitmap(content);
        return convertView;
    }
}
