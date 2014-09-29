package com.cococo.lesson3;

/**
 * Created by Freemahn on 28.09.2014.
 */

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;


/**
 * Created by Freemahn on 28.09.2014.
 */
public class DownloadingImagesTask extends AsyncTask<String, Void, JSONObject> {
    Context context;

    public DownloadingImagesTask(Context context) {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONObject doInBackground(String... urls) {

        return null;
    }


    @Override
    protected void onPostExecute(JSONObject jsonData) {

    }

}
