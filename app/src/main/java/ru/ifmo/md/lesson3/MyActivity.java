package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyActivity extends Activity {

    public static boolean condition = true;

    public static String Strin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        findViewById(R.id.button).setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                EditText ed =(EditText) findViewById(R.id.editText);
                Strin = ed.getText().toString();
                Intent intent = new Intent(MyActivity.this, MyActivity_second1.class);
                //intent.putExtra(Strin, Strin);
                startActivity(intent);
                return false;
            }
        });
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
