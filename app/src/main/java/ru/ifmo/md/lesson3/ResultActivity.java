package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

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
        @Override
        protected String doInBackground(String[] strings) {
            String input = strings[0];
            Translator translator;
            if (input.matches("[А-я\\s\\d]+"))
                translator = new Translator(Translator.TranslateDirection.RussianToEnglish);
            else translator = new Translator(Translator.TranslateDirection.EnglishToRussian);
            return translator.translate(input);
        }

        protected void onPostExecute(String result) {
            textView.setText(result);
        }
    }
/*


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
    }*/
}

