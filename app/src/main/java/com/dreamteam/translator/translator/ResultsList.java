package com.dreamteam.translator.translator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ResultsList extends Activity {

    GridView gallery;
    TextView resultText;
    Intent starter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_list);
        starter = getIntent();
        gallery = (GridView) findViewById(R.id.gallery);
        resultText = (TextView) findViewById(R.id.result_text);
        long start = System.currentTimeMillis();
        ArrayList<String> urls = starter.getStringArrayListExtra(SearchField.IMAGES);
        Drawable[] cachedImages = new Drawable[urls.size()];
        long stop = System.currentTimeMillis();
        Log.i("UNPARCEL TIME", stop - start + "");
        start = System.currentTimeMillis();
        gallery.setAdapter(new ImageAdapter(urls, gallery, cachedImages, this));
        stop = System.currentTimeMillis();
        Log.i("ADAPTER TIME", stop - start + "");
        resultText.setText(starter.getStringExtra(SearchField.TRANSLATION_RESULT));
    }

    public void goBack(View view) {
        Intent back = new Intent(this, SearchField.class);
        startActivity(back);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setImageView(final ImageView imageView, final Drawable d) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageDrawable(d);
            }
        });
    }
}
