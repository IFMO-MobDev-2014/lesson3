package ru.ifmo.md.lesson3;

import android.os.AsyncTask;
import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.SearchParameters;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author volhovm
 *         Created on 9/27/14
 */

public class FindImagesTask extends AsyncTask<String, Void, ArrayList<URL[]>> {
    //url.length = 2 (full, null or thumbnail)
    private final String apiKey = "49ef1b092e3381a3e2607de11585ed24";
    private final String apiSecret = "0414901141154034";


    private int number = 10;
    ResultActivity activity;
    public FindImagesTask(ResultActivity resultActivity, int number) {
        activity = resultActivity;
        this.number = number;
    }

    protected ArrayList<URL[]> doInBackground(String... strings) {
        // TODO More accurate search
        ArrayList<URL[]> list = new ArrayList<URL[]>();
        Flickr flickr = new Flickr(apiKey, apiSecret);
        SearchParameters parameters = new SearchParameters();
        parameters.setSafeSearch(Flickr.SAFETYLEVEL_SAFE);
        parameters.setTags(new String[]{strings[0]});
        parameters.setText(strings[0]);
        try {
            PhotoList plist = flickr.getPhotosInterface().search(parameters, number, 1);
            for (int i = 0; i < plist.size(); i++) {
                Photo photo = plist.get(i);
                list.add(new URL[]{
                        new URL("https://farm" + photo.getFarm()
                        + ".staticflickr.com/" + photo.getServer()
                        + "/" + photo.getId() + "_" + photo.getSecret() +  "_t.jpg"),
                        new URL("https://farm" + photo.getFarm()
                        + ".staticflickr.com/" + photo.getServer()
                        + "/" + photo.getId() + "_" + photo.getSecret() +  "_z.jpg"),

                });
            }
            HashSet<URL[]> set = new HashSet<URL[]>(); // duplicate removed
            set.addAll(list);
            list.clear();
            list.addAll(set);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FlickrException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<URL[]> entries) {
        activity.onImagesUPLsRecieved(entries);
    }
}
