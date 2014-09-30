package com.pinguinson.translator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Translator extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String translation = getIntent().getStringExtra(Search.QUERY);
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(translation);
    }
}
