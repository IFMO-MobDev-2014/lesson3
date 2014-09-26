package odeenpva.lesson3;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by gshark on 26.09.2014.
 */
public class Translater extends AsyncTask<Void, Void, Boolean> {
    public enum Language {
        EN("en"),
        RU("ru");
        private String language;

        Language(String language) {
            this.language = language;
        }

        @Override
        public String toString() {
            return language;
        }
    }
    final static String key = "trnsl.1.1.20140925T183441Z.57ed333331da62e5.53748fc59b6f0f1ed5db824ab1021b6642197029";
    private String word;
    private Language from;
    private Language to;

    public Translater(String word, Language from, Language to) {
        super();
        this.word = word;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Document doc = null;
        String link = "https://translate.yandex.net/api/v1.5/tr/translate?" +
                "key=" + key +
                "&text=" + word +
                "&lang=" + from + "-" + to +
                "&[format=plain]" +
                "&[options=1]";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(new URL(link).openStream());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        try {
            String ans = doc.getElementsByTagName("text").item(0).getTextContent();
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }
}