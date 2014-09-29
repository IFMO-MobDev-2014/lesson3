package ru.ifmo.md.lesson3;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ListView;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

//TODO
public class ImageFetcher {
    private final int pictureCount;
    private static final String key = "e668a28be78987a1e7c65b60e8d08c3f";
    private static final String pictureUrl = "https://api.flickr.com/services/rest/";
    private final Resources res;
    protected Bitmap[] result ;
    int nextIndex = 0;
    int openAsync = 0;

    public ImageFetcher(int N, Resources res) {
        this.pictureCount = N;
        this.res = res;
    }

    //"https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="+key+"&text=test&per_page="+pictureCount+"&format=json&nojsoncallback=1"

    public Bitmap[] setImages(String query, ImageAdapter imageAdapter) {
        result = new Bitmap[pictureCount];


        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(pictureUrl);
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("method", "flickr.photos.search"));
            pairs.add(new BasicNameValuePair("api_key", key));
            pairs.add(new BasicNameValuePair("text", query));
            pairs.add(new BasicNameValuePair("per_page", Integer.toString(pictureCount)));
            pairs.add(new BasicNameValuePair("format", "json"));
            post.setEntity(new UrlEncodedFormEntity(pairs));
            HttpResponse response = client.execute(post);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null;) {
                builder.append(line).append("\n");
            }
            String json_result = builder.toString();
            JSONObject jsonResponse = new JSONObject(json_result.substring(json_result.indexOf("(") + 1,
                    json_result.lastIndexOf(")")));
            JSONArray finalResult = jsonResponse.getJSONObject("photos").getJSONArray("photo");
            for (int i=0;i<finalResult.length();i++)
            {
                JSONObject pictureWithId= finalResult.getJSONObject(i);

                String pictureId =  pictureWithId.getString("id");

                new downloadPicture().execute(pictureId);
                openAsync++;

            }




        } catch (UnsupportedEncodingException e) {



            for (int i = 0; i < pictureCount; i++) {
                result[i] = BitmapFactory.decodeResource(res, R.drawable.sad);
            }
            e.printStackTrace();
        } catch (ClientProtocolException e) {



            for (int i = 0; i < pictureCount; i++) {
                result[i] = BitmapFactory.decodeResource(res, R.drawable.sad);
            }
            e.printStackTrace();
        } catch (IOException e) {



            for (int i = 0; i < pictureCount; i++) {
                result[i] = BitmapFactory.decodeResource(res, R.drawable.sad);
            }
            e.printStackTrace();
        } catch (JSONException e) {



            for (int i = 0; i < pictureCount; i++) {
                result[i] = BitmapFactory.decodeResource(res, R.drawable.sad);
            }
            e.printStackTrace();
        }

        while (openAsync>0)
        {
            SystemClock.sleep(800);
        }


        return result;
    }

    class downloadPicture extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... id) {
            String url=null;
            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost(pictureUrl);
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("method", "flickr.photos.getSizes"));
                pairs.add(new BasicNameValuePair("api_key", key));
                pairs.add(new BasicNameValuePair("photo_id", id[0]));
                pairs.add(new BasicNameValuePair("format", "json"));
                post.setEntity(new UrlEncodedFormEntity(pairs));
                HttpResponse response = client.execute(post);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                StringBuilder builder = new StringBuilder();
                for (String line = null; (line = reader.readLine()) != null;) {
                    builder.append(line).append("\n");
                }
                String json_result = builder.toString();
                JSONObject jsonResponse = new JSONObject(json_result.substring(json_result.indexOf("(") + 1,
                        json_result.lastIndexOf(")")));
                JSONArray finalResult = jsonResponse.getJSONObject("sizes").getJSONArray("size");
                for (int i=0;i<finalResult.length();i++)
                {
                    JSONObject sizePicture= finalResult.getJSONObject(i);
                    if(sizePicture.getString("label").equals("Small"))
                    {
                        url =  sizePicture.getString("source");

                        break;
                    }
                }
                if(url==null)
                {
                    throw new IOException("fail haven't size");
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return BitmapFactory.decodeResource(res, R.drawable.sad);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                return BitmapFactory.decodeResource(res, R.drawable.sad);
            } catch (IOException e) {
                e.printStackTrace();
                return BitmapFactory.decodeResource(res, R.drawable.sad);
            } catch (JSONException e) {
                e.printStackTrace();
                return BitmapFactory.decodeResource(res, R.drawable.sad);
            }

            InputStream input = null;


            try {
                URL urlConn = new URL(url);
                input = urlConn.openStream();
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
                return BitmapFactory.decodeResource(res, R.drawable.sad);
            }
            catch (IOException e) {
                e.printStackTrace();
                return BitmapFactory.decodeResource(res, R.drawable.sad);
            }

            return BitmapFactory.decodeStream(input);
        }

        @Override
        protected void onPostExecute(Bitmap resulted) {
            super.onPostExecute(resulted);
            result[nextIndex++] = resulted;
            openAsync--;
        }
    }





}
