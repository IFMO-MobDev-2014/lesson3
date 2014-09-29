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

    private View createMoreButton(ViewGroup parent) {
        View result = getLayoutInflater().inflate(R.layout.image_view, parent, false);
        ImageView view = (ImageView) result.findViewById(R.id.image_view);
        view.setImageResource(R.drawable.ic_action_new);
        result.setOnClickListener(getFragment());
        return result;
    }

    @Override
    protected View createLastItem(ViewGroup parent) {
        return getLayoutInflater().inflate(R.layout.ind_progress_bar_framed, parent, false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap content = (Bitmap) getItem(position);
        if (convertView == null) {
            if (content == null)
                return doneLoading ? createMoreButton(parent) : createLastItem(parent);
            convertView = getLayoutInflater().inflate(R.layout.image_view, parent, false);
        }
        if (content != null)
            ((ImageView) convertView.findViewById(R.id.image_view)).setImageBitmap(content);
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
