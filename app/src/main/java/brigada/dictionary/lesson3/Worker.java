package brigada.dictionary.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URLEncoder;

/**
 * Created by Dmitry on 29.09.2014.
 */
public class Worker extends Activity {
    TextView textView;
    RelativeLayout layout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker);

        Button button = (Button) findViewById(R.id.back);
        textView = (TextView) findViewById(R.id.output);
        textView.setTextSize(50);
        textView.setTextColor(Color.BLACK);
        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        Translater translater = new Translater(word);
        translater.execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class Translater extends AsyncTask<Void, Void, String> {
        String word;

        Translater(String word) {
            this.word = URLEncoder.encode(word);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);
        }

        @Override
        protected String doInBackground(Void... parameter) {
            String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20140929T081453Z.0222651814bd7ed3.4bb093fa3fe9a9174dc234085e79a55cf90d0037&text=" + word + "&lang=en-ru";
            String out;
            try {
                InputStream in = new java.net.URL(url).openStream();
                java.util.Scanner str = new java.util.Scanner(in).useDelimiter("\\A");
                out = str.hasNext() ? str.next() : "";
            } catch (Exception e) {
                return "No connection";
            }
            int a = out.indexOf('[') + 2;
            int b = out.indexOf(']') - 1;
            return out.substring(a, b);
        }
    }
}
