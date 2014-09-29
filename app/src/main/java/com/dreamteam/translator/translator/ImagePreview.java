package com.dreamteam.translator.translator;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class ImagePreview extends Activity {
    public static final long IMAGE_PREVIEW_TIMEOUT = 4000;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        imgView = (ImageView) findViewById(R.id.preview_container);
        String url = getIntent().getStringExtra("url");
        url = url.replace("_q.jpg", ".jpg");
        ImageLoadTask loadTask = new ImageLoadTask(this, imgView, url);
        TimeoutTaskRunner.runTask(loadTask, IMAGE_PREVIEW_TIMEOUT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_preview, menu);
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
