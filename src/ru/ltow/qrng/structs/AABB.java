package ru.ltow.qrng;

public class AABB {
  private int xmin, xmax, ymin, ymax;

  public AABB(int x0, int x1, int y0, int y1) {
    xmin = x0;
    xmax = x1;
    ymin = y0;
    ymax = y1;
  }

  int color;
  public AABB(int x0, int x1, int y0, int y1, int c) {
    this(x0, x1, y0, y1);
    color = c;
  }

  public void color(int c) {color = c;}
  public int color() {return color;}

  public int[] xxyy() {
    return new int[]{xmin, xmax, ymin, ymax};
  }

  public int l() {return xmin;}
  public int t() {return ymin;}
  public int r() {return xmax;}
  public int b() {return ymax;}

  public Dot center() {
    return new Dot((xmax+xmin)/2, (ymax+ymin)/2);
  }

  public boolean contains(Dot dot) {
    return (
    xmin <= dot.x() && xmax > dot.x() && 
    ymin <= dot.y() && ymax > dot.y()
    );
  }

  public AABB[] split() {
    Dot center = center();
    return new AABB[]{
    new AABB(xmin, center.x(), ymin, center.y()),
    new AABB(xmin, center.x(), center.y(), ymax),
    new AABB(center.x(), xmax, center.y(), ymax),
    new AABB(center.x(), xmax, ymin, center.y())
    };
  }

  @Override
  public String toString() {
    return String.format("[%d %d, %d %d]\n", xmin, xmax, ymin, ymax);
  }
}