package ru.ifmo.mobdev.translator;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.ifmo.mobdev.translator.models.Picture;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class ImageGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Drawable> pictures = new ArrayList<Drawable>();

    public ImageGridAdapter(Context context) {
        this.context = context;
    }

    public void addPicture(Picture picture) {
        pictures.add(picture.getDrawable());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return pictures.size();
    }

    @Override
    public Object getItem(int i) {
        return pictures.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            view = inflater.inflate(R.layout.pic_and_info, null);

        } else {
            view = convertView;
        }
        TextView textView = (TextView) view.findViewById(R.id.gridInfo);
        textView.setText("Picture number " + (i + 1));
        ImageView imageView = (ImageView) view.findViewById(R.id.gridImage);
        imageView.setImageDrawable(pictures.get(i));
        return view;
    }

}