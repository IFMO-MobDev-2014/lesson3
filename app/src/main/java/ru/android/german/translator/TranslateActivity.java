package ru.android.german.translator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by german on 27.09.14.
 */
public class TranslateActivity extends Activity {
    private Button backButton;
    private GridViewAdapter adapter = null;
    ArrayList<Bitmap> data = new ArrayList<Bitmap>();
    LoadImage[] loadImages = new LoadImage[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.translate_activity);
        GridView gridView = (GridView)findViewById(R.id.gridView);
        adapter = new GridViewAdapter(this, R.layout.grid_item, data);
        gridView.setAdapter(adapter);

        backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            String translate = "Yandex translator doesn't work . . .";
            if (intent.hasExtra("translate")) {
                translate = intent.getStringExtra("translate");
            }
            TextView textView = (TextView)findViewById(R.id.textView);
            textView.setText(translate);
            for (int i = data.size(); i < 10; i++) {
                String url;
                if (intent.hasExtra("img" + i)) {
                    url = intent.getStringExtra("img"+i);
                    loadImages[i] = new LoadImage();
                    loadImages[i].execute(url);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (int i = 0; i < 10; i++) {
            if (loadImages[i] != null) {
                loadImages[i].cancel(true);
            }
        }
    }

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected Bitmap doInBackground(String... args) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return bitmap;
            }
        }
        protected void onPostExecute(Bitmap image) {
            if(image != null){
                data.add(image);
                adapter.notifyDataSetChanged();
                Toast.makeText(TranslateActivity.this, "Download went okay ", Toast.LENGTH_SHORT).show();
            }else{
                data.add(BitmapFactory.decodeResource(getResources(), R.drawable.android_logo));
                adapter.notifyDataSetChanged();
                Toast.makeText(TranslateActivity.this, "Image Does not exist or Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }
}