package com.example.pva701.lesson3;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonTranslateClick(View v) {
        EditText editText = (EditText)findViewById(R.id.teInputWord);
        String word = editText.getText().toString();
        if (word.isEmpty())
            return;
        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }
}
