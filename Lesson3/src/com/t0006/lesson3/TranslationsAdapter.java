package com.t0006.lesson3;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by dimatomp on 26.09.14.
 */
public class TranslationsAdapter extends StdListAdapter<WordTranslation> {

    public TranslationsAdapter(Context context) {
        super(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        WordTranslation translation = (WordTranslation) getItem(position);
        if (convertView == null) {
            if (translation == null) {
                ProgressBar progressBar = new ProgressBar(inflater.getContext());
                progressBar.setIndeterminate(true);
                return progressBar;
            }
            convertView = inflater.inflate(android.R.layout.two_line_list_item, parent, false);
        }
        if (translation != null) {
            TextView title = (TextView) convertView.findViewById(android.R.id.text1);
            title.setText(translation.translation);
            title = (TextView) convertView.findViewById(android.R.id.text2);
            title.setText(translation.lang);
        }
        return convertView;
    }
}
