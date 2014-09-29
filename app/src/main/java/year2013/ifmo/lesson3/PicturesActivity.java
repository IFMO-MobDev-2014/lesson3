package year2013.ifmo.lesson3;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class PicturesActivity extends Activity {
    private ListView mListView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        final String message = getIntent().getStringExtra(TranslationActivity.EXTRA_MESSAGE);
        ((TextView) findViewById(R.id.tvView)).setText(message);

        mListView = (ListView)findViewById(R.id.results);
        mAdapter = new MyAdapter(this);
        MyGridAdapter a = new MyGridAdapter(mAdapter);
        mListView.setAdapter(a);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    // clear existing results
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();
                        }
                    });

                    // do a google image search, get the ~10 paginated results
                    int start = 0;
                    final ArrayList<String> urls = new ArrayList<String>();
                    while (start < 40) {
                        DefaultHttpClient client = new DefaultHttpClient();
                        HttpGet get = new HttpGet(String.format("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=%s&start=%d&imgsz=medium", Uri.encode(message), start));
                        HttpResponse resp = client.execute(get);
                        HttpEntity entity = resp.getEntity();
                        InputStream is = entity.getContent();
                        final JSONObject json = new JSONObject(readToEnd(is));
                        is.close();
                        final JSONArray results = json.getJSONObject("responseData").getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject result = results.getJSONObject(i);
                            urls.add(result.getString("url"));
                        }

                        start += results.length();
                    }
                    // add the results to the adapter
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (String url: urls) {
                                mAdapter.add(url);
                            }
                        }
                    });
                }
                catch (final Exception ex) {
                    // explodey error, lets toast it
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PicturesActivity.this, ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        };
        thread.start();

    }

    private static String readToEnd(InputStream input) throws IOException
    {
        DataInputStream dis = new DataInputStream(input);
        byte[] stuff = new byte[1024];
        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        int read = 0;
        while ((read = dis.read(stuff)) != -1)
        {
            buff.write(stuff, 0, read);
        }

        return new String(buff.toByteArray());
    }

    private class Row extends ArrayList {

    }

    private class MyGridAdapter extends BaseAdapter {
        public MyGridAdapter(Adapter adapter) {
            mAdapter = adapter;
            mAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    notifyDataSetChanged();
                }
                @Override
                public void onInvalidated() {
                    super.onInvalidated();
                    notifyDataSetInvalidated();
                }
            });
        }
        Adapter mAdapter;

        @Override
        public int getCount() {
            return (int)Math.ceil((double)mAdapter.getCount() / 4d);
        }

        @Override
        public Row getItem(int position) {
            Row row = new Row();
            for (int i = position * 4; i < 4; i++) {
                if (mAdapter.getCount() < i)
                    row.add(mAdapter.getItem(i));
                else
                    row.add(null);
            }
            return row;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.row, null);
            LinearLayout row = (LinearLayout)convertView;
            LinearLayout l = (LinearLayout)row.getChildAt(0);
            for (int child = 0; child < 4; child++) {
                int i = position * 4 + child;
                LinearLayout c = (LinearLayout)l.getChildAt(child);
                c.removeAllViews();
                if (i < mAdapter.getCount()) {
                    c.addView(mAdapter.getView(i, null, null));
                }
            }

            return convertView;
        }

    }

    private class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context) {
            super(context, 0);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = getLayoutInflater().inflate(R.layout.image, null);

            final ImageView iv = (ImageView)convertView.findViewById(R.id.image);

            iv.setAnimation(null);
            // yep, that's it. it handles the downloading and showing an interstitial image automagically.
            UrlImageViewHelper.setUrlDrawable(iv, getItem(position), R.drawable.loading, new UrlImageViewCallback() {
                @Override
                public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
                    if (!loadedFromCache) {
                        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                        scale.setDuration(300);
                        scale.setInterpolator(new OvershootInterpolator());
                        imageView.startAnimation(scale);
                    }
                }
            });

            return convertView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
