package team.good.translator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class EditActivity extends Activity {

    public static Bitmap[] strToBitmaps(String str) {
        Bitmap[] bmps = new Bitmap[10];
        AsyncTask<String, Void, String[]> task1 = new SearchImagesTask().execute(str);
        String[] urls = new String[10];
        try {
            urls = task1.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            AsyncTask<String, Void, Bitmap> task2 = new DownloadImageTask().execute(urls[i]);
            try {
                bmps[i] = task2.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bmps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
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

    public void translate(View  w) {
        Intent intent = new Intent(this, ResultActivity.class);

        startActivity(intent);
    }
}
