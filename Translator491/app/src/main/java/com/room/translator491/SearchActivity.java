package com.room.translator491;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.translator491.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends Activity {
    GridView mGridViewResult;
    String query;
    ArrayList<ItemImageResult> itemImageResults = new ArrayList<ItemImageResult>();
    ItemImageResultsArrayAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        query = getIntent().getExtras().getString("query");
        imageAdapter = new ItemImageResultsArrayAdapter(this, itemImageResults);
        mGridViewResult = (GridView) findViewById(R.id.gvResults);
//      ActionBar actionBar = getActionBar();
//      actionBar.setDisplayHomeAsUpEnabled(true);
        mGridViewResult.setAdapter(imageAdapter);
        itemImageResults.clear();
        imageAdapter.clear();
        searchWithOffset(0);

        mGridViewResult.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                Intent i = new Intent(getApplicationContext(), ShowFoundResults.class);
                ItemImageResult mItemImageResult = itemImageResults.get(position);
                i.putExtra("url", mItemImageResult.getFUrl());
                startActivity(i);
            }
        });

        mGridViewResult.setOnScrollListener(new EndScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                searchWithOffset(page);
            }
        });
    }

    public void searchWithOffset(int offset) {
        AsyncHttpClient client = new AsyncHttpClient();
        System.out.println(query);
        String url = "https://ajax.googleapis.com/ajax/services/search/images?" +
                "start=" + Integer.toString(offset * 8) + "&v=1.0&q=" + Uri.encode(query) + "&rsz=8";
        Log.i("url", url);
        client.get(url, new JsonHttpResponseHandler() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onSuccess(JSONObject response) {
                JSONArray imageJsonResults = null;
                try {
                    imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                    imageAdapter.addAll(ItemImageResult.fromJSONArray(imageJsonResults));
                    Log.i("link", itemImageResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
