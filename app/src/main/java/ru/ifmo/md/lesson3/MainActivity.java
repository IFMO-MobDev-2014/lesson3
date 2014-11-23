package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    public static String string;
    public static String translate;
    public static String text;
    public EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        editText = (EditText) findViewById(R.id.editText);
    }

    public void onTranslateResponse(String response) {
        translate = response;
        Log.i("translate",translate);
    }

    public void onPress(View view) {
        try {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("text", editText.getText().toString());
            startActivity(intent);
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }

    }


}
