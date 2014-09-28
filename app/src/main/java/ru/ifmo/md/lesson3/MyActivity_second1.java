package ru.ifmo.md.lesson3;


import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MyActivity_second1 extends Activity {

    public static boolean condition = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_1);
        //String str =getIntent().getStringExtra(MyActivity.Strin);
        findViewById(R.id.button_left1).setOnTouchListener(new Button.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                condition = !condition;
                return false;
            }
        });
    }
}