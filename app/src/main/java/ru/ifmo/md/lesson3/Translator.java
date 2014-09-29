package ru.ifmo.md.lesson3;


import android.os.AsyncTask;
import java.io.InputStream;
import java.net.URLEncoder;


public class Translator extends AsyncTask<Void, Void, String> {
    String word;
    Translator(String word) {
        this.word = URLEncoder.encode(word);
    }

    public String onPostExecute(String... result) {
        super.onPostExecute(result[0]);
        return result[0];
    }

    @Override
    public String doInBackground(Void... parameter) {
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20140925T190201Z.b735e376a5f23f41.f3f69178b4dee8d08132140f11d5084b72137077&text=" + word + "&lang=en-ru";
        String text;
        try {
            InputStream in = new java.net.URL(url).openStream();
            java.util.Scanner str = new java.util.Scanner(in).useDelimiter("\\A");
            text = str.hasNext() ? str.next() : "";

        } catch (Exception e) {
            return "can't connect to internet";
        }
        int i1 = text.indexOf('[') + 2;
        int i2 = text.indexOf(']') - 1;
        return text.substring(i1,i2);
    }
}