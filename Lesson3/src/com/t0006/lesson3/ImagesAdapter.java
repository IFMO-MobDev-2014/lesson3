package com.t0006.lesson3;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by dimatomp on 27.09.14.
 */
public class ImagesAdapter extends StdListAdapter<Bitmap> {

    public ImagesAdapter(AsyncTaskFragment fragment) {
        super(fragment);
    }

    private View createMoreButton() {
        ImageView view = new ImageView(getLayoutInflater().getContext());
        view.setImageResource(R.drawable.ic_action_new);
        view.setOnClickListener(getFragment());
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap content = (Bitmap) getItem(position);
        if (convertView == null) {
            if (content == null)
                return doneLoading ? createMoreButton() : createLastItem(parent);
            convertView = new ImageView(getLayoutInflater().getContext());
        }
        if (content != null)
            ((ImageView) convertView).setImageBitmap(content);
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        int sup = super.getItemViewType(position);
        if (sup == 1 && doneLoading)
            return 2;
        return sup;
    }

    public void moreContent() {
        doneLoading = false;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return super.getCount() + (doneLoading ? 1 : 0);
    }
}
