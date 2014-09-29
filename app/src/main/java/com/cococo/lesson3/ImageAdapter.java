package com.cococo.lesson3;

/**
 * Created by ilya4544 on 29.09.2014.
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ImageAdapter extends BaseAdapter {

    private Context myContext;
    private Drawable[] image;

    public ImageAdapter(Context сontext, Drawable[] images) {
        myContext = сontext;
        this.image = images;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public Object getItem(int position) {
        return image[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = new ImageView(myContext);
        view.setImageDrawable(image[position]);
        view.setPadding(25, 25, 25, 25);
        view.setLayoutParams(new ListView.LayoutParams(250, 250));
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }
}
