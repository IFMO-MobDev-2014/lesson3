package com.cococo.lesson3;

/**
 * Created by Freemahn on 28.09.2014.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GettingUrlsTask extends AsyncTask<String, Void, List<String>> {
    Context context;
    String word;

    public GettingUrlsTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(List<String> s) {
        ((ResultActivity) context).setUrls(s);
    }

    @Override
    protected List<String> doInBackground(String... urls) {
        String url = "https://api.flickr.com/services/rest";
        JSONParser parser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("method", "flickr.photos.search"));
        params.add(new BasicNameValuePair("api_key", "d9b7d0e96cb335ded4d607a74330bda5"));
        params.add(new BasicNameValuePair("tags", word));
        params.add(new BasicNameValuePair("format", "json"));
        params.add(new BasicNameValuePair("per_page", "10"));
        params.add(new BasicNameValuePair("media", "photos"));
        JSONObject b = parser.makeHttpRequest(url, params);
        JSONArray a = null;
        List<String> temp = null;

        try {
            a = b.getJSONObject("photos").getJSONArray("photo");

            temp = new ArrayList<String>();
            for (int i = 0; i < 10; i++) {
                JSONObject s = a.getJSONObject(i);
                String farmid = s.getString("farm");
                String serverid = s.getString("server");
                String id = s.getString("id");
                String secret = s.getString("secret");
                temp.add("http://farm" + farmid + ".staticflickr.com/" + serverid + "/" + id + "_" + secret + ".jpg");
                Log.v("TEMP", temp.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;

    }


}
