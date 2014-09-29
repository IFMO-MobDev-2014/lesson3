package ru.android.german.translator;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by german on 28.09.14.
 */
public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<Bitmap> data = new ArrayList<Bitmap>();

    public GridViewAdapter(Context context, int resource, ArrayList<Bitmap> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageView imageView = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            imageView = (ImageView)row.findViewById(R.id.image);
            row.setTag(imageView);
        } else {
            imageView = (ImageView)row.getTag();
        }
        imageView.setImageBitmap(data.get(position));
        return row;
    }
}
