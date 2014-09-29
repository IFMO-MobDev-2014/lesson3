package com.room.translator491;

import android.app.Activity;
import android.os.Bundle;

import com.example.translator491.R;
import com.loopj.android.image.SmartImageView;

public class ShowFoundResults extends Activity {
    SmartImageView mSmartImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        mSmartImageView = (SmartImageView) findViewById(R.id.mSmartImageView);
        String imageUrl = (String)
                getIntent().getStringExtra("url");
        mSmartImageView.setImageUrl(imageUrl);
    }
}
