package ru.ifmo.md.lesson3;

import android.net.Uri;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ComradeK on 28/09/2014.
 */
public class ImageSearch implements Runnable {

    public static final int IMGCOUNT = 10;
    String input;

    public ImageSearch(String string) {
        input = string;
    }

    public void search(String input) {
        try {
            String pictures[] = new String[10];
            HttpClient client = new DefaultHttpClient();
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.scheme("https")
                    .authority("api.flickr.com")
                    .appendPath("services")
                    .appendPath("rest")
                    .appendPath("")
                    .appendQueryParameter("api_key", "f101f9679bf4a613577977629b5a710f")
                    .appendQueryParameter("method", "flickr.photos.search")
                    .appendQueryParameter("format", "json")
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
            SecondActivity.urls = pictures;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        search(input);
    }
}
