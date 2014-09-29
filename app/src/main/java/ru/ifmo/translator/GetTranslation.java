package ru.ifmo.translator;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;


/**
 * @author Marianna Bisyrina
 */
public class GetTranslation extends AsyncTask <String, Void, String> {

    public String response;

    @Override
    protected String doInBackground(String... params) {
        try {
            HttpsURLConnection connection = ConnectionFactory.getHTTPS(Arrays.toString(params));
            connection.disconnect();
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            response = readLine(in);
            JSONObject json = new JSONObject(response);
            response = json.getString("text");
            response = response.substring(3, response.length() - 3);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        return response;
    }

    private StringBuilder out = new StringBuilder();
    private String readLine(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        out.append(br.readLine());
        br.close();
        return out.toString();
    }
}

