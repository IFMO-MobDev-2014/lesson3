package com.example.pva701.lesson3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;


public class FullScreenShowActivity extends Activity {
    private Bitmap picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_show);
        picture = (Bitmap)getIntent().getExtras().get("image");
        final String ref = (String)getIntent().getExtras().get("ref");
        final ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(picture);
        if (ref != null) {
            new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... voids) {
                    try {
                        picture = BitmapFactory.decodeStream(new URL(ref).openStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return  picture;
                }
                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            }.execute();
        }
        setContentView(imageView);
    }
}
