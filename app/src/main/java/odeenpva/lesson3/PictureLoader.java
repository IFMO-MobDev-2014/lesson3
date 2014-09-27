package odeenpva.lesson3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Женя on 26.09.2014.
 */
public class PictureLoader extends AsyncTask<Void, PictureProfiler, Boolean> {

    private String word;
    private int cnt;
    public PictureLoader(String word, int cnt) {
        this.word = word;
        this.cnt = cnt;
    }

    private String parseFunc(String f) {
    	for (int i = 0; i + 4 < f.length(); i++) {
    		if (f.substring(i, i + 4).equals("http")) {
    			String ans = "";
    			for (int j = i; f.charAt(j) != '"'; j++)
    				ans += f.charAt(j);
    			return ans;
    		}
    	}
    	return null;
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
        Elements qualityImages = images;
        images = images.select("img[src]");
        int c = 0;
        for (org.jsoup.nodes.Element e : images) {
            URL url = null;
            PictureProfiler profiler;
            try {
                url = new URL("http:" + e.attr("src"));
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                return false;
            }
            try {
                bitmap = BitmapFactory.decodeStream(url.openStream());
                String ref = null;
                ref =parseFunc(qualityImages.get(c).attr("onmousedown"));
                profiler = new PictureProfiler(bitmap, ref);
            } catch (IOException e1) {
                e1.printStackTrace();
                return false;
            }
            publishProgress(profiler);
            if (--cnt == 0)
                break;
            ++c;
        }
        return true;
    }
}
