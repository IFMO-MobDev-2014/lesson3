package ru.ifmo.md.lesson3.patrikeev_shah.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class InputActivity extends Activity {

    String wordToTranslate;
    Button translateButton;
    EditText wordField;
    Intent searchIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        translateButton = (Button) findViewById(R.id.translateButton);
        wordField = (EditText) findViewById(R.id.editText);
    }

    public void translateClicked(View view) {
        wordToTranslate = wordField.getText().toString();
        Log.d("Word", "entered " + wordToTranslate);
//        searchIntent = new Intent(this, ResultActivity.class);
    }
}
