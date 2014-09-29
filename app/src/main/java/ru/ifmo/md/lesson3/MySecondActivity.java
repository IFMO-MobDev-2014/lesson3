package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by shambala on 27.09.2014.
 */
public class MySecondActivity extends Activity {

    ImageView[] imageView;
    String word;
    ArrayList<Bitmap> bmp;
    FindAndDownloadImagesTask finder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        Intent in = getIntent();
        word = in.getStringExtra("word");
        finder = new FindAndDownloadImagesTask();
        imageView = new ImageView[10];

        imageView[0] = (ImageView) findViewById(R.id.imageView);
        imageView[1] = (ImageView) findViewById(R.id.imageView2);
        imageView[2] = (ImageView) findViewById(R.id.imageView3);
        imageView[3] = (ImageView) findViewById(R.id.imageView4);
        imageView[4] = (ImageView) findViewById(R.id.imageView5);
        imageView[5] = (ImageView) findViewById(R.id.imageView6);
        imageView[6] = (ImageView) findViewById(R.id.imageView7);
        imageView[7] = (ImageView) findViewById(R.id.imageView8);
        imageView[8] = (ImageView) findViewById(R.id.imageView9);
        imageView[9] = (ImageView) findViewById(R.id.imageView10);

        try {
            finder.execute(word);
            bmp = finder.get();
            if (bmp.size() != 0) {
                for (int i = 0; i < bmp.size(); i++) {
                    imageView[i].setImageBitmap(bmp.get(i));
                }
            }
        }
        catch (Exception e){}

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
