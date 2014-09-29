package team.good.translator;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SearchImagesTask extends AsyncTask<String, Void, String[]> {
    private static final int N = 10;

    @Override
    protected String[] doInBackground(String... str) {
        String[] urls = new String[N];

        int j = 0;
        int p = 0;
        while (j < N) {
            try {
                String text = URLEncoder.encode(str[0], "utf-8");
                URL url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
                        "v=1.0&q=" + text + "&rsz=" + 8 + "&start=" + p + "&imgsz=small");
                URLConnection connection = url.openConnection();
                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                JSONObject json = new JSONObject(builder.toString());
                JSONArray resArray = json.getJSONObject("responseData").getJSONArray("results");
                String s;
                for (int i = 0; i < resArray.length() && j < N; i++, j++) {
                    s = resArray.getJSONObject(i).getString("url");
                    URLConnection imgConnection = (new URL(s)).openConnection();
                    imgConnection.setConnectTimeout(5000);
                    urls[j] = s;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            p += 8;
        }
        return urls;
    }
}