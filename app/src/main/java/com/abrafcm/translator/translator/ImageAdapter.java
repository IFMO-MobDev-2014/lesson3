package com.abrafcm.translator.translator;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Nikita Yaschenko on 27.09.14.
 */
public class ImageAdapter extends ArrayAdapter{
    private ArrayList<ImageItem> mItems;
    private IImagesProvider provider;

    private static class ViewHolder {
        private ImageView imageView;
    }

    public ImageAdapter(Context context, ArrayList<ImageItem> items) {
        super(context, 0, items);
        provider = new FakeImagesProvider(context);
        mItems = items;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_image, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.item_imageView);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        ImageItem item = mItems.get(position);
        if (item != null) {
            Bitmap bitmap = provider.getImage(item.getUrl());
            viewHolder.imageView.setImageBitmap(bitmap);
        }

        return view;
    }
}
