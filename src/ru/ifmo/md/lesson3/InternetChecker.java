package ru.ifmo.md.lesson3;

public class InternetChecker {

    static boolean isOnline() {
	    ConnectivityManager cm =
			    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
		    return true;
	    }
	    return false;
    }

}