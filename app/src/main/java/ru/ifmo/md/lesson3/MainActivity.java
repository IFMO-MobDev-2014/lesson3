package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    public static boolean condition = true;

    public static String Strin;
    public static String translate;
    public static String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        findViewById(R.id.button).setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                EditText ed =(EditText) findViewById(R.id.editText);
                text = ed.getText().toString();
                onButtonTranslatePushed(view);
                Intent intent = new Intent(MainActivity.this, MyActivity_second1.class);
                intent.putExtra(Strin, translate);
                startActivity(intent);
                return false;
            }
        });
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
