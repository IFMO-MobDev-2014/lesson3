package com.example.daria.translate.Tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.PhotosInterface;
import com.googlecode.flickrjandroid.photos.SearchParameters;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by daria on 02.01.15.
 */
public class DownloadImagesTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {
    protected static final String apiKey = "b4e43547df5984b8156f18e864539a0e";

    @Override
    protected ArrayList<Bitmap> doInBackground(String... params) {
        Flickr flickr = new Flickr(apiKey);
        SearchParameters searchParameters = new SearchParameters();
        ArrayList<Bitmap> result = new ArrayList<Bitmap>();
        PhotosInterface photosInterface = flickr.getPhotosInterface();
        PhotoList photos;

        searchParameters.setTags(params);
        try {
            photos = photosInterface.search(searchParameters, 10, 1);
            for (int i = 0; i < photos.size(); i++) {
                Photo photo = photos.get(i);
                URL photoURL = new URL("https://farm" + photo.getFarm() +
                        ".static.flickr.com/" + photo.getServer() + "/" + photo.getId() +
                        "_" + photo.getSecret() + "." + photo.getOriginalFormat());
                InputStream in = photoURL.openStream();
                result.add(BitmapFactory.decodeStream(in));
            }
        } catch (Exception e) {
            return null;
        }

        return result;
    }


}
