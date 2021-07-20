package ru.ltow.qrng;

import java.io.Reader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import java.net.URL;
import org.json.JSONObject;
import org.json.JSONException;

public class JSONParser {
  public static JSONObject receive(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      return new JSONObject(
        readAll(new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))))
      );
    } finally {
      is.close();
    }
  }

  private static String readAll(Reader r) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = r.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  /*public static void main(String[] args) throws IOException, JSONException {
    JSONObject json = readJsonFromUrl("https://graph.facebook.com/19292868552");
    System.out.println(json.toString());
    System.out.println(json.get("id"));
  }*/
}