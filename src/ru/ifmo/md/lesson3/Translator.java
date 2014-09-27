package ru.ifmo.md.lesson3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class Translator {

	public String translate(String query) throws Exception {
		String url = "https://translate.yandex.net/api/v1.5/tr/translate?key=trnsl.1.1.20140926T182019Z.80862ff6fe2cbf98.b19536598e4b06436715164aa8b62e4ec79f737d&text=" + query + "&lang=en-ru&format=plain";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		try {
			con.getResponseCode();
		} catch (IOException e) {
			return "no internet";
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		String res = response.substring(response.indexOf("<text>"), response.indexOf("</text>"));
		return res.substring(6);
	}
}
