package ru.ifmo.md.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by ComradeK on 28/09/2014.
 */
public class ImageSearch {

    public static final int IMGCOUNT = 10;

    public String[] search(String input) {
        try {
            String pictures[] = new String[10];
            HttpClient client = new DefaultHttpClient();
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("https")
                    .authority("api.flickr.com")
                    .appendPath("services")
                    .appendPath("rest")
                    .appendQueryParameter("api_key", "f101f9679bf4a613577977629b5a710f")
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("method", "flickr.photos.search")
                    .appendQueryParameter("text", input);
            HttpGet POST = new HttpGet(uriBuilder.build().toString());
            ResponseHandler<String> handler = new BasicResponseHandler();
            String response = client.execute(POST, handler);
            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("(") + 1,
                    response.lastIndexOf(")")));
            JSONArray results = jsonResponse.getJSONObject("photos").getJSONArray("photo");
            int count = IMGCOUNT;
            if (count > results.length())
                count = results.length();
            for (int i = 0; i < count; i++) {
                JSONObject result = results.getJSONObject(i);
                String farm = result.getString("farm");
                String server = result.getString("server");
                String id = result.getString("id");
                String secret = result.getString("secret");
                pictures[i] = "https://farm" + farm + ".staticflickr.com/" +
                                server + "/" + id + "_" + secret + "_q.jpg";
            }
            return pictures;
        } catch (Exception e) {
            return new String[0];
        }
    }
}
