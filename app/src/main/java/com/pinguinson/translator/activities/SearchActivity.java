package com.pinguinson.translator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.pinguinson.translator.R;

/**
 * Created by pinguinson on 12.10.2014.
 */
public class SearchActivity extends Activity {

    public static final String QUERY = "QUERY";

    private Intent intent;
    private EditText queryField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        queryField = (EditText) findViewById(R.id.search_field);
    }

    public void startTranslation(View view) {
        intent = new Intent(this, ResultActivity.class);
        String query = queryField.getText().toString();
        intent.putExtra(QUERY, query);
        startActivity(intent);
    }
}
