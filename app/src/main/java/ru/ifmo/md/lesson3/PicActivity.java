package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.*;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by nagibator2005 on 2014-10-01.
 */
public class PicActivity extends Activity {
    final static String key =
            "trnsl.1.1.20141001T131234Z.d0d12c54ce935713.b921000e185119d983488e3ae0ee12d8da0cf1ac";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);

        Intent intent = getIntent();
        final String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Here should be checking for internet connection. TODO.

        // Translate a message from previous activity.
        translate(message);

    }

    public void translate(final String str) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + key +
                "&lang=en-ru&text=" + str, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                TextView textView = (TextView) findViewById(R.id.translated_word);
                textView.setTextColor(Color.rgb(204, 129, 29));
                textView.setText("Pending...");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                JSONObject resp = null;
                String translatedWord = null;
                try {
                    resp = new JSONObject(new String(response));
                    JSONArray arrayWord = resp.getJSONArray("text");
                    translatedWord = (String) arrayWord.get(0);
                } catch (JSONException e) {
                    TextView textView = (TextView) findViewById(R.id.translated_word);
                    textView.setTextColor(Color.RED);
                    textView.setText("Failed");
                    Log.i("Error in parsing JSON response: ", e.toString());
                }
                TextView textView = (TextView) findViewById(R.id.translated_word);
                textView.setTextColor(Color.rgb(0, 153, 204));
                textView.setText(translatedWord != null ? translatedWord : str);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                TextView textView = (TextView) findViewById(R.id.translated_word);
                textView.setTextColor(Color.RED);
                textView.setText("Failed");
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }
}

