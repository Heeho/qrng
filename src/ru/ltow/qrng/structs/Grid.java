package ru.ltow.qrng;

public class Grid {
  private static int GRID = 10;

  int max;
  private int[][] rows;

  public Grid(int m) {
    max = m;
    rows = new int[GRID][GRID];
  }

  public Grid put(Dot... dots) {
    for(Dot dot: dots) put(dot);
    return this;
  }

  public Grid put(Dot dot) {
    if(dot != null) rows[(dot.y()*GRID)/max][(dot.x()*GRID)/max]++;
    return this;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(int[] row : rows) sb.append(Stringer.cast(row)).append("\n");
    return sb.toString().replaceAll("\\[|\\]", "|").replaceAll("0|,| ","  ").replaceAll("\\d+","o");
  }  
}