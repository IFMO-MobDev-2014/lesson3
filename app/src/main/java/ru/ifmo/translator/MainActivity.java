package ru.ifmo.translator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
    public final static String TRANSLATION_RESULT = "ru.ifmo.translator.TRANSLATION_RESULT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, TranslationResultActivity.class);

        Button button = (Button) findViewById(R.id.translateButton);
        final EditText editText = (EditText) findViewById(R.id.wordInput);
        final Context context = this;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString();

                new GetTranslation(context, new ResponseListener() {
                    @Override
                    public void onResponse(String result, Drawable[] images) {
                        intent.putExtra(TRANSLATION_RESULT, result);
                        ImagesProvider.setImages(images);
                        startActivity(intent);
                    }
                }).execute(query);
            }
        });
    }
}
