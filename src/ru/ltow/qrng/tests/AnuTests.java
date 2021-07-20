package ru.ltow.qrng;

public class AnuTests {
  public static boolean resultLengthIsNbytes() {
    return (847475 == Anu.hex(847475).length());
  }
}