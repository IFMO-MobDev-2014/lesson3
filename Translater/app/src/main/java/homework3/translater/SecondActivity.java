package homework3.translater;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anstanasia on 18.01.2015.
 */
public class SecondActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);

        Intent intent = getIntent();

        TextView t = (TextView) findViewById(R.id.originalWord);
        String originalWord = intent.getStringExtra("originalWord");
        t.setText(originalWord);

        t = (TextView) findViewById(R.id.translatedWord);
        String translatedWord = intent.getStringExtra("translatedWord");
        t.setText(translatedWord);

        GridView gridview = (GridView) findViewById(R.id.gridView);
        gridview.setAdapter(new ImageAdapter(this, Main.images));
    }
}
