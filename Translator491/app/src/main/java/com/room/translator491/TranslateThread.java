package com.room.translator491;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

class TranslateThread implements Runnable {
    public String result;
    private final static String url = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private final static String key = "trnsl.1.1.20140929T052309Z.becc628d2c320343.0f459c24d65e8aa963715f4d5f60ded4f9cf7d49";
    private boolean en_ru = true;
    private String text;
    Thread t;

    TranslateThread(String str, boolean lang) {
        t = new Thread(this, "translate");
        text = str;
        en_ru = lang;
        result = "";
        t.start();
    }

    private String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    private String translate(String text, boolean en_ru) {
        String lang = en_ru ? "en-ru" : "ru-en";
        try {
            String json = readUrl(url + "?key=" + key + "&text=" + Uri.encode(text) + "&lang=" + lang);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("text");
            return jsonArray.getString(0);
        } catch (Exception e) {
            e.printStackTrace();
            return "Oops";
        }
    }

    @Override
    public void run() {
        result = translate(text, en_ru);
    }
}
