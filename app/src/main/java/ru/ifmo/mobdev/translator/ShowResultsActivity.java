package ru.ifmo.mobdev.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class ShowResultsActivity extends Activity {
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);
        final Intent caller = getIntent();
        final TextView translationField = (TextView) findViewById(R.id.translationField);
        String translation = caller.getStringExtra(MainActivity.TRANSLATED_INPUT);
        if (translation == null) {
            translationField.setText(R.string.translationError);
        } else {
            translationField.setText(caller.getStringExtra(MainActivity.TRANSLATED_INPUT));
        }
        returnButton = (Button) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
