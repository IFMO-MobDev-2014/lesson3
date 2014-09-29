package ru.android.german.translator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class TranslatorAPI {
    public Context ctx = null;
    ProgressDialog progressDialog;

    public void exec(Context ctx, String q) {
        this.ctx = ctx;
        progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage("Translating...");
        progressDialog.show();
        LoadTask task = new LoadTask();
        task.execute(q);
    }

    public class LoadTask extends AsyncTask<String, Void, ArrayList<String> > {
        //private final String USER_AGENT = "Mozilla/5.0";
        private final String key = "trnsl.1.1.20140927T191415Z.85ed08b03e3d84ae.c9561739ed7a76ef198ecb09eef98cceb9d102be";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<String> doInBackground(String... query) {
            //System.out.print(query[0].length());
            //System.out.println("Send Http GET request");
            ArrayList<String> res = new ArrayList<String>();
            try {
                String q = "";
                for(int i = 0; i < query[0].length(); i++) {
                    if (query[0].charAt(i) == ' ') {
                        q += "%20";
                    } else if (query[0].charAt(i) != '\n') {
                        q += query[0].charAt(i);
                    }
                }
                URL translateUrl = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?lang=en-ru&key="+key+"&text="+q);
                HttpURLConnection con = (HttpURLConnection)translateUrl.openConnection();
                con.setRequestMethod("GET");// optional default is GET
                //con.setRequestProperty("User-Agent", USER_AGENT);//add request header
                System.out.println("Translation Response Code: "+con.getResponseCode());

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String translation = (new JSONObject(in.readLine())).getJSONArray("text").getString(0);
                in.close();

                ArrayList<String> imagesBuffer = new ArrayList<String>();
                imagesBuffer.add(translation);
                for(int i = 0; i < 2; i++) {
                    String url = "http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+q+"&rsz=5&start="+(i*5);
                    System.out.println(url);
                    URL imagesUrl = new URL(url);
                    con = (HttpURLConnection)imagesUrl.openConnection();
                    con.setRequestMethod("GET");// optional default is GET
                    System.out.println("Images(load "+(i+1)+") Response Code: "+con.getResponseCode());
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String tmp = in.readLine();
                    System.out.println(tmp);
                    JSONObject obj = new JSONObject(tmp).getJSONObject("responseData");
                    JSONArray arr = obj.getJSONArray("results");
                    in.close();
                    for(int j = 0; j < arr.length(); j++) {
                        JSONObject item = arr.getJSONObject(j);
                        imagesBuffer.add(item.getString("url"));
                    }
                }
                res = imagesBuffer;
            } catch (IOException io) {
                System.out.println("IOException occured");
            } catch (JSONException e) {
                System.out.println("JSONException occured");
            }
            return res;
        }

        @Override
        protected void onPostExecute(ArrayList<String> arr) {
            super.onPostExecute(arr);
            Intent intent = new Intent(ctx, TranslateActivity.class);
            if (arr.size() > 0) {
                intent.putExtra("translate", arr.get(0));
            }
            for(int i = 0; i < arr.size()-1; i++) {
                intent.putExtra("img"+i, arr.get(i+1));
            }
            progressDialog.dismiss();
            ctx.startActivity(intent);
        }
    }

}
