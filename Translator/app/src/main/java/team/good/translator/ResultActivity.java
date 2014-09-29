package team.good.translator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.TextView;

import team.good.translator.R;

public class ResultActivity extends Activity {

    public TextView wordTr;
    public Button backBut;
    public static final int COUNT = 10;

    public static Bitmap[] strToBitmaps(String str) {
        Bitmap[] bmps = new Bitmap[COUNT];
        AsyncTask<String, Void, String[]> task1 = new SearchImagesTask().execute(str);
        String[] urls = new String[COUNT];
        try {
            urls = task1.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < COUNT; i++) {
            AsyncTask<String, Void, Bitmap> task2 = new DownloadImageTask().execute(urls[i]);
            try {
                bmps[i] = task2.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bmps;
    }


    public static String translate(String text) {
        AsyncTask<String, Void, String> task = new TranslateTask().execute(text);
        String tr;
        try {
            tr = task.get();
            return tr;
        } catch (Exception e) {
            e.printStackTrace();
            return "translate function was crashed";
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        wordTr = (TextView) findViewById(R.id.wordTr);
        backBut = (Button) findViewById(R.id.backBut);
        String word = getIntent().getStringExtra("translate");
        String str = translate(word);
        wordTr.setText(str);
        final Gallery gallery = (Gallery) findViewById(R.id.gallery);
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);
        gallery.setAdapter(new ImageAdaptor(this, strToBitmaps(word), metricsB.widthPixels - 300, 50                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getBack(View w) {
        this.finish();
    }
}
