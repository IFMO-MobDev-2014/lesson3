package ru.ifmo.md.lesson3.patrikeev_shah.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by sergey on 27.09.14.
 */
public class ResultActivity extends Activity {

    Intent callerIntent;

    public ResultActivity() {
        callerIntent = getIntent();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }


    public void backButtonClicked(View view) {

    }
}
