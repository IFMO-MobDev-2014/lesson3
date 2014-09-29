package ru.ifmo.md.lesson3;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
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
    private int cellHeight;

    public DoubleImageAdapter(ResultActivity c) {
        context = c;
        cellHeight = getCellHeight();
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
        fullURLs.add(url); // non thread safe
        pictures.add(picture);
    }

    private int getCellHeight() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        if (width < 800) return (width - 40) / 2; // vary this, try at your own!
        else return 250; // tablets get more images
    }

    // TODO Set "loading" picture until loaded
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new AbsListView.LayoutParams(cellHeight, cellHeight)); // TODO Mate it relative somehow
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
