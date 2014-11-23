package ru.ifmo.md.lesson3;

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
    private static final String APIKey = "trnsl.1.1.20141123T233635Z.1f56d125830d32d4.9e6527efde15b6ee9eac9ea94218da11b80edcf9";
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
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            Scanner sc = new Scanner(in);
            String ret = sc.nextLine();
            return  ret.substring(ret.lastIndexOf('[') + 2, ret.lastIndexOf(']') - 1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }
    public void run() {
       SecondActivity.ret = translate(input);
    }

}
