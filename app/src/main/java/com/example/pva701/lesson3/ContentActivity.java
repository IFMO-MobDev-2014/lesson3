package com.example.pva701.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import odeenpva.lesson3.*;

public class ContentActivity extends Activity {
    private ImageAdapter adapter;
    private TextView tvTranslateWord;
    private ArrayList<PictureProfiler> pictureProfilers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        String word = getIntent().getExtras().getString("word").trim();
        Log.i("WORD=", word);
        tvTranslateWord = (TextView)findViewById(R.id.tvTranslateWord);
        pictureProfilers = new ArrayList<PictureProfiler>();
        new PictureLoader(word, 12) {
            @Override
            protected void onProgressUpdate(PictureProfiler... progress) {
                adapter.addImage(progress[0].getImage());
                pictureProfilers.add(progress[0]);
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
        gvPictures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ContentActivity.this, FullScreenShowActivity.class);
                Bitmap b = pictureProfilers.get(i).getQualityImage();
                String ref = pictureProfilers.get(i).getQRef();
                if (b == null)
                    b = pictureProfilers.get(i).getImage();
                else
                    ref = null;
                intent.putExtra("image", pictureProfilers.get(i).getQualityImage());
                intent.putExtra("ref", pictureProfilers.get(i).getQRef());
                startActivity(intent);
            }
        });
    }

}
