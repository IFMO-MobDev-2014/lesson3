package com.example.pva701.lesson3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;
import odeenpva.lesson3.*;

public class ContentActivity extends Activity {
    private ImageAdapter adapter;
    private TextView tvTranslateWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        String word = getIntent().getExtras().getString("word").trim();
        Log.i("WORD=", word);
        tvTranslateWord = (TextView)findViewById(R.id.tvTranslateWord);

        new PictureLoader(word, 12) {
            @Override
            protected void onProgressUpdate(Bitmap... progress) {
                adapter.addImage(progress[0]);
                Log.i("BITMAP", "bitmap loaded!");
            }

            @Override
            protected void onPostExecute(Boolean status) {
                Log.i("BITMAP", "ALL Bitmaps loaded with status " + status);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        new Translater(word, Translater.Language.EN, Translater.Language.RU) {
            @Override
            protected void onPostExecute(String result) {
                if (result == null) {
                    Log.i("ERROR", "SHIT");
                } else
                    tvTranslateWord.setText(result);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        Log.i("START", "started bitmap!");
        GridView gvPictures = (GridView)findViewById(R.id.gvPictures);
        adapter = new ImageAdapter(this);
        gvPictures.setAdapter(adapter);
    }

    /*@Override
    public void onDestroy() {
        Log.i("onDestroy");
    }*/
}
