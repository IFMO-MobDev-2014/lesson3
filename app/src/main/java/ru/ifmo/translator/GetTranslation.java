package ru.ifmo.translator;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class GetTranslation extends AsyncTask<String, Void, String> {
    ResponseListener listener;
    Context context;
    ProgressDialog dialog;
    Drawable[] images;

    public GetTranslation(Context context, ResponseListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new ProgressDialog(context);
        dialog.setMessage("Wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            images = ImageLoader.loadImage(params[0]);
            return TranslationLoader.translate(params[0]);
        } catch (Exception e) {
            images = new Drawable[]{context.getResources().getDrawable(R.drawable.error)};

            return "An error occurred, check the network connection.";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        listener.onResponse(s, images);
        dialog.dismiss();
    }
}
