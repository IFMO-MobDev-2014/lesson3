package ru.ifmo.mobdev.translator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import ru.ifmo.mobdev.translator.models.Picture;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class ImageGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Picture> pictures = new ArrayList<Picture>();

    public ImageGridAdapter(Context context) {
        this.context = context;
    }

    public void addPicture(Picture picture, int i) {
        if (i < pictures.size() && pictures.get(i) != null)
            pictures.remove(i);
        pictures.add(i, picture);
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
        if (pictures.get(i).getAuthor() != null)
            textView.setText("@" + pictures.get(i).getAuthor().split("\n")[0]);
        ImageView imageView = (ImageView) view.findViewById(R.id.gridImage);
        if (pictures.get(i).getDrawable() != null) {
            view.findViewById(R.id.pbar).setVisibility(View.GONE);
            imageView.setImageDrawable(pictures.get(i).getDrawable());
        }
        return view;
    }

}