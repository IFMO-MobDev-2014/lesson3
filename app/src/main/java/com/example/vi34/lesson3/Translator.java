package com.example.vi34.lesson3;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Nikita on 27.09.14.
 */

public class Translator {

    String ans;

    public void execution(String query) {
        new GetTranslation().execute(query);
    }

    public class GetTranslation extends AsyncTask<String, Integer, String> {

        public String doInBackground(String... query) {

            try {
                String s = "https://translate.yandex.net/api/v1.5/tr.json/translate?" + "&lang=en-ru" +
                        "&text=" + query[0] + "&key=trnsl.1.1.20140926T102848Z.42d9d373152a7efe.8c6e65edf8517aece5c562af708f5e5304303f8b";

                URL url = new URL(s);

                URLConnection connection = url.openConnection();
                connection.addRequestProperty("Referer", "ITMO homework app");

                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                JSONObject json = new JSONObject(builder.toString());
                ans = json.getString("text");
                ans = ans.substring(2, ans.length() - 2);
                Log.i("ANS:", ans);

            } catch (Exception e) {
                Log.i("ASYNC", "SOMETHING GOES WRONG:(");
            }

            return ans;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            SecondActivity.translation = ans;
            SecondActivity.russianText.setText(SecondActivity.translation);
        }
    }
}
