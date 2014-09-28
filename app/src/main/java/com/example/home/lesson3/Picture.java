package com.example.home.lesson3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Picture extends Activity {

    private String translation;
    private String query;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(Picture.this, Main.class);
            intent.putExtra("textField", query);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    ListView listView;
    List<RowItem> rowItems;
    CustomListViewAdapter adapter = null;

    public void addBitmap(Bitmap bmp) {
        listView = (ListView) findViewById(R.id.listView);
        if (adapter == null)
            adapter = new CustomListViewAdapter(this, R.layout.list_item, null);
        adapter.add(new RowItem(bmp));
        listView.setAdapter(adapter);
    }

    public void setListWiew(Bitmap[] bmps) {
        rowItems = new ArrayList<RowItem>();
        for (Bitmap bmp : bmps) {
            RowItem item = new RowItem(bmp);
            rowItems.add(item);
        }
        listView = (ListView) findViewById(R.id.listView);
        adapter = new CustomListViewAdapter(this,
                R.layout.list_item, rowItems);
        listView.setAdapter(adapter);
    }

    public void setTranslation(String s) {
        translation = s;
        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText(translation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_picture);

        Bundle b = getIntent().getExtras();
        if(b != null) {
            translation = b.getString("translation");
            query = b.getString("query");
        }

        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText(translation);
        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(query);

        new YandexTranslator(this).execute(query);
        new PicturesDownloader(this).execute(query);
    }

}
