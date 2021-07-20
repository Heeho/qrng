package ru.ltow.qrng;

import android.widget.SeekBar;
import android.widget.TextView;
import android.util.AttributeSet;
import android.content.Context;

public class SBToolTip extends TextView {
  public SBToolTip(Context c) {super(c); ctor();}
  public SBToolTip(Context c, AttributeSet a) {super(c, a); ctor();}
  public SBToolTip(Context c, AttributeSet a, int dsa) {super(c, a, dsa); ctor();}

  public void ctor() {}

  public void track(SeekBar sb) {
    setText((new StringBuilder())
    .append(sb.getTag().toString()).append(String.valueOf(sb.getProgress())).toString());
    //setX(sb.getThumb().getBounds().left);
    //setY(sb.getThumb().getBounds().top);
  }
}