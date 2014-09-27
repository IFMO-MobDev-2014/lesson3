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

    public void setListWiew(Bitmap[] bmps) {
        rowItems = new ArrayList<RowItem>();
        for (Bitmap bmp : bmps) {
            RowItem item = new RowItem(bmp);
            rowItems.add(item);
        }
        listView = (ListView) findViewById(R.id.listView);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.list_item, rowItems);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Picture.this, Main.class);
                intent.putExtra("textField", query);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
            }
        });

        new PicturesDownloader(this).execute("cats");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
