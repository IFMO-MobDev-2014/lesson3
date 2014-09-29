package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public static boolean condition = true;
    public String ret;
    public static String strin;
    public static String translate;
    public static String text;
    public EditText ed;
    public static ImageView imageView;
    public TextView textView;
    public static Bitmap images[] ;
    public static String urls[] = new String[10];
    ImageSearch imageSearch;
    public static int i = 0;
    public static Bitmap current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        ed = (EditText) findViewById(R.id.editText);

        images = new Bitmap[10];
    }

    public void onButtonTranslatePushed(View view) {
        Log.i("","onButtonTranslatePushed");
        TranslateAsyncTask task = new TranslateAsyncTask(this);
        task.execute(text);
    }

    public void onTranslateResponse(String response) {
        translate = response;
        Log.i("translate",translate);
    }

    public void tra(View view) {
        try {
            strin = ed.getText().toString();
            //Log.i("asdas", ed.getText().toString());
            Thread thread = new Thread(new Translate(strin));
            thread.start();
            Thread thread2 = new Thread(new ImageSearch(strin));
            //Log.i("", urls[0]);
            Thread thread3 = new Thread(new LoadImage());
            thread2.start();
            setContentView(R.layout.layout2);
            Log.i("shit", strin);
            textView = (TextView)findViewById(R.id.textView);
            thread.join();
            thread2.join();
            Log.i("", Integer.toString(urls.length));
            for (int i = 0 ; i < urls.length; i++)
                Log.i("shi~", urls[i]);
            imageView = (ImageView)findViewById(R.id.imageView);
            thread3.start();
            thread3.join();
            textView.setText(strin);

            Log.i("", images[0].toString());
            imageView.setImageBitmap(images[0]);
        }
        catch (Exception e)
        {
            Log.i("shit", "happened");
        }

    }
    public void nextImage(View view) {
        i = (i + 1) % urls.length;
        imageView.setImageBitmap(images[i]);

    }

  /*  @Override
    public void onResume() {
        super.onResume();
        whirl.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        whirl.pause();*/
}
