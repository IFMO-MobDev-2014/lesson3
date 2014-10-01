package ru.ifmo.md.lesson3;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by nagibator2005 on 2014-10-01.
 */
public class HttpLoader implements Runnable {

    public static abstract class OnReadyHandler {
        public abstract void onReady(String result);
    }

    public static abstract class OnStreamHandler {
        public abstract void onStream(InputStream stream);
    }

    private String link;
    OnReadyHandler readyHandler = null;
    OnStreamHandler streamHandler = null;

    HttpLoader(String link) {
        this.link = link;
    }

    public void setReadyHandler(OnReadyHandler handler) {
        this.readyHandler = handler;
    }

    public void setStreamHandler(OnStreamHandler handler) {
        this.streamHandler = handler;
    }

    public void run() {
        HttpResponse response = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));
            response = client.execute(request);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String result = "";
        if (response != null) {
            try {
                InputStream inputStream = response.getEntity().getContent();
                if (streamHandler != null) {
                    streamHandler.onStream(inputStream);
                }
                if (readyHandler != null) {
                    result = convertStreamToString(inputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (readyHandler != null) {
            readyHandler.onReady(result);
        }
    }

    private static String convertStreamToString(InputStream inputStream) throws IOException {
        if (inputStream != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"),1024);
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                inputStream.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }


}
