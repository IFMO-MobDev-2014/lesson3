package ru.ifmo.md.lesson3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.InputStream;

/**
 * Created by nagibator2005 on 2014-10-01.
 */
public class PicActivity extends Activity {

    ImageView[] images;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        showPictures(message);
    }

    private boolean internetWorks() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo[] netInfos = manager.getAllNetworkInfo();
        if (netInfos == null) {
            return false;
        }
        for (NetworkInfo netInfo : netInfos) {
            String name = netInfo.getTypeName();
            if ((name.equalsIgnoreCase("wifi") || name.equalsIgnoreCase("MOBILE")) && netInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    private void showPictures(String query) {
        if (!internetWorks()) {
            return;
        }
        ListView lvPics = (ListView) findViewById(R.id.listView);
        adapter = new ImageAdapter(this);
        lvPics.setAdapter(adapter);
        HttpLoader loader = new HttpLoader("https://www.google.com/search?q=" + query + "&tbm=isch");
        loader.setReadyHandler(new HttpLoader.OnReadyHandler() {
            @Override
            public void onReady(String result) {
                gotPage(result);
            }
        });
        Thread thread = new Thread(loader);
        thread.start();
    }

    public void gotPage(String result) {
        int nPics = 0, curStart = result.indexOf("<table class=\"images_table\"");
        if (curStart < 0) {
            return;
        }
        String imgUrls[] = new String[10];
        while (nPics < 10) {
            curStart = result.indexOf("<img", curStart) + 4;
            if (curStart < 0) {
                break;
            }
            int start = result.indexOf("src=\"", curStart) + 5;
            int end = result.indexOf("\"", start);
            imgUrls[nPics] = result.substring(start, end);
            curStart = end;
            nPics++;
        }

        images = new ImageView[nPics];
        for (int i = 0; i < nPics; i++) {
            images[i] = new ImageView(this, null);
            HttpLoader httpLoader = new HttpLoader(imgUrls[i]);
            httpLoader.setStreamHandler(new StreamHandler(images[i]) {
                @Override
                public void onStream(InputStream stream) {
                    view.setImageBitmap(BitmapFactory.decodeStream(stream));
                    view.setMinimumHeight(300);
                    view.setMinimumWidth(300);
                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.add(view);
                        }
                    });
                }
            });
            Thread thread = new Thread(httpLoader);
            thread.start();
        }
    }

    private abstract static class StreamHandler extends HttpLoader.OnStreamHandler {
        ImageView view = null;
        public StreamHandler(ImageView view) {
            this.view = view;
        }
    }

    private static class ImageAdapter extends ArrayAdapter<ImageView> {
        public ImageAdapter(Activity context) {
            super(context, R.layout.list_single);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position);
        }
    }
}

