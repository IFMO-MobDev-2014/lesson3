package ru.ifmo.translator;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;


public class TranslationResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_result);

        String result = getIntent().getStringExtra(MainActivity.TRANSLATION_RESULT);
        final TextView translation = (TextView) findViewById(R.id.translationResult);
        translation.setText(result);

        Drawable[] images = ImagesProvider.getImages();
        ImagesAdapter adapter = new ImagesAdapter(this, images);

        ListView view = (ListView) findViewById(R.id.gallery);

        view.setAdapter(adapter);
    }
}
