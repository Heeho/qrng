package ru.ltow.qrng;

public class HexParser {
  private static final int HEXPERBYTE = 2;
  private static final String PREFIX = "#";
  private static final String BYTE = "ff";

  public static int ff(int nbytes) {
    StringBuilder hex = new StringBuilder();
    for(int i = 0; i < nbytes; i++) hex.append(BYTE);
    return decode(hex.toString());
  }

  public static Dot[] dots(String hex, int bytesperdot) {
    if(hex.length() < bytesperdot)
    throw new IllegalArgumentException("not enough bytes for even one dot");

    int length = hex.length();
    int charsperdot = bytesperdot * HEXPERBYTE;
    int charspercoord = bytesperdot;
    Dot[] dots = new Dot[length / charsperdot];

    for(int i = 0; i < length; i += charsperdot) {
      dots[i / charsperdot] = new Dot(
        decode(hex.substring(i, i + charspercoord)),
        decode(hex.substring(i + charspercoord, i + charsperdot))
      );
    }
    return dots;
  }

  public static int[] bytes(String hex) {
    if(hex.length() < HEXPERBYTE)
    throw new IllegalArgumentException("not enough chars for even one byte");

    //trim
    hex = hex.substring(0, (hex.length() / HEXPERBYTE) * HEXPERBYTE);

    int[] bytes = new int[hex.length()/HEXPERBYTE];

    for(int i = 0; i < hex.length(); i += HEXPERBYTE)
    bytes[i/2] = decode(hex.substring(i, i + HEXPERBYTE));

    return bytes;
  }

  private static int decode(String hex) {
    return Integer.decode(PREFIX + hex);
  }
}