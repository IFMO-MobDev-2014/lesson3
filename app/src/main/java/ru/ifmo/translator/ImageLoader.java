package ru.ifmo.translator;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */

class getImage extends AsyncTask<String, Void, Drawable[]> {

    private static String key = "JLwThFmpOoYj9p7XTneZ41HUuO3jYoY8Wi3yNftMqfs";
    private static String encodedKey = "Basic " + Base64.encodeToString((key + ":" + key).getBytes(), Base64.NO_WRAP);
    private static int COUNT_IMAGES = 3;

    protected Drawable download(String surl) {
        try {
            URL url = new URL(surl);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");
            connect.connect();
            InputStream is = connect.getInputStream();
            return Drawable.createFromStream(is, "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Drawable[] doInBackground(String... params) {
        System.out.println(encodedKey);
        try {
            URL url = new URL("https://api.datamarket.azure.com/Bing/Search/Image?$format=json&Query=%27" + params[0].replaceAll(" ", "%32") + "%27");
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestProperty("Authorization", encodedKey);
            connect.setRequestProperty("Query", params[0]);

            InputStream is = connect.getInputStream();
            Scanner in = new Scanner(is);
            String s = "";
            while (in.hasNext()) {
                s += in.next();
            }
            JSONObject json = new JSONObject(s);
            json = new JSONObject(json.get("d").toString());
            JSONArray arr = new JSONArray(json.get("results").toString());
            Drawable[] res = new Drawable[COUNT_IMAGES];
            for (int i = 0; i < Math.min(COUNT_IMAGES, arr.length()); i++) {
                JSONObject cur = new JSONObject(arr.get(i).toString());
                res[i] = download(cur.get("MediaUrl").toString());
            }
            Log.i("", "OK");
            return res;
        } catch (Exception e) {
            Log.i("", "FAIL");
            e.printStackTrace();
            return new Drawable[COUNT_IMAGES];
        }
    }
}

public class ImageLoader {
    Drawable[] loadImage(String query) {
        try {
            return new getImage().execute(query).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
