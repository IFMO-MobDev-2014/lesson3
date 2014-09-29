package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
    // TODO add logging
    ProgressDialog progress;
    public final static String TRANSLATOR_RESPONSE = "translatorResponse";
    public final static String MAIN_QUERY = "mainQuery";
    Intent intent;
    EditText editText;
    Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        resources = getResources();
        editText = (EditText) findViewById(R.id.editText);
        intent = new Intent(this, ResultActivity.class);
    }

    public void onButtonTranslatePushed(View view) {
        progress = new ProgressDialog(this);
        progress.setTitle(resources.getString(R.string.loading));
        progress.setMessage(resources.getString(R.string.loading_trns));
        progress.show();
        String query = editText.getText().toString();
        intent.putExtra(MAIN_QUERY, query);
        new FindTranslationTask(this).execute(query);
    }

    public void onTranslateResponse(String response) {
        intent.putExtra(TRANSLATOR_RESPONSE, response);
        progress.dismiss();
        startActivity(intent);
    }

    public void onTranslateFail() {
        //TODO: TODO TODO
    }
}