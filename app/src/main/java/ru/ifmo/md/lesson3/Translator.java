package ru.ifmo.md.lesson3;

import android.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author volhovm
 *         Created on 9/25/14
 */

@SuppressWarnings("UnusedDeclaration")
public class Translator {
    public static enum TranslateDirection { //TODO Запилить языки по одной штуке, а конструктор транслятора сделать от пары
        RussianToEnglish("ru-en"),
        EnglishToRussian("en-ru");

        private String lang;

        TranslateDirection(String lang) {
            this.lang = lang;
        }
    }

    private final String urlTemplate = "https://translate.yandex.net/api/v1.5/tr/translate?key=%s&lang=%s&text=%s";
    private final String key = "trnsl.1.1.20140924T160000Z.13a5048fefdf6ede.90e14a1a1fb4eb21cb6ffad6e8cd4b1bf0051860";
    private TranslateDirection direction = TranslateDirection.EnglishToRussian; // let it be default
    private String urlString;

    public Translator() {
    }

    public Translator(TranslateDirection direction) {
        this.direction = direction;
    }

    public String translate(String input) {
        try {
            urlString = String.format(urlTemplate, key, direction.lang, input);
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(conn.getInputStream());
            Node node = doc.getElementsByTagName("text").item(0);
            String out = node.getTextContent(); //FIXME Fails on ru-en direction
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
