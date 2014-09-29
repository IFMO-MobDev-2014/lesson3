package ru.ifmo.mobdev.translator.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ru.ifmo.mobdev.translator.activities.MainActivity;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class TranslateWordTask extends AsyncTask<String, Void, String> {
    MainActivity mainScreen;
    private static final String address = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    private static final String key = "trnsl.1.1.20140926T123039Z.ac6cd94cc722d" +
            "474.ce67e9d60a3a9144cee56c5eea5f4c1f2d4fdfbe";

    public TranslateWordTask(MainActivity resultScreen) {
        this.mainScreen = resultScreen;
    }

    @Override
    protected String doInBackground(String... strings) {
        String request = address + "key=" + key + "&text=" + strings[0] +
                "&lang=ru" + "&format=plain" + "&options=1";
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response;
        String translation = null;
        try {
            response = httpClient.execute(new HttpGet(request));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                String responseString = out.toString();
                translation = parseJSON(responseString);
            } else {
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException el) {
            Log.e("ERROR", "ClientProtocolException in TranslateWordTask");
        } catch (IOException e) {
            Toast.makeText(mainScreen, "Error translating word, check your Internet connection", Toast.LENGTH_LONG)
                    .show();
            Log.e("ERROR", "IOException in TranslateWordTask");
        }
        return translation;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mainScreen.onTranslation(s);
    }

    private String parseJSON(String responseString) {
        String translation = null;
        try {
            JSONObject answer = new JSONObject(responseString);
            translation = answer.optString("text");
            translation = translation.substring(2, translation.length() - 2);
        } catch (JSONException ex) {
            Log.e("ERROR", "While parsing JSON answer");
        }
        return translation;
    }
}
