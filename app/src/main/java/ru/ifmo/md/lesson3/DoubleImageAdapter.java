package ru.ifmo.md.lesson3;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.net.URL;
import java.util.ArrayList;

public class DoubleImageAdapter extends BaseAdapter {
    static final String FULL_PICTURE = "fullPicture";
    private ResultActivity context;
    private ArrayList<Drawable> pictures = new ArrayList<Drawable>();
    private ArrayList<URL> fullURLs = new ArrayList<URL>();
    int cellHeight;
    int realPictures = 0;

    public DoubleImageAdapter(ResultActivity c) {
        context = c;
        updateCellHeight();
    }

    public void setAll(int n){
        for (int i = 0; i < n; i++) {
            pictures.add(context.getResources().getDrawable(R.drawable.loading_little));
        }
    }

    public int getCount() {
        return pictures.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void add(Drawable picture, URL url) {
        fullURLs.add(realPictures, url); // non thread safe
        pictures.add(realPictures, picture);
        realPictures++;
    }

    int updateCellHeight() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int k = (width - 60) / 200;
        cellHeight = (width - 60) / k; // vary this, try at your own!
        return k;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(cellHeight, cellHeight));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(8, 8, 8, 8); // useless
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.onFullRequest(String.valueOf(fullURLs.get(position)));
            }
        });

        Drawable picture = pictures.get(position);
        imageView.setImageDrawable(picture);
        return imageView;
    }
}
