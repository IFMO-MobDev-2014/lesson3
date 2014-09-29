package ru.android.german.translator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by german on 26.09.14.
 */

//dict.1.1.20140927T204944Z.38e0bd765b588895.e4a4b28725b82c0b6341b70dc3fa8f8e84c6fe72

public class MainActivity extends Activity {
    private Button translateButton;
    private boolean translateButtonWasClicked = false;

    public void startNewActivity(String translate) {
        Intent intent = new Intent(MainActivity.this, TranslateActivity.class);
        intent.putExtra("translate", translate);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Context context = this;
        translateButtonWasClicked = false;
        translateButton = (Button)findViewById(R.id.translteButton);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (translateButtonWasClicked) return;
                translateButtonWasClicked = true;
                EditText editText = (EditText)findViewById(R.id.inputWord);
                String input = editText.getText().toString();
                if (input.length() != 0) {
                    TranslatorAPI api = new TranslatorAPI();
                    api.exec(context, input);
                    translateButtonWasClicked = false;
                } else {
                    translateButtonWasClicked = false;
                }
            }
        });
    }


}