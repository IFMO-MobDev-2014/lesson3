package com.example.daria.translate;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.daria.translate.Tasks.DownloadImagesTask;
import com.example.daria.translate.Tasks.TranslatorTask;

import java.util.ArrayList;


public class SearchActivity extends Activity {

    DownloadImagesTask download;
    TranslatorTask translate;
    EditText query;
    Button search;
    public int pos = 0;
    public ImageView image;
    ArrayList<Bitmap> images;
    Context context;
    InputMethodManager inputMethodManager;
    ViewSwitcher switcher;
    TextView progress;
    boolean homescreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        switcher = (ViewSwitcher)findViewById(R.id.switcher);
        search = (Button) findViewById(R.id.search_button);
        query = (EditText) findViewById(R.id.word_field);

        progress = (TextView)findViewById(R.id.progress);

        search.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                homescreen = false;
                progress.setVisibility(View.VISIBLE);

                AsyncTask task = new AsyncUpdate().execute();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && !homescreen) {
            switcher.showPrevious();
            homescreen = true;
            progress.setVisibility(View.INVISIBLE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private class AsyncUpdate extends AsyncTask<Void, Void, Void> {

        final String word = query.getText().toString();


        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... v) {
            translate();
            return null;
        }

        @Override
        protected void onPostExecute(Void v){

            image = (ImageView) findViewById(R.id.imageView);
            ((TextView) findViewById(R.id.word_query)).setText(word);
            try {
                ((TextView) findViewById(R.id.translation)).setText(translate.get());
            } catch (Exception e) {
                ((TextView) findViewById(R.id.translation)).setText("Error");
            }

            try {
                images = download.get();
                image.setImageBitmap(images.get(pos));
                image.setOnClickListener(new ImageView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pos = (pos + 1) % 10;
                        image.setImageBitmap(images.get(pos));
                    }
                });

            } catch (Exception e) {
                image.setImageResource(R.drawable.error);
            }

            switcher.showNext();

        }

        public void translate() {

            download = new DownloadImagesTask();
            download.execute(word);

            translate = new TranslatorTask(word);
            translate.execute();

        }

        protected void onProgressUpdate(){
        }
    }
}
