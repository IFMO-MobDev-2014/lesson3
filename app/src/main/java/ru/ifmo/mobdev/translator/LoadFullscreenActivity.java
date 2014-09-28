package ru.ifmo.mobdev.translator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class LoadFullscreenActivity extends Activity {
    private final ImageView imageView = (ImageView) findViewById(R.id.fullscreenImage);
    private URL link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_activity);
        Bundle extras = getIntent().getExtras();
        Bitmap picture = (Bitmap) extras.get("");
        link = (URL) extras.get("");
        imageView.setImageBitmap(picture);
        if (link != null)
            new FullScreenLoader().execute();
    }

    public void goBack(View view) {
        finish();
    }

    private class FullScreenLoader extends AsyncTask<Void, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap picture = null;
            try {
                picture = BitmapFactory.decodeStream(link.openStream());
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
