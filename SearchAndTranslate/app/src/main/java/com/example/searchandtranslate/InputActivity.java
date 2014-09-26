package com.example.searchandtranslate;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class InputActivity extends ActionBarActivity {

    public static EditText input_text;
    public static Button button;
    public static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_layout);

        input_text = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(view);
            }
        });

    }

    void changeActivity(View view){
        intent = new Intent(InputActivity.this, OutputActivity.class);
        intent.putExtra("word", input_text.getText().toString());
        startActivity(intent);
    }
}
