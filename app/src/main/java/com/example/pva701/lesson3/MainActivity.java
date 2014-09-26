package com.example.pva701.lesson3;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            Log.i("INPUT ERROR", "Empty");
            return;
        }
        if (!englishWord(word)) {
            Log.i("INPUT ERROR", "Not english word");
            return;
        }

        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }
}
