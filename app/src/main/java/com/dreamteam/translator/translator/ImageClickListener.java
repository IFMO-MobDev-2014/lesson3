package com.dreamteam.translator.translator;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by flyingleafe on 29.09.14.
 */
public class ImageClickListener implements View.OnClickListener {
    String url;
    Context ctx;

    public ImageClickListener(Context ctx, String url) {
        super();
        this.ctx = ctx;
        this.url = url;
    }

    @Override
    public void onClick(View view) {
        Intent viewIntent = new Intent(ctx, ImagePreview.class);
        viewIntent.putExtra("url", url);
        ctx.startActivity(viewIntent);
    }
}
