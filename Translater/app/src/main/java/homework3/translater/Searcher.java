package homework3.translater;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anstanasia on 18.01.2015.
 */

public class Searcher extends AsyncTask<String, Void, List<Bitmap> > {

    Bitmap getBitmap(String url) {
        final DefaultHttpClient client = new DefaultHttpClient();
        final HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode +
                      " while retrieving bitmap from " + url + " ");
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    return BitmapFactory.decodeStream(inputStream);
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            getRequest.abort();
            Log.e("ImageDownloader", "Something went wrong while" +
                    " retrieving bitmap from " + url + "\nError: " + e.toString());
        }
        return null;
    }

    @Override
    protected List<Bitmap> doInBackground(String... s) {
        Log.d("Searcher", "doInBack " + s[0]);
        List<Bitmap> l = new ArrayList<Bitmap>();

        String text = null;
        try {
            text = URLEncoder.encode(s[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 12; i+=4) {
            URL url;
            try {
                url = new URL("https://ajax.googleapis.com/ajax/services/search/images?" +
                        "v=1.0&q=" + text + "&rsz=" + 4 + "&start=" + i + "&imgsz=small");
                URLConnection connection;
                connection = url.openConnection();
                String line;
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                JSONObject json = new JSONObject(builder.toString());
                JSONArray resArray = json.getJSONObject("responseData").getJSONArray("results");

                String ss;
                for (int j = 0; j < resArray.length(); j++) {
                    ss = resArray.getJSONObject(j).getString("url");
                    l.add(getBitmap(ss));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return l;
    }

        @Override
    protected void onPostExecute(List<Bitmap> l) {
        super.onPostExecute(l);
    }
}
