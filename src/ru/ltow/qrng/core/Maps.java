package ru.ltow.qrng;

import java.util.Locale;
import android.location.Location;

public class Maps {
  private static final String
  YA = "https://yandex.ru/maps/?ll=%9.6f,%9.6f&z=12&l=map",
  YAMARK = "https://yandex.ru/maps/?pt=%9.6f,%9.6f&z=18&l=map",
  GOOGLE = "https://www.google.ru/maps/@%10.7f,%10.7f,17z",
  GOOGLEMARK = "https://www.google.com/maps/search/?api=1&query=%10.7f,%10.7f",
  YASTATIC =
  "https://static-maps.yandex.ru/1.x/?ll=%9.6f,%9.6f&size=450,450&z=13&l=map&pt=%9.6f,%9.6f,pmwtm1";

  public static String link(Location l) {
    return yamark(l);
  }

  public static String ya(Location l) {
    return String.format(Locale.ROOT, YA, l.getLongitude(), l.getLatitude());
  }

  public static String yamark(Location l) {
    return String.format(Locale.ROOT, YAMARK, l.getLongitude(), l.getLatitude());
  }

  public static String google(Location l) {
    return String.format(Locale.ROOT, GOOGLE, l.getLatitude(), l.getLongitude());
  }

  public static String googlemark(Location l) {
    return String.format(Locale.ROOT, GOOGLEMARK, l.getLatitude(), l.getLongitude());
  }

  public static String yaStaticAPI(Location l) {
    return String.format(Locale.ROOT, YASTATIC,
      l.getLongitude(), l.getLatitude(), l.getLongitude(), l.getLatitude()
    );
  }
}