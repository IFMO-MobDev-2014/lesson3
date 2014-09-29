package ru.ifmo.mobdev.translator.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URL;

import ru.ifmo.mobdev.translator.R;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class LoadFullscreenActivity extends Activity {
    private ImageView imageView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fullscreen_activity);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading fullscreen image");
        progressDialog.show();
        imageView = (ImageView) findViewById(R.id.fullscreenImage);
        final Bundle extras = getIntent().getExtras();
        final URL link = (URL) extras.get(ShowResultsActivity.FULLSCREEN_LINK);
        if (link != null)
            new FullScreenLoader().execute(link);
    }

    public void goBack(View view) {
        finish();
    }

    private class FullScreenLoader extends AsyncTask<URL, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(URL... urls) {
            Bitmap picture = null;
            try {
                picture = BitmapFactory.decodeStream(urls[0].openStream());
            } catch (Exception e) {
                onFullScreenLoadingError();
                Log.e("Error while loading fullscreen image", e.getMessage());
            }
            return picture;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressDialog.dismiss();
            imageView.setImageBitmap(bitmap);
        }
    }

    private void onFullScreenLoadingError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                Toast.makeText(LoadFullscreenActivity.this, "Error loading fullscreen image. " +
                        "Check your Internet connection.", Toast.LENGTH_LONG).show();
            }
        });
        finish();
    }
}
