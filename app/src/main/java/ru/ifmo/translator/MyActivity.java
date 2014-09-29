package ru.ifmo.translator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class MyActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button button = (Button) findViewById(R.id.translateButton);
        final TextView translation = (TextView) findViewById(R.id.translationResult);
        final ImageView image = (ImageView) findViewById(R.id.image);
        final EditText editText = (EditText) findViewById(R.id.wordInput);
        final ImageLoader imageLoader = new ImageLoader();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editText.getText().toString(); // TODO: not sure toString method
                translation.setText(TranslationLoader.translate(query));
//                 TODO: Uncomment this line when testing this class
//                image.setImageDrawable(imageLoader.loadImage(query));
                image.setImageDrawable(getResources().getDrawable(R.drawable.cat));
            }
        });
    }
}
