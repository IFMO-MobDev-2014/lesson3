package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ResultActivity extends Activity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = (TextView) findViewById(R.id.textView);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_QUERY);
        new FindTranslationTask().execute(message);


    }

    private class FindTranslationTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... strings) {
            String urlTemplate = "https://translate.yandex.net/api/v1.5/tr/translate?key=%s&lang=%s&text=%s";
            String key = "trnsl.1.1.20140924T160000Z.13a5048fefdf6ede.90e14a1a1fb4eb21cb6ffad6e8cd4b1bf0051860";
            String lang = "en-ru";
            String urlString = String.format(urlTemplate, key, lang, strings[0]);
            try {
                URL url = new URL(urlString);
                URLConnection conn = url.openConnection();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(conn.getInputStream());

                Node node = doc.getElementsByTagName("text").item(0);
                String result = node.getTextContent();
                return result;
            } catch (Exception e) {
                Log.e("error", "", e);
                return e.toString();
            }
        }

        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }



    private class FindImageTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... strings) {
            String urlTemplate = "https://wiki.videolan.org/images/Caption_%n.png";
            try {
                for (int i=0; i<10; i++) {
                    String urlString = String.format(urlTemplate, key, lang, strings[0]);
                    URL url = new URL(urlString);
                    URLConnection conn = url.openConnection();

                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(conn.getInputStream());

                    Node node = doc.getElementsByTagName("text").item(0);
                    String result = node.getTextContent();
                }
                return result;
            } catch (Exception e) {
                Log.e("error", "", e);
                return e.toString();
            }
        }

        protected void onPostExecute(String[] result) {
            textView.setText(result[0]);
        }
    }
}

