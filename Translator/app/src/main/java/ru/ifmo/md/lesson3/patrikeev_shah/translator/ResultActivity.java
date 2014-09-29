package  ru.ifmo.md.lesson3.patrikeev_shah.translator;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by sergey on 27.09.14.
 */
public class ResultActivity extends Activity {

    Intent callerIntent;

    private ImagesReceiver loaderImages = null;
    private UrlsImagesReceiver loaderUrls = null;
    private GridView gridView = null;
    private Button backButton;
    private String initialWord;
    private String translatedWord;
    private TextView translationTextView;
    private ImageAdapter imageAdapter;

     public ResultActivity() {
        callerIntent = getIntent();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        backButton = (Button) findViewById(R.id.backButton);
        translationTextView = (TextView) findViewById(R.id.translationTextView);

        initialWord = getIntent().getStringExtra(getPackageName() + ".initialWord");
        translatedWord = getIntent().getStringExtra(getPackageName() + ".translatedWord");

        translationTextView.setText(initialWord + " = " + translatedWord);

        gridView = (GridView)findViewById(R.id.grid_view);

        imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        getImages();
    }

    public void backButtonClicked(View view) {
        finish();
    }

    private void getImages() {
        loaderUrls = new UrlsImagesReceiver();
        loaderUrls.execute(initialWord);

        loaderImages = new ImagesReceiver(imageAdapter);

        try {
            loaderImages.execute(loaderUrls.get());
        } catch (InterruptedException | ExecutionException e) {
        }
    }

}





