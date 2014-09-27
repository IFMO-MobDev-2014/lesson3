package ru.ifmo.translator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by mariashka on 9/27/14.
 */
public class ConnectionFactory {
    static final String host = "https://translate.yandex.net/api/v1.5/tr.json/translate?";
    static final String key = "key=";
    static final String apiKey = "trnsl.1.1.20140927T071022Z.69b1664eb27bac7b.a50740b042ae022932d6efd0fd9e5938ea1b0699";
    static final String text = "&text=";
    static final String lang = "&lang=en-ru";

    private static URL getURL(String trText) throws MalformedURLException {
        return new URL(host + key + apiKey + text + trText + lang);
    }

    public static HttpsURLConnection getHTTPS(String str) throws IOException {
        URL url = getURL(str);
        return (HttpsURLConnection) url.openConnection();
    }

}
