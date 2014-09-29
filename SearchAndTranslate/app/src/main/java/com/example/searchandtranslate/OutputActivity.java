package com.example.searchandtranslate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class OutputActivity extends ActionBarActivity {


    public static TextView text;
    public static ListView list;
    public static Context context;
    public String word = "asdfasd";

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_layout);
        word = InputActivity.intent.getStringExtra("word");
        text = (TextView)findViewById(R.id.textView);
        list = (ListView)findViewById(R.id.listView);
        context = this;

        DataLoader.asyncTranslate(word, new DataLoader.MyCallbackString());
        OutputActivity.list.setAdapter(new PicturesAdapter(context, word));
       // DataLoader.asyncLoadPictures(word, new DataLoader.MyCallbackPicture(context));

    }

}
