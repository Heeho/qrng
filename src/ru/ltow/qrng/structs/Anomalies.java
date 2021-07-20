package ru.ltow.qrng;

import android.location.Location;

public class Anomalies {
  private static final String
  OUTPUTFORMAT = "\n-- Anomalies --\nCluster: %9.6f %9.6f\nHole: %9.6f %9.6f\n";

  private Location[] locations;

  public Anomalies(Location cluster, Location hole) {
    locations = new Location[]{cluster, hole};
  }

  public Location cluster() {return locations[0];}
  public Location hole() {return locations[1];}

  /*public Location anomaly() {
      return the sharpest, no critera yet
  }*/

  @Override
  public String toString() {
    return String.format(OUTPUTFORMAT,
      cluster().getLongitude(), cluster().getLatitude(), 
      hole().getLongitude(), hole().getLatitude()
    );
  }
}