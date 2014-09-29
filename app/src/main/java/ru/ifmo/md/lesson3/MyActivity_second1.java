package ru.ifmo.md.lesson3;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class MyActivity_second1 extends Activity {

    public static int i = 0;
    public static int[] c = {
            //надо сохранять и удалять картинку в ресурсы, сохранять можно в том же месте где производим перевод
            //а удалять при нажатии back
            R.drawable.img0,
            R.drawable.img1,
            R.drawable.img2,
            R.drawable.img3,
            R.drawable.img4,
            R.drawable.img5,
            R.drawable.img6,
            R.drawable.img7,
            R.drawable.img8,
            R.drawable.img9,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String str =getIntent().getStringExtra("String");


        //тут надо получить перевод str и положить его в str

    }}