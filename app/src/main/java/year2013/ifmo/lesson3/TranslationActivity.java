package year2013.ifmo.lesson3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TranslationActivity extends Activity {

    String phrase;
    String result;
    Translation myTranslation;

    public boolean hasInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        if (netInfo == null) {
            return false;
        }
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected()) {
                    return true;
                }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected()) {
                    return true;
                }
        }
        return false;
    }

    public final static String EXTRA_MESSAGE = "year2013.ifmo.lesson3.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translation);
    }

    public void sendWords(View view) {
        phrase = ((EditText) findViewById(R.id.words)).getText().toString();
        if ("".equals(phrase)) {
            Toast myToast = Toast.makeText(getApplicationContext(), "Ошибка: введите слово для перевода", Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.CENTER, 0, 0);
            myToast.show();
        } else {
            if (hasInternetConnection()) {
                myTranslation = new Translation();
                myTranslation.execute(phrase);
            } else {
                Toast myToast = Toast.makeText(getApplicationContext(), "Ошибка: Невозможно выполнить сетевой запрос. Проверьте наличие Интернет соединения", Toast.LENGTH_SHORT);
                myToast.setGravity(Gravity.CENTER, 0, 0);
                myToast.show();
            }
        }
    }


    public class Translation extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... value) {
            try {
                result = getTranslate(value[0]);
            } catch (IOException e) {
                result = null;
            } catch (JSONException e) {
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String answer) {
            super.onPostExecute(answer);
            if (answer == null) {
                Toast myToast = Toast.makeText(getApplicationContext(), "Ошибка перевода", Toast.LENGTH_SHORT);
                myToast.setGravity(Gravity.CENTER, 0, 0);
                myToast.show();
            } else {
                Intent intent = new Intent(TranslationActivity.this, PicturesActivity.class);
                intent.putExtra(EXTRA_MESSAGE, answer);
                startActivity(intent);
            }
        }

        public String getTranslate(String text) throws IOException, JSONException {
            String apiKey = "trnsl.1.1.20140927T095752Z.8e2a06f7da6c707e.0c6ad55d7115d71499faae9b5e3866e9e3c3e685";
            text = text.replace(" ", "%20");
            String requestUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
                    + apiKey + "&text=" + text + "&lang=en-ru&format=plain";
            URL url = new URL(requestUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.connect();
            int mes = httpConnection.getResponseCode();
            try {
                if (mes == 200) {
                    String line;
                    BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = buffReader.readLine()) != null) {
                        stringBuilder.append(line + '\n');
                        Log.i("CHECK", "String=" + line);
                    }
                    String string = stringBuilder.toString();
                    JSONObject object = (JSONObject) new JSONTokener(string).nextValue();
                    String result = object.getString("text");
                    result = result.subSequence(2, result.length() - 2).toString();
                    return result;
                } else {
                    return null;
                }
            } finally {
                httpConnection.disconnect();
            }
        }
    }

}
