package com.example.searchandtranslate;

import android.content.Context;

/**
 * Created by knah on 24.09.2014.
 */
public abstract class MyCallback<T> {

    protected final Context context;

    public MyCallback(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    abstract public void run(T param);
}

