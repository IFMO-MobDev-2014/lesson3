package odeenpva.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Женя on 26.09.2014.
 */
public class PictureLoader extends AsyncTask<Void, Bitmap, Boolean> {

    private String word;
    private int cnt;
    public PictureLoader(String word, int cnt) {
        this.word = word;
        this.cnt = cnt;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        String link = "http://yandex.ru/images/search?text="+word+"&uinfo=sw-1366-sh-768-ww-1366-wh-667-pd-1-wp-16x9_1366x768";
        Bitmap bitmap = null;
        org.jsoup.nodes.Document doc = null;
        try {
            doc = Jsoup.connect(link).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0").get();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        Elements images = doc.select("a[class^=serp]");
        images = images.select("img[src]");
        for (org.jsoup.nodes.Element e : images) {
            URL url = null;
            try {
                url = new URL("http:" + e.attr("src"));
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                return false;
            }
            try {
                bitmap = BitmapFactory.decodeStream(url.openStream());
            } catch (IOException e1) {
                e1.printStackTrace();
                return false;
            }
            publishProgress(bitmap);
            if (--cnt == 0)
                break;
        }
        return true;
    }
}
