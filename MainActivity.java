package com.room.translator491;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.translator491.R;


public class MainActivity extends Activity implements OnClickListener {

    EditText InputQuery;
    Button ShowImage;
    Button To;
    Button From;
    boolean en_ru = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        InputQuery = (EditText) findViewById(R.id.inputQuery);
        ShowImage = (Button) findViewById(R.id.buttonShowImage);
        From = (Button) findViewById(R.id.from);
        To = (Button) findViewById(R.id.to);
        ShowImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("query", InputQuery.getText().toString());
        System.out.println(InputQuery.getText().toString());
        startActivityForResult(intent, 42);
    }

    public void translate(View view) throws InterruptedException {
        EditText editText = (EditText) findViewById(R.id.inputQuery);
        TextView textView = (TextView) findViewById(R.id.result);
        TranslateThread translateThread = new TranslateThread(editText.getText().toString(), en_ru);
        while (translateThread.result == "") {
            Thread.sleep(20);
        }
        textView.setText(translateThread.result);
    }

    public void swapLanguage(View view) {
        en_ru ^= true;
        if (en_ru) {
            From.setText("english");
            To.setText("russian");
        } else {
            From.setText("russian");
            To.setText("english");
        }
    }
}