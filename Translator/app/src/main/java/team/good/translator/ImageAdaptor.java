package team.good.translator;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import android.widget.Gallery;

/**
 * Created by timur on 29.09.14.
 */

public class ImageAdaptor extends BaseAdapter {
    private Context context;
    private Bitmap[] bitmaps;
    private int size, view_padding;

    public ImageAdaptor(Context сontext, Bitmap[] bitmaps, int size, int view_padding) {
        this.context = сontext;
        this.bitmaps = bitmaps;
        this.size = size;
        this.view_padding = view_padding;
    }

    @Override
    public int getCount() {
        return bitmaps.length;
    }

    @Override
    public Object getItem(int position) {
        return bitmaps[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = new ImageView(context);
        view.setImageBitmap(bitmaps[position]);
        view.setPadding(view_padding, view_padding, view_padding, view_padding);
        view.setLayoutParams(new Gallery.LayoutParams(size, size));
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }
}