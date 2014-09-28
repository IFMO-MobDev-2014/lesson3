package ru.ifmo.md.lesson3.patrikeev_shah.translator;

import java.io.IOException;
import android.os.AsyncTask;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sergey on 28.09.14.
 */
public class TranslationReceiver extends AsyncTask<String, Void, String> {

    private static final String PATH = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final String API_KEY = "trnsl.1.1.20140927T163937Z.dde9f7f711b770f3.8c37c71f85bacc0c8538899b9d2452c4fc150dd1";

    @Override
    protected String doInBackground(String... strings) {
        String requestString = PATH + "?key=" + API_KEY +
                "&lang=" + "en-ru" +
                "&text=" + strings[0];
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(requestString);
        String translatedText = null;
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            String response = httpClient.execute(request, responseHandler);
            try {
                JSONObject responseJson = new JSONObject(response);
                translatedText = responseJson.getJSONArray("text").get(0).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return translatedText;
    }

}
