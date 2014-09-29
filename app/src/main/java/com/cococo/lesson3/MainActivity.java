package com.cococo.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

    Button btnTranslate;
    EditText editWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        editWord = (EditText) findViewById(R.id.editWord);
        btnTranslate = (Button) findViewById(R.id.btnTranslate);
        btnTranslate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTranslate:
                Intent intent = new Intent(this, ResultActivity.class);
                intent.putExtra("word", editWord.getText().toString());
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
