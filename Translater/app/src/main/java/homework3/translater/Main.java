package homework3.translater;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anstanasia on 18.01.2015.
 */

public class Main extends Activity {
    public static String originalWord;
    public static String translatedWord;
    public static float x;
    public static List<Bitmap> images = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        x = getApplicationContext().getResources().getDisplayMetrics().density;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.startWord);
        Button button = (Button) findViewById(R.id.translateButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                originalWord = textView.getText().toString();
                textView.setText("", TextView.BufferType.EDITABLE);
                doTheThing();
            }
        });
    }

    void doTheThing() {
        Translator t = new Translator();
        t.execute(originalWord);
        try {
            translatedWord = t.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Searcher s = new Searcher();
        s.execute(originalWord);
        try {
            images = s.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(Main.this, SecondActivity.class);
        intent.putExtra("originalWord", originalWord);
        intent.putExtra("translatedWord", translatedWord);
        startActivity(intent);
    }
}