package team.good.translator;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * Created by Artur on 29.09.2014.
 */



public class TranslateTask extends AsyncTask<String, Void, String> {
    static private String lang = "en-ru";
    static private String key = "trnsl.1.1.20140927T195326Z.be027d8996c15274.7aba0f207271092f651358b169d637d7c850b92c";
    private String text;
    private String translation;
    private int status = -1;


    @Override
    protected String doInBackground(String... str) {
        try {
            text = URLEncoder.encode(str[0], "utf-8");
        } catch (Exception e) {
            text = str[0];
        }
        translation = "";
        status = -1;
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + key + "&text=" + text + "&lang=" + lang);
        httpGet.setHeader("Content-type", "application/json");
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line = reader.readLine();
                JSONObject jsonObject = new JSONObject(line);
                translation = jsonObject.getJSONArray("text").getString(0);
            }
            status = statusCode;
            return getTranslation();
        }
        catch (Exception e) {
            status = 0;
            return getTranslation();
        }
    }


    public int getStatusCode() {
        return status;
    }

    public String getTranslation() {
        if (status == 200) {
            return translation;
        } else {
            return statusCode(status);
        }
    }



    public String statusCode(int code) {
        String ans = "unknown";
        if (code == 200) {
            ans = "OK";
        } else if (code == 401) {
            ans = "wrong API key";
        } else if (code == 402) {
            ans = "API key is blocked";
        } else if (code == 403) {
            ans = "day requests limit is exeeded";
        } else if (code == 404) {
            ans = "day total text limit is exeeded";
        } else if (code == 413) {
            ans = "too long text";
        } else if (code == 422) {
            ans = "text can't be translated";
        } else if (code == 501) {
            ans = "wrong language";
        } else if (code == 0) {
            ans = "something wrong with Internet connection";
        } else if (code == -1) {
            ans = "translating...";
        }
        return ans;
    }


}