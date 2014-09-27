package com.example.pva701.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import java.util.ArrayList;
import odeenpva.lesson3.*;

public class ContentActivity extends Activity {
    private ImageAdapter adapter;
    private TextView tvTranslateWord;
    private PictureLoader pictureLoader;
    private Translator translator;

    private ArrayList<PictureProfiler> pictureProfilers = new ArrayList<PictureProfiler>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        String word = getIntent().getExtras().getString("word").trim();
        tvTranslateWord = (TextView)findViewById(R.id.tvTranslateWord);

        pictureLoader = new PictureLoader(word, 12) {
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
        };
        pictureLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        translator = new Translator(word, Translator.Language.EN, Translator.Language.RU) {
            @Override
            protected void onPostExecute(String result) {
                tvTranslateWord.setText(result);
            }
        };
        translator.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        GridView gvPictures = (GridView)findViewById(R.id.gvPictures);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        adapter = new ImageAdapter(this, width, height);
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
                intent.putExtra("image", b);
                intent.putExtra("ref", ref);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pictureLoader.cancel(true);
        translator.cancel(true);
    }
}
