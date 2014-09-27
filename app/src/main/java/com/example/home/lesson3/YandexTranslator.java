package com.example.home.lesson3;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by User on 27.09.2014.
 */
public class YandexTranslator extends AsyncTask<String, Void, String> {
    private static final String apiKey = "trnsl.1.1.20140924T185319Z.988cfba8926139f0" +
            ".cd1a85b0a94a070c8f04b7c8fd7ac83aa6542a56";

    private Picture picture;

    public YandexTranslator(Picture p) {
        picture = p;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        picture.setTranslation(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        String url = "https://translate.yandex.net/api/v1.5/tr/translate?" +
                "key=" + apiKey +
                "&text=" + strings[0].replace(" ", "+") + "&lang=en-ru";

        try {
            Document xmlResponse = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse((InputStream) new URL(url).getContent());
            return xmlResponse.getDocumentElement().
                    getElementsByTagName("text").
                    item(0).
                    getFirstChild().
                    getNodeValue();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }
}
