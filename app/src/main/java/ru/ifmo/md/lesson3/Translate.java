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

    private static final String APIKey = "trnsl.1.1.20140929T071711Z.570b47c67d97796a.a555577f122c91bea72d909962924615127a86c4";
    public static String translate(String word) {
        String urlString = "https://translate.yandex.net/api/v1.5/tr.json/translate ? \n" +
                "key=" + APIKey + "\n" +
                " & text=" + word + "\n" +
                " & lang=en-ru\n";
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {

        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            Scanner sc = new Scanner(in);
            Log.i("", sc.nextLine());
        } catch (IOException e) {

        } finally {
            urlConnection.disconnect();
        }
        return "EMPTY";
    }
    public void run() {

    }
}
