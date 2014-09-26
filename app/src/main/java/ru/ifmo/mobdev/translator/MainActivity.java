package ru.ifmo.mobdev.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class MainActivity extends Activity {
    static final String TRANSLATED_INPUT = "ru.ifmo.mobdev.translator.translation";
    Button translateButton;
    Intent intent;
    EditText queryField;
    MainActivity caller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        caller = this;
        intent = new Intent(this, ShowResultsActivity.class);
        queryField = (EditText) findViewById(R.id.query_field);
        translateButton = (Button) findViewById(R.id.translateButton);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Translator(caller).execute();
            }
        });
    }

    public void onTranslation(String s){
        intent.putExtra(TRANSLATED_INPUT, s);
        startActivity(intent);
    }
}
