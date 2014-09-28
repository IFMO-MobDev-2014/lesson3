package ru.ifmo.md.lesson3.patrikeev_shah.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sergey on 27.09.14.
 */
public class ResultActivity extends Activity {

    private Button backButton;
    private String initialWord;
    private String translatedWord;
    private TextView translationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        backButton = (Button) findViewById(R.id.backButton);
        translationTextView = (TextView) findViewById(R.id.translationTextView);

        initialWord = getIntent().getStringExtra(getPackageName() + ".initialWord");
        translatedWord = getIntent().getStringExtra(getPackageName() + ".translatedWord");

        translationTextView.setText(initialWord + " = " + translatedWord);
    }

    public void backButtonClicked(View view) {
        Log.d("Back button", "clicked");
        finish();
    }



}
