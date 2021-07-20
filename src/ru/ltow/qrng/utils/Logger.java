package ru.ltow.qrng;

import java.util.Arrays;
import android.util.Log;

public class Logger {
  public static final String TAG = "qrng";

  public static void log(String s) {log(callerName(), s);}
  public static void log(String note, int[] a) {log(callerName(), note, Arrays.toString(a));}

  private static void log(StackTraceElement caller, String... args) {
    StringBuilder b = new StringBuilder();
    b.append(caller).append(" -- ");
    for(String s : args) b.append(s);
    Log.e(TAG, b.toString());
  }

  private static StackTraceElement callerName() {
    return Thread.currentThread().getStackTrace()[4];
  }
}