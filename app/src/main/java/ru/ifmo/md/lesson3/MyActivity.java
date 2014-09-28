package ru.ifmo.md.lesson3;

/**
 * Created by 107476 on 25.09.2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MyActivity extends Activity {
    ImageView animView;
    Button back;
    TextView eng;
    TextView rus;
    EditText input;
    Translator translate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        animView = (ImageView)findViewById(R.id.imageView2);
        final Button translator = (Button)findViewById(R.id.button);
        back = (Button)findViewById(R.id.button2);
        eng = (TextView)findViewById(R.id.textView);
        rus = (TextView)findViewById(R.id.textView5);
        input = (EditText) findViewById(R.id.editText);
        final Animation falling = AnimationUtils.loadAnimation(this, R.anim.falling);
        final Animation rising = AnimationUtils.loadAnimation(this, R.anim.rising);
        translator.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                eng.setText(input.getText());

                String word = input.getText().toString();
                translate = new Translator(word);
                translate.execute();
                try {
                    rus.setText(translate.get());
                } catch (Exception e) {
                    rus.setText("Oops, something goes wrong");
                }

                animView.startAnimation(falling);
                back.startAnimation(falling);
                eng.startAnimation(falling);
                rus.startAnimation(falling);

                animView.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
                eng.setVisibility(View.VISIBLE);
                rus.setVisibility(View.VISIBLE);
            }
        });
        back.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                animView.startAnimation(rising);
                back.startAnimation(rising);
                eng.startAnimation(rising);
                rus.startAnimation(rising);

                animView.setVisibility(View.INVISIBLE);
                back.setVisibility(View.INVISIBLE);
                eng.setVisibility(View.INVISIBLE);
                rus.setVisibility(View.INVISIBLE);
            }
        });
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


    public void onClick(View view) {
        Intent intent = new Intent(this, MySecondActivity.class);
        startActivity(intent);
    }

}
