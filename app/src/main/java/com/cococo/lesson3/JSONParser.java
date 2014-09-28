package com.cococo.lesson3;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Freemahn on 28.09.2014.
 */
public class JSONParser {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public JSONParser() {
    }

    public JSONObject makeHttpRequest(String url,
                                      List<NameValuePair> params) {

        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = client.execute(httpPost);
            BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse
                    .getEntity().getContent(), "UTF-8"));
            json = br.readLine();


            jObj = new JSONObject(json);
        } catch (Exception e) {
            Log.e("Getting JSON error", json);
        }
        return jObj;

    }
}