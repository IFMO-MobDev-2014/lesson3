package ru.ifmo.mobdev.translator;

import android.content.Context;

/**
 * @author nqy
 */
public class Application extends android.app.Application {
    private static Context context;
    public static final String CONSUMER_KEY = "oZWIZFLBbwniW3FIi1bzyspuEdSvjY8GyXjZRMGP";

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }
}
