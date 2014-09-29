package homework3.translater;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class SearchViewActivity extends Activity {
    public static final String ORIGINAL_WORD_EXTRA = "ORIGINAL_WORD_EXTRA";
    public static final String TRANSLATED_WORD_EXTRA = "TRANSLATED_WORD_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        ((TextView) findViewById(R.id.originalWord)).setText(getIntent().getStringExtra(ORIGINAL_WORD_EXTRA));
        ((TextView) findViewById(R.id.translatedWord)).setText(getIntent().getStringExtra(TRANSLATED_WORD_EXTRA));


    }


    class InstagramGetter extends AsyncTask<String, Void, ArrayList<String>> {
        public static final String API_ADDRESS = "https://api.instagram.com/v1/tags/%s/media/recent?client_id=a5c14675f47a440bba00cc4e0c67a640";

        private String normalizeWord(String word) {
            return word.toLowerCase().replace(" ", "");
        }

        public JSONObject getJSONFromUrl(String url) {
            InputStream is = null;
            JSONObject jObj = null;
            String json = "";

            // Making HTTP request
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();
                json = sb.toString();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }
            return jObj;
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            String tag = normalizeWord(strings[0]);
            String apiEndpoint = API_ADDRESS.replace("%s", tag);

            ArrayList<String> result = new ArrayList<String>();

            try {
                JSONObject data = getJSONFromUrl(apiEndpoint);
                JSONArray posts = data.getJSONArray("data");

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject postEntry = posts.getJSONObject(i);
                    JSONObject images = postEntry.getJSONObject("images");
                    JSONObject imageData = images.getJSONObject("low_resolution");
                    String imageUrl = imageData.getString("url");
                    result.add(imageUrl);
                }
            } catch (JSONException e) {
                Log.e("InsPs", "Instagram missing " + e.toString());
            }

            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> imageUrls) {

        }
    }

    public class WebImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }
}
