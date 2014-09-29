package ru.ifmo.mobdev.translator.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

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
    private MainActivity caller;
    private ProgressDialog progressDialog;

    private String lastTranslatedInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        caller = this;
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
                new TranslateWordTask(caller).execute(lastTranslatedInput);
            }
        });
    }

    public void onTranslation(String s){
        intent.putExtra(INPUT, lastTranslatedInput);
        intent.putExtra(TRANSLATED_INPUT, s);
        progressDialog.dismiss();
        startActivity(intent);
    }
}
