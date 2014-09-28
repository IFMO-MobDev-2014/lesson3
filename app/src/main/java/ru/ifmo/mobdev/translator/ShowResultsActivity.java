package ru.ifmo.mobdev.translator;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class ShowResultsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_results_activity);
        final Intent caller = getIntent();
        final TextView translationField = (TextView) findViewById(R.id.translationField);
        String translation = caller.getStringExtra(MainActivity.TRANSLATED_INPUT);
        if (translation == null) {
            translationField.setText(R.string.translationError);
        } else {
            translationField.setText(caller.getStringExtra(MainActivity.TRANSLATED_INPUT));
        }
        final Resources resources = getResources();
        ArrayList<Drawable> pics = new ArrayList<Drawable>(){{
            add(resources.getDrawable(R.drawable.pic1));
            add(resources.getDrawable(R.drawable.pic2));
            add(resources.getDrawable(R.drawable.pic3));
            add(resources.getDrawable(R.drawable.pic4));
            add(resources.getDrawable(R.drawable.pic5));
            add(resources.getDrawable(R.drawable.pic6));
        }};
        GridView view = (GridView) findViewById(R.id.picsGv);
        ImageGridAdapter adapter = new ImageGridAdapter(this);
        view.setAdapter(adapter);
        adapter.addPicture(pics.get(0));
        adapter.addPicture(pics.get(1));
        adapter.addPicture(pics.get(2));
        adapter.addPicture(pics.get(3));
        adapter.addPicture(pics.get(4));
        adapter.addPicture(pics.get(5));
    }
}
