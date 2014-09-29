package ru.ifmo.mobdev.translator.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.ifmo.mobdev.translator.R;
import ru.ifmo.mobdev.translator.tasks.TranslateWordTask;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class MainActivity extends Activity {
    static final String INPUT = "ru.ifmo.mobdev.translator.input";
    static final String TRANSLATED_INPUT = "ru.ifmo.mobdev.translator.translation";
    private Intent intent;
    private EditText queryField;
    private ProgressDialog progressDialog;
    private String lastTranslatedInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, ShowResultsActivity.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading translation");
        queryField = (EditText) findViewById(R.id.query_field);
        final Button translateButton = (Button) findViewById(R.id.translateButton);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                lastTranslatedInput = queryField.getText().toString();
                new TranslateWordTask(MainActivity.this).execute(lastTranslatedInput);
            }
        });

        queryField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent event) {
                if (i == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    lastTranslatedInput = queryField.getText().toString();
                    progressDialog.show();
                    new TranslateWordTask(MainActivity.this).execute(lastTranslatedInput);
                }
                return true;
            }
        });
    }

    public void onTranslation(String s) {
        intent.putExtra(INPUT, lastTranslatedInput);
        intent.putExtra(TRANSLATED_INPUT, s);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        progressDialog.dismiss();
    }

    public void onTranslationError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Unexpected error occurred. " +
                        "Please, check your Internet connection.", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
