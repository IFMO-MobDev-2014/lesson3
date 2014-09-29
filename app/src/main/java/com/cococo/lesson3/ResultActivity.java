package com.cococo.lesson3;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ResultActivity extends Activity {
    List<String> urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String trWord = getIntent().getStringExtra("word");
        new TranslateTask(this, trWord).execute();
        new GettingUrlsTask(this, trWord).execute();
    }

    public void setTranslatedText(String res) {
        final TextView txt = (TextView) findViewById(R.id.translatedWord);
        txt.setText(res);
    }

    public void setFoundedImages(Drawable[] imagePack) {
        final ListView view = (ListView) findViewById(R.id.listView);
        view.setAdapter(new ImageAdapter(this, imagePack));
    }

    public void setUrls(List<String> s) {
        urls = s;
    }
}
