package brigada.dictionary.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by Dmitry on 29.09.2014.
 */
public class Worker extends Activity {
    TextView textView;
    LinearLayout layout;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.worker);

        Button button = (Button) findViewById(R.id.back);
        textView = (TextView) findViewById(R.id.output);
        textView.setTextSize(50);
        textView.setTextColor(Color.BLACK);
        Intent intent = getIntent();
        String word = intent.getStringExtra("word");
        Translator translator = new Translator(word);
        ImageDownload pictureFinder = new ImageDownload(word);
        layout = (LinearLayout) findViewById(R.id.imagesLayout);
        translator.execute();
        pictureFinder.execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class Translator extends AsyncTask<Void, Void, String> {
        String word;

        Translator(String word) {
            this.word = word;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);
        }

        @Override
        protected String doInBackground(Void... parameter) {
            String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20140929T081453Z.0222651814bd7ed3.4bb093fa3fe9a9174dc234085e79a55cf90d0037&text=" + word + "&lang=en-ru";
            String out;
            try {
                InputStream in = new java.net.URL(url).openStream();
                java.util.Scanner str = new java.util.Scanner(in).useDelimiter("\\A");
                out = str.hasNext() ? str.next() : "";
            } catch (Exception e) {
                return "No connection";
            }
            int a = out.indexOf('[') + 2;
            int b = out.indexOf(']') - 1;
            return out.substring(a, b);
        }
    }

    public class ImageDownload extends AsyncTask<Void, Void, ArrayList<Drawable>> {

        public Drawable download(String link) throws IOException {

            Drawable out;
            try {
                out = Drawable.createFromStream((InputStream) new URL(link).getContent(), "Pic");

            } catch (FileNotFoundException e) {

                out =  Drawable.createFromPath("errorPic");
            }
            return out;

        }

        private String linker;

        @Override
        public ArrayList<Drawable> doInBackground(Void... var) {

            URLConnection contact;
            ArrayList<String> picture = new ArrayList<String>();
            String URL = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";

            try {

                for (int j = 0; j < 3; j++) {
                    int x;

                    if (j==2){
                        x=2;
                    } else {
                        x=4;
                    }

                    URL page = new URL(URL + linker + "&start=" + (4 * j));
                    contact = page.openConnection();
                    contact.connect();

                    BufferedReader in;
                    in = new BufferedReader(new InputStreamReader(contact.getInputStream(), "UTF8"));

                    JSONObject link = new JSONObject(in.readLine());
                    link = link.getJSONObject("responseData");
                    JSONArray images = link.getJSONArray("results");

                    for (int i = 0; i < x; i++) {
                        JSONObject current = images.getJSONObject(i);
                        picture.add(current.getString("url"));
                    }

                }

                ArrayList<Drawable> album = new ArrayList<Drawable>();

                for (int i = 0; i < 10; i++) {
                    album.add(download(picture.get(i)));
                }

                return album;

            } catch (Exception e) {

                return null;

            }

        }

        public ImageDownload(String link) {
            linker = link;
        }

        @Override
        public void onPostExecute(ArrayList<Drawable> album) {

            super.onPostExecute(album);

            if (album == null) {

                TextView mistake = (TextView) findViewById(R.id.errorWithPics);
                mistake.setTextSize(30);
                mistake.setTextColor(Color.RED);
                mistake.setText("Not found");

            } else {

                for (int i = 0; i < 10; i++) {

                    ImageView image = new ImageView(Worker.this);
                    image.setImageDrawable(album.get(i));
                    layout.addView(image);
                    layout.invalidate();

                }
                textView.setTextSize(Color.BLACK);

            }

        }

    }
}
