package ru.ifmo.mobdev.translator.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

import ru.ifmo.mobdev.translator.R;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class LoadFullscreenActivity extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_activity);
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
            } catch (IOException e) {
                Log.e("Error while loading fullscreen image", e.getMessage());
            }
            return picture;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
