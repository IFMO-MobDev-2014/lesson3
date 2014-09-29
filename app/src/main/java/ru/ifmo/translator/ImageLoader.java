package ru.ifmo.translator;

import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * @author Ilya Zban
 */
public class ImageLoader {
    private static final String key = "JLwThFmpOoYj9p7XTneZ41HUuO3jYoY8Wi3yNftMqfs";
    private static final String encodedKey = "Basic " + Base64.encodeToString((key + ":" + key).getBytes(), Base64.NO_WRAP);
    private static final int COUNT_IMAGES = 5;

    public static Drawable[] loadImage(String query) {
        try {
            URL url = new URL("https://api.datamarket.azure.com/Bing/Search/Image?$format=json&Query=%27" + query.replaceAll(" ", "%32") + "%27");
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestProperty("Authorization", encodedKey);
            connect.setRequestProperty("Query", query);

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
            Log.d("", "OK");
            return res;
        } catch (Exception e) {
            Log.d("", "FAIL");
            e.printStackTrace();
            return new Drawable[COUNT_IMAGES];
        }
    }

    protected static Drawable download(String urlString) {
        try {
            URL url = new URL(urlString);
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
}
