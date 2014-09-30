package com.pinguinson.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Search extends Activity {

    public static final String QUERY = "query";

    private Intent      searchIntent;
    private EditText    searchField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchField = (EditText) findViewById(R.id.form);
        searchIntent = new Intent(Search.this, Translator.class);
    }

    public void startTranslation(View view) {
        String query = searchField.getText().toString();
        searchIntent.putExtra(QUERY, query);
        startActivity(searchIntent);
    }
}
