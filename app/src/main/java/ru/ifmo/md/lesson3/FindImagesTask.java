package ru.ifmo.md.lesson3;

import android.os.AsyncTask;
import android.util.Log;
import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.photos.SearchParameters;

import java.net.URL;
import java.util.ArrayList;

/**
 * @author volhovm
 *         Created on 9/27/14
 */

public class FindImagesTask extends AsyncTask<String, Void, ArrayList<URL>> { //thumbnail, full
    private final String apiKey = "49ef1b092e3381a3e2607de11585ed24";
    private final String apiSecret = "0414901141154034";

    private int number = 10;
    ResultActivity activity;
    public FindImagesTask(ResultActivity resultActivity, int number) {
        activity = resultActivity;
        this.number = number;
    }

    protected ArrayList<URL> doInBackground(String... strings) {
        ArrayList<URL> list = new ArrayList<URL>();
        String urlTemplate = "http://www.joomlaworks.net/images/demos/galleries/abstract/%d.jpg"; // dummy
        try {
            for (int i = 0; i < number; i++) {
                URL url = new URL(String.format(urlTemplate, i));
                // TODO Вот тут мякотка
                list.add(url);
            }
        } catch (Exception e) {
            Log.e("error", "", e);
        }
//        for (int i = 0; i < number; i++){
//            Flickr flickr = new Flickr(apiKey, apiSecret);
//            flickr.
//        }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<URL> entries) {
        activity.onImagesUPLsRecieved(entries);
    }
}
