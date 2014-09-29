package ru.ifmo.md.lesson3;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Kirill on 29.09.2014.
 */
public class Translate implements Runnable{

    private static String input;
    private static final String APIKey = "trnsl.1.1.20140929T071711Z.570b47c67d97796a.a555577f122c91bea72d909962924615127a86c4";
    public Translate(String string) {
        input = string;
    }
    public static String translate(String word) {
        String urlString = "https://translate.yandex.net/api/v1.5/tr.json/translate?" +
                "key=" + APIKey  +
                "&text=" + word  +
                "&lang=en-ru";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.i("shit ", "1");
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            Scanner sc = new Scanner(in);
            //
            String ret = sc.nextLine();
            Log.i("!", ret.substring(ret.lastIndexOf('[') + 2, ret.lastIndexOf(']') - 1));
            MainActivity.strin = ret.substring(ret.lastIndexOf('[') + 2, ret.lastIndexOf(']') - 1);

        } catch (Exception e) {
            Log.i("shit ", "2");
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }
    public void run() {
        translate(input);
    }

}
