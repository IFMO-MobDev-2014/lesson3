package com.abrafcm.translator.translator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class TranslateActivity extends Activity {
    private static final String TAG = "TranslateActivity";

    private Button mTranslateButton;
    private EditText mTranslateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        initViews();
    }

    private void initViews() {
        mTranslateButton = (Button) findViewById(R.id.button_translate);
        mTranslateEdit = (EditText) findViewById(R.id.edit_translate);

        mTranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toTranslate = mTranslateEdit.getText().toString();
                startResultActivity(toTranslate);
            }
        });
    }

    private void startResultActivity(String translate) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(Helper.TRANSLATE, translate);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.translate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
