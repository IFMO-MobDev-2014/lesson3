package com.example.searchandtranslate;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;



public class OutputActivity extends ActionBarActivity {

    public static TextView text;
    public static ListView list;
    public static Context context;
    public String word = "";

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
        list.setDivider(getResources().getDrawable(android.R.color.transparent));
        context = this;

        DataLoader.asyncTranslate(word, new DataLoader.MyCallbackString(getApplicationContext()));
        OutputActivity.list.setAdapter(new PicturesAdapter(context, word));
    }
}
