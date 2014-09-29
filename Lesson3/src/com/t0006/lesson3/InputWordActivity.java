package com.t0006.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by dimatomp on 27.09.14.
 */
public class InputWordActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

    public void processButton(View view) {
        if (view.getId() == R.id.buttonOk) {
            Intent intent = new Intent(this, RetrievedContentActivity.class);
            String text = ((EditText) findViewById(R.id.inputWord)).getText().toString();
            intent.putExtra(RetrievedContentActivity.EXTRA_SET_WORD, text);
            startActivity(intent);
        } else
            finish();
    }
}