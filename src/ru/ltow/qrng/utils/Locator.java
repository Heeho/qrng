package ru.ltow.qrng;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import android.provider.Settings;

class Locator implements LocationListener {
  private static Location location;

  public static Location location() {
    return location;
  }

  public static void set(Context c) {
    LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);

    if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      Toast.makeText(c, c.getText(R.string.gpsoff).toString(), Toast.LENGTH_LONG).show();
      c.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    LocationListener ll = new Locator();  

    lm.requestLocationUpdates(
      LocationManager.GPS_PROVIDER,
      5000,
      10,
      ll);

    location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
  }

  @Override
  public void onLocationChanged(Location loc) {
    location = loc;
  }

  @Override
  public void onProviderDisabled(String provider) {}

  @Override
  public void onProviderEnabled(String provider) {}

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {}
}