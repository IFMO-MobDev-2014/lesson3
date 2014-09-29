package com.abrafcm.translator.translator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TranslateActivity extends Activity {
    private static final String TAG = "TranslateActivity";

    private Button mTranslateButton;
    private EditText mTranslateEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        setupViews();
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void setupViews() {
        mTranslateButton = (Button) findViewById(R.id.button_translate);
        mTranslateEdit = (EditText) findViewById(R.id.edit_translate);

        mTranslateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toTranslate = mTranslateEdit.getText().toString();
                if (!isOnline()) {
                    Toast.makeText(
                            getApplicationContext(),
                            Helper.ERROR_NO_INTERNET,
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                if (validString(toTranslate)) {
                    startResultActivity(toTranslate);
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            Helper.ERROR_EMPTY_STRING,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private boolean validString(String string) {
        return string != null && string.length() > 0;
    }

    private void startResultActivity(String translate) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(Helper.EXTRA_TRANSLATE, translate);
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
