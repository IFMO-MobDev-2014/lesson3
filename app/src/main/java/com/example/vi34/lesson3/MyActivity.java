package com.example.vi34.lesson3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MyActivity extends Activity {

    String textToTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstscreen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my, menu);
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

    public boolean problemWithInternetConnection() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected()) {
            Log.i("Connection ", "OK");
            return false;
        } else {
            Log.i("No ", "connection");
            return true;
        }
    }

    public void onClick(View view) {
        if (problemWithInternetConnection()) {
            Intent intent = new Intent(MyActivity.this, ProblemWithConnectionActivity.class);
            startActivity(intent);
        } else {
            EditText textInEnglish = (EditText) findViewById(R.id.editText);
            textToTranslate = textInEnglish.getText().toString();
            Intent intent = new Intent(MyActivity.this, SecondActivity.class);
            intent.putExtra("Text to translate", textToTranslate);
            startActivity(intent);
        }
    }


}
