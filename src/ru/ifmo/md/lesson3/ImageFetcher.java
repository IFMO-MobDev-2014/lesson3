package ru.ifmo.md.lesson3;

import android.graphics.Bitmap;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//TODO
public class ImageFetcher {
    private final int pictureCount;
    private static final String sekretKey = "ad4a117a330cb8d1";
    private static final String key = "e668a28be78987a1e7c65b60e8d08c3f";
    private static final String pictureUrl = "https://api.flickr.com/services/rest/";

    public ImageFetcher(int N) {
        this.pictureCount = N;
    }


    //"https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="+key+"&text=test&per_page="+pictureCount+"&format=json&nojsoncallback=1"

    public Bitmap[] fetch(String query)  {

        Bitmap [] result = new Bitmap[pictureCount];

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(pictureUrl);
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("method", "flickr.photos.search"));
        pairs.add(new BasicNameValuePair("api_key", key));
        pairs.add(new BasicNameValuePair("text", query));
        pairs.add(new BasicNameValuePair("per_page", Integer.toString(pictureCount)));
        pairs.add(new BasicNameValuePair("format", "json"));
        try{
            post.setEntity(new UrlEncodedFormEntity(pairs));
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
