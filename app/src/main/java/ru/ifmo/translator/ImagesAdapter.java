package ru.ifmo.translator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class ImagesAdapter extends BaseAdapter {
    Context context;
    Drawable[] images;

    public ImagesAdapter(Context context, Drawable[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = new ImageView(context);
        ((ImageView) convertView).setImageDrawable(images[position]);
        return convertView;
    }
}
