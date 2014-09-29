package com.room.translator491;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.translator491.R;
import com.loopj.android.image.SmartImageView;

import java.util.List;

import static android.R.color.*;

public class ItemImageResultsArrayAdapter extends ArrayAdapter<ItemImageResult> {


    private static class ViewHolder {

        public SmartImageView sImage;
        public ViewHolder(View view) {
            sImage = (SmartImageView) view.findViewById(R.id.sImage);
        }

    }

    public ItemImageResultsArrayAdapter(Context context, List<ItemImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemImageResult imageInfo = this.getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_image_result, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.sImage.setImageResource(darker_gray);
        }

        holder.sImage.setImageUrl(imageInfo.getSUrl());
        return convertView;
    }

}
