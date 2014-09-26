package ru.ifmo.mobdev.translator;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by sugakandrey on 19.09.14.
 */
public class ImageFinder extends AsyncTask<String, Void, ArrayList<String>> {

    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        return new ArrayList<String>(){{
            add("place");
            add("holder");
        }};
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
    }
}
