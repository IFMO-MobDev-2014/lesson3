package com.cococo.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        new TranslateTask(this, intent.getStringExtra("word")).execute();
        new DownloadingImagesTask(this).execute();
    }

    public void setTranslatedText(String res) {
        final TextView txt = (TextView) findViewById(R.id.translatedWord);
        txt.setText(res);
    }

    public void setFoundedImages(Drawable[] imagePack) {
        final ListView view = (ListView) findViewById(R.id.listView);
        view.setAdapter(new ImageAdapter(this, imagePack));
    }
}
