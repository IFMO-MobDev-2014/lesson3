package ru.ifmo.translator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import javax.net.ssl.HttpsURLConnection;

/**
 * @author Zakhar Voit (zakharvoit@gmail.com)
 */
public class TranslationLoader {
    static String translate(String query) {
        String result = "Translation error";
        try {
            HttpsURLConnection connection = ConnectionFactory.getHTTPS(query);
            connection.disconnect();
            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            result = readLine(in);
            JSONObject json = new JSONObject(result);
            result = json.getString("text");
            result = result.substring(2, result.length() - 2);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String readLine(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String result = br.readLine();
        br.close();
        return result;
    }
}