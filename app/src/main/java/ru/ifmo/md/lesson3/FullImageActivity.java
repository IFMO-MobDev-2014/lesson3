package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;

/**
 * @author volhovm
 *         Created on 9/28/14
 */

public class FullImageActivity extends Activity {
    private Intent intent;

    // TODO Add scrollable left-right
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_loading);
        intent = getIntent();
        String url = intent.getStringExtra(DoubleImageAdapter.FULL_PICTURE);
        new SimpleDownloadImageTask(this).execute(url);
    }

    private class SimpleDownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private FullImageActivity parentActivity;

        public SimpleDownloadImageTask(FullImageActivity parentActivity) {
            this.parentActivity = parentActivity;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error while downloading image", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                parentActivity.onImageLoaded(result);
            } else {
                parentActivity.onImageFailed();
            }
        }
    }

    private void onImageFailed() {
        TextView textView = (TextView) findViewById(R.id.errorTextView);
        findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
        textView.setText(getResources().getString(R.string.fullscreen_image_at_fail));
    }

    private void onImageLoaded(Bitmap bitmap) {
        setContentView(R.layout.fullscreen_picture);
        ImageView mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageBitmap(bitmap);
    }
}
