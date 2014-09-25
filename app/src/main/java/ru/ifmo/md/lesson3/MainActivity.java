package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
    public final static String EXTRA_QUERY = "ru.ifmo.md.lesson3.QUERY";
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText = (EditText) findViewById(R.id.editText);
    }

    public void translate(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        String query = editText.getText().toString();
        intent.putExtra(EXTRA_QUERY, query);
        startActivity(intent);
    }
}