package com.example.pva701.lesson3;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import odeenpva.lesson3.HistoryItem;
import odeenpva.lesson3.HistorySource;


public class MainActivity extends Activity {
    private HistorySource database;
    private ArrayAdapter <String> adapter;
    private ArrayList <String> historyWords;

    private EditText editText;
    private ListView lvHistory;

    private boolean isEnglish(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z';
    }

    private boolean englishWord(String word) {
        for (int i = 0; i < word.length(); ++i)
            if (!isEnglish(word.charAt(i)))
                return false;
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.teInputWord);
        lvHistory = (ListView)findViewById(R.id.lvHistory);
        lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String word = ((TextView)view).getText().toString();
                if (!check(word))
                    return;
                editText.setText("");
                translate(word);
            }
        });

        database = new HistorySource(this);
        try {
            database.open();
        } catch (SQLException e) {
            Log.i("SQL", "Exception");
            e.printStackTrace();
        }
        ArrayList <HistoryItem> history = database.getAllWords();
        Collections.reverse(history);
        historyWords = new ArrayList<String>();
        for (int i = 0; i < history.size(); ++i)
            historyWords.add(history.get(i).getWord());

        adapter = new ArrayAdapter<String>(this, R.layout.item, historyWords);
        lvHistory.setAdapter(adapter);
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

    private boolean check(String word) {
        if (word.isEmpty()) {
            Toast.makeText(this, "Empty word", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!englishWord(word)) {
            Toast.makeText(this, "Wrong word", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isOnline()) {
            Toast.makeText(this, "Check your Internet connection", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void buttonTranslateClick(View v) {
        String word = editText.getText().toString();
        if (!check(word))
            return;
        editText.setText("");
        translate(word);
    }

    private void translate(String word) {
        database.insertWord(word);
        historyWords.add(0, word);
        adapter.notifyDataSetChanged();

        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("word", word);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        database.close();
    }
}
