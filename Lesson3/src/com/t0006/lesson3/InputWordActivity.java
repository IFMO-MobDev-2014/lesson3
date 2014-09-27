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
    public static final String EXTRA_PREV_NAME = "com.t0006.lesson3.InputWordActivity.EXTRA_PREV_NAME";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        if (getIntent().hasExtra(EXTRA_PREV_NAME)) {
            EditText input = (EditText) findViewById(R.id.inputWord);
            input.setText(getIntent().getStringExtra(EXTRA_PREV_NAME));
        }
    }

    public void processButton(View view) {
        switch (view.getId()) {
            case R.id.buttonCancel:
                setResult(RESULT_CANCELED);
                break;
            case R.id.buttonOk:
                Intent intent = new Intent(this, RetrievedContentActivity.class);
                String text = ((EditText) findViewById(R.id.inputWord)).getText().toString();
                intent.putExtra(RetrievedContentActivity.EXTRA_NEW_NAME, text);
                setResult(RESULT_OK, intent);
                break;
        }
        finish();
    }
}