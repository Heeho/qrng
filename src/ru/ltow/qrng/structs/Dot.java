package ru.ltow.qrng;

public class Dot {
  //public static final int DIMS = 2;
  private int x, y;

  public Dot(int a, int b) {x = a; y = b;}
  public int x() {return x;}
  public int y() {return y;}

  @Override
  public String toString() {
    return String.format("[%d, %d]", x, y);
  }
}