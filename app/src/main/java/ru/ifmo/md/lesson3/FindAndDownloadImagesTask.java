package ru.ifmo.md.lesson3;

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
 * Created by Mikhail on 28.09.2014.
 */
public class FindAndDownloadImagesTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {
    public static final String apiKey = "3a242389656e825a73911632adbffa80";

    @Override
    protected ArrayList<Bitmap> doInBackground(String... params) {
        Flickr flickr = new Flickr(apiKey);
        SearchParameters searchParameters = new SearchParameters();
        ArrayList<Bitmap> images = new ArrayList<Bitmap>();
        PhotosInterface photosInterface = flickr.getPhotosInterface();
        PhotoList photoList;

        searchParameters.setTags(params);
        try {
            photoList = photosInterface.search(searchParameters, 10, 1);

            for(int i = 0; i < photoList.size(); i++) {
                Photo photo = photoList.get(i);
                URL photoURL = new URL("https://farm" + photo.getFarm() + ".static.flickr.com/" + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() + "." + photo.getOriginalFormat());
                InputStream input = photoURL.openStream();
                images.add(BitmapFactory.decodeStream(input));
            }
        } catch (Exception e) {
            return null;
        }

        return images;
    }
}