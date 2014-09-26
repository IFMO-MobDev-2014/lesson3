package com.example.pva701.lesson3;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    private boolean isEnglish(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }

    private boolean englishWord(String word) {
        for (int i = 0; i < word.length(); ++i)
            if (!isEnglish(word.charAt(i)))
                return false;
        return true;
    }

    public void buttonTranslateClick(View v) {
        EditText editText = (EditText)findViewById(R.id.teInputWord);
        String word = editText.getText().toString();

        if (word.isEmpty()) {
            Toast.makeText(this, "Empty word", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!englishWord(word)) {
            Toast.makeText(this, "Wrong word", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isOnline()) {
            Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }
}
