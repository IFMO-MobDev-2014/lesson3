package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
    public static final String EXTRA_MESSAGE = "ru.ifmo.ms.lesson3.WORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendWords(View view) {
        String phrase = ((EditText) findViewById(R.id.words)).getText().toString();
        if (phrase.isEmpty()) {
            Toast myToast = Toast.makeText(getApplicationContext(), getString(R.string.empty_message), Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.CENTER, 0, 0);
            myToast.show();
        } else {
            translate();
        }
    }

    void translate() {
        startPicturesActivity("word");
    }

    void startPicturesActivity(String answer) {
        Intent intent = new Intent(this, PicturesActivity.class);
        intent.putExtra(EXTRA_MESSAGE, answer);
        startActivity(intent);
    }
}
