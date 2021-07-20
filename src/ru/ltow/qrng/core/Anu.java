package ru.ltow.qrng;

import java.io.IOException;
import org.json.JSONException;

public class Anu {
  private static final String
  HEXTEMPLATE = "https://qrng.anu.edu.au/API/jsonI.php?length=%d&type=hex16&size=%d",
  DATA = "data";

  private static final int APILIMIT = 1024;

  public static String hex(int nbytes) {
    int length = 0, size = 0;

    if(nbytes > APILIMIT) {
      length = 1 + nbytes / APILIMIT;
      size = nbytes / length + 1; //result of (length * size > nbytes)
    } else {
      length = 1; size = nbytes;
    }

    if(length > APILIMIT || size == 0)
    throw new IllegalArgumentException(
      String.format("error: length=%d, size=%d : must be in (0.. 1024] (api limit)", length, size)
    );

    String hex = "";
    try {
      hex = JSONParser.receive(String.format(HEXTEMPLATE, length, size))
      .getJSONArray(DATA).join("").replaceAll("\"","").substring(0, nbytes);
    } catch(IOException io) {io.printStackTrace();
    } catch(JSONException j) {j.printStackTrace();
    }

    return hex;
  }
}


