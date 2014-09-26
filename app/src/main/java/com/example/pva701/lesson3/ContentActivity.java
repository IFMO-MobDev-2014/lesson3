package com.example.pva701.lesson3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import odeenpva.lesson3.*;

public class ContentActivity extends Activity {
    private ImageAdapter adapter;
    private TextView tvTranslateWord;
    private PictureLoader pictureLoader;
    private Translater translater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        String word = getIntent().getExtras().getString("word").trim();
        tvTranslateWord = (TextView)findViewById(R.id.tvTranslateWord);

        pictureLoader = new PictureLoader(word, 12) {
            @Override
            protected void onProgressUpdate(Bitmap... progress) {
                adapter.addImage(progress[0]);
                Log.i("BITMAP", "bitmap loaded!");
            }

            @Override
            protected void onPostExecute(Boolean status) {
                Log.i("BITMAP", "ALL Bitmaps loaded with status " + status);
            }
        };
        pictureLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        translater = new Translater(word, Translater.Language.EN, Translater.Language.RU) {
            @Override
            protected void onPostExecute(String result) {
                if (result == null) {
                    Log.i("ERROR", "SHIT");
                } else
                    tvTranslateWord.setText(result);
            }
        };
        translater.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        GridView gvPictures = (GridView)findViewById(R.id.gvPictures);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        adapter = new ImageAdapter(this, width, height);
        gvPictures.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pictureLoader.cancel(true);
        translater.cancel(true);
    }
}
