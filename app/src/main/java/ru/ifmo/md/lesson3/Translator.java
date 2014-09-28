package ru.ifmo.md.lesson3;

import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author volhovm
 *         Created on 9/25/14
 */

@SuppressWarnings("UnusedDeclaration")
public class Translator {
    public class TranslateDirection {
        public TranslateDirection(TranslateLanguage from, TranslateLanguage to) {
            this.from = from;
            this.to = to;
        }

        TranslateLanguage from, to;
        private String lang() { return from.lang + "-" + to.lang; }
    }

    public static enum TranslateLanguage {
        Russian("ru"),
        English("en");
        //and more if needed

        private String lang;
        TranslateLanguage(String lang) {
            this.lang = lang;
        }
    }

    private final String urlTemplate = "https://translate.yandex.net/api/v1.5/tr/translate?key=%s&lang=%s&text=%s";
    private final String key = "trnsl.1.1.20140924T160000Z.13a5048fefdf6ede.90e14a1a1fb4eb21cb6ffad6e8cd4b1bf0051860";
    private TranslateDirection direction;
    private String urlString;

    public Translator() {
    }

    public Translator(TranslateLanguage from, TranslateLanguage to) {
        this.direction = new TranslateDirection(from, to);
    }

    public String translate(String input) {
        try {
            urlString = String.format(urlTemplate, key, direction.lang(), URLEncoder.encode(input, "UTF-8"));
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());
            Node node = doc.getElementsByTagName("text").item(0);
            String out = node.getTextContent();
            return out;
        } catch (Exception e) {
            Log.e("Transator error", "", e);
            return e.toString();
        }
    }

    public TranslateDirection getLanguage() {
        return direction;
    }
}
