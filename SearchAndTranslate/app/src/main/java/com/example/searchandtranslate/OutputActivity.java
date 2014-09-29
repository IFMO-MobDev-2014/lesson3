package com.example.searchandtranslate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;


public class OutputActivity extends ActionBarActivity {


    public static TextView text;
    public static GridView grid;
    public static Context context;
    public String word = "asdfasd";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(OutputActivity.this, InputActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.output_layout);
        word = InputActivity.intent.getStringExtra("word");
        text = (TextView)findViewById(R.id.textView);
        grid = (GridView)findViewById(R.id.gridView);
        context = this;

        DataLoader.asyncTranslate(word, new DataLoader.MyCallbackString());
        OutputActivity.grid.setAdapter(new PicturesAdapter(context));
       // DataLoader.asyncLoadPictures(word, new DataLoader.MyCallbackPicture(context));

    }

}
