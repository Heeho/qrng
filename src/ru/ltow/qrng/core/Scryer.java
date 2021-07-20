package ru.ltow.qrng;

import android.location.Location;

public class Scryer {
  private static final int MIN = 0, DIMS = 2;

  private double distance; //anomaly generation sn/we distance, m
  private int ndots, bytespercoord, bytesperdot, max;
  private Qtree q, cluster, hole;

  private static final double
  DEG = 360,
  C = 40075000,  //avg. earth circumference, m
  LON = C / DEG; //avg. m per 1deg longitude

  public Scryer(int ndots, int bpc, double r) {
    ndots(ndots);
    bytespercoord(bpc);
    distance(r);
  }

  public void ndots(int n) {ndots = n;}
  public void bytespercoord(int bpc) {
    bytespercoord = bpc;
    bytesperdot = bytespercoord * DIMS;
    max = HexParser.ff(bytespercoord);
  }
  public void distance(double r) {distance = r;}

  public Qtree qtree() {return q;}
  public Qtree cluster() {return cluster;}
  public Qtree hole() {return hole;}

  public void flush() {
    q = null; cluster = null; hole = null;
  }

  public void init() {
    q = new Qtree(new AABB(MIN, max, MIN, max));

    for(Dot d : HexParser.dots(Anu.hex(bytesperdot * ndots), bytesperdot)) q.insert(d);

    cluster = q.cluster();
    hole = q.hole();
  }

  public Anomalies anomalies(Location l) {
    Dot[] anomalies = new Dot[]{cluster.boundary().center(), hole.boundary().center()};

    double
    //degrees per input distance
    dlat = distance / (C * Math.cos(l.getLongitude()) / DEG),
    dlon = distance / LON,
    //relational deltas
    max = (double) this.max,
    clusterdx = ((double) anomalies[0].x() - max/2) / max/2,
    clusterdy = ((double) anomalies[0].y() - max/2) / max/2,
    holedx = ((double) anomalies[1].x() - max/2) / max/2,
    holedy = ((double) anomalies[1].y() - max/2) / max/2;

    Location
    cluster = new Location(l),
    hole = new Location(l);

    cluster.setLatitude(l.getLatitude() + dlat * clusterdy);
    cluster.setLongitude(l.getLongitude() + dlon * clusterdx);

    hole.setLatitude(l.getLatitude() + dlat * holedy);
    hole.setLongitude(l.getLongitude() + dlon * holedx);

    return new Anomalies(cluster, hole);
  }

  public String grid() {
    return Stringer.join(
      cluster.boundary().center().toString(),
      hole.boundary().center().toString(),
      (new Grid(HexParser.ff(bytespercoord)))
      .put(cluster.boundary().center(),hole.boundary().center()).toString()
    );
  }
}