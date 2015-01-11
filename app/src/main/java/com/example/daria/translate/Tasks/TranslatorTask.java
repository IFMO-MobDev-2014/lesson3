package com.example.daria.translate.Tasks;

import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URLEncoder;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by daria on 02.01.15.
 */
public class TranslatorTask extends AsyncTask<Void, Void, String>{

    String word;
    final String URL = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=";
    final String KEY = "trnsl.1.1.20150101T222209Z.1ec6d705379f0a05.b48f408f7e1b8006ad914322c1b50bade642de4f";

    public TranslatorTask(String word) {
        this.word = URLEncoder.encode(word);
    }
    @Override
    protected String doInBackground(Void... params) {
        String text;
        try {
            InputStream in = new URL(URL + KEY + "&text=" + word + "&lang=en-ru").openStream();
            Scanner scanner = new Scanner(in).useDelimiter("\\A");
            if (scanner.hasNext()) {
                text = scanner.next();
            }
            else {
                text = "";
            }
        } catch (Exception e) {
            return "Network Connection Failed";
        }

        return text.substring(text.indexOf('[') + 2, text.indexOf(']') - 1);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
