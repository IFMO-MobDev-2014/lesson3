package ru.ifmo.ctddev.soloveva.translator.api;

import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by maria on 26.09.14.
 */
public class BingImageSearchService implements Closeable {
    private static final String URI_FORMAT = "https://api.datamarket.azure.com/Bing/Search/Image" +
                    "?Query=%27{0}%27" +
                    "&$top={1}" +
                    "&$format=json" +
                    "&ImageFilters=%27Size:Medium%27";

    private static final Gson GSON = new Gson();
    private static final JsonParser JSON_PARSER = new JsonParser();

    private final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
    private final String authData;

    public BingImageSearchService(String accountKey) {
        this.authData = Base64.encodeToString((accountKey + ":" + accountKey).getBytes(), Base64.NO_WRAP);
    }

    public List<BingImageData> search(String query, int limit) throws IOException {
        HttpGet request = new HttpGet(MessageFormat.format(URI_FORMAT, Uri.encode(query), limit));
        request.setHeader("Authorization", "Basic " + authData);
        HttpResponse response = client.execute(request);
        JsonElement responseData = JSON_PARSER.parse(new InputStreamReader(response.getEntity().getContent()));
        JsonElement resultsJson = responseData.getAsJsonObject().get("d").getAsJsonObject().get("results");
        return GSON.fromJson(resultsJson, new TypeToken<List<BingImageData>>() {}.getType());
    }

    @Override
    public void close() throws IOException {
        client.close();
    }
}
