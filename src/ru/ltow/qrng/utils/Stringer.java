package ru.ltow.qrng;

import java.util.Arrays;

public class Stringer {
  public static String cast(int[] a) {
    return Arrays.toString(a);
  }

  public static String cast(String... args) {
    StringBuilder sb = new StringBuilder();
    for(String s: args) sb.append(s);
    return sb.toString();
  }

  public static String join(String... args) {
    StringBuilder sb = new StringBuilder();
    for(String s: args) sb.append(s).append("\n");
    return sb.toString();
  }
}