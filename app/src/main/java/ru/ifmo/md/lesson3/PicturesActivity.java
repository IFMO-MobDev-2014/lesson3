package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class PicturesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        final String message = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);
        ((TextView) findViewById(R.id.tvView)).setText(message);
    }
}
