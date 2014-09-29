package com.cococo.lesson3;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TranslateTask extends AsyncTask<String, Void, JSONObject> {
    Context context;
    String word;
    final String key = "trnsl.1.1.20140928T150541Z.b4f933a541d1ff2f.6f09a278c16eb5387d743c5a5bab8a0d3e9f444c";
    final String url = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    final String lang = "en-ru";

    public TranslateTask(Context context,String word) {
        super();
        this.context = context;
        this.word = word;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        JSONParser jParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("key", key));
        params.add(new BasicNameValuePair("text", word));
        params.add(new BasicNameValuePair("lang", lang));
        return jParser.makeHttpRequest(url, params);
    }


    @Override
    protected void onPostExecute(JSONObject jsonData) {
        if (jsonData != null) {
            String translatedText = "";
            try {
                translatedText = jsonData.getString("text");
                ((ResultActivity) context).setTranslatedText(translatedText.substring(2,translatedText.length()-2));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ((ResultActivity) context).setTranslatedText("ERROR");
        }
    }

}