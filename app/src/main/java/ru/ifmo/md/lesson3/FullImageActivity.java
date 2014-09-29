package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.app.ProgressDialog;
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
    private ProgressDialog progressDialog;
    private Intent intent;

    // TODO Add scrollable left-right
    // TODO Add error handler dialog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_picture);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        intent = getIntent();
        ImageView mImageView = (ImageView) findViewById(R.id.imageView);
        String url = intent.getStringExtra(DoubleImageAdapter.FULL_PICTURE);
        new SimpleDownloadImageTask(mImageView, this).execute(url);
    }

    private class SimpleDownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private FullImageActivity parentActivity;
        ImageView bmImage;

        public SimpleDownloadImageTask(ImageView bmImage, FullImageActivity parentActivity) {
            this.bmImage = bmImage;
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
            parentActivity.progressDialog.dismiss();
            bmImage.setImageBitmap(result);
        }
    }
}
