package homework3.translater;
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
 * Created by Anstanasia Maksimovskih on 29.09.2014.
 */

public class Translator extends AsyncTask<String, String, String>{
    final static String key = "trnsl.1.1.20140929T153338Z.b0695ea50453dbf6.45c30639fe49d07b9a5b4febf8a44fe4985a3670";

    @Override
    protected String doInBackground(String... text) {
        String link = "https://translate.yandex.net/api/v1.5/tr/translate?" +
                "key=" + key +
                "&text=" + text[0] +
                "&lang=en-ru";
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URL url = new URL(link);
            Document doc = db.parse(url.openStream());
            try {
                return doc.getElementsByTagName("text").item(0).getTextContent();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}