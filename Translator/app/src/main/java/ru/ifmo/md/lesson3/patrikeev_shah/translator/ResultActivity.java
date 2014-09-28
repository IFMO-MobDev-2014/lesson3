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
    private ArrayList<Bitmap> bitmaps = null;
    private Button backButton;
    private String initialWord;
    private String translatedWord;
    private TextView translationTextView;

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

        bitmaps = getImages();
        ImageAdapter imageAdapter = new ImageAdapter(this, bitmaps);

        gridView.setAdapter(imageAdapter);
    }

    public void backButtonClicked(View view) {
        finish();
    }

    private ArrayList<Bitmap> getImages() {
        if (loaderUrls == null || loaderUrls.getStatus().equals(AsyncTask.Status.FINISHED)) {
            loaderUrls = new UrlsImagesReceiver();
            loaderUrls.execute(initialWord);
        }

        if (loaderImages == null ||
                loaderImages.getStatus().equals(AsyncTask.Status.FINISHED)) {
            loaderImages = new ImagesReceiver();
            try {
                loaderImages.execute(loaderUrls.get());
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }
        }

        try {
            return loaderImages.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }


}





