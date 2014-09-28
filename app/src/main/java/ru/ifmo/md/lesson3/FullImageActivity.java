package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * @author volhovm
 *         Created on 9/28/14
 */

public class FullImageActivity extends Activity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        String url = intent.getStringExtra(DoubleImageAdapter.FULL_PICTURE);
        setContentView(R.layout.fullscreen_picture);
        ImageView mImageView = (ImageView) findViewById(R.id.imageView);
        new SimpleDownloadImageTask(mImageView).execute(url);
    }

    private class SimpleDownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public SimpleDownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
