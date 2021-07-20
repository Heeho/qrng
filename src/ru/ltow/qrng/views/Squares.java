package ru.ltow.qrng;

import java.util.ArrayList;
import android.util.AttributeSet;
import android.content.Context;
import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Bitmap;

public class Squares extends View {
  private boolean reinitialized;
  private int width, height;
  private ArrayList<int[]> squares = new ArrayList<>();
  private final Paint p = new Paint();
  private Bitmap cache;

  private float 
  hueoffset = 45f,
  hueincrement = 60f,
  saturation = 0.75f,
  brightness = 0.5f;

  public Squares(Context c) {super(c); ctor();}
  public Squares(Context c, AttributeSet a) {super(c, a); ctor();}
  public Squares(Context c, AttributeSet a, int dsa) {super(c, a, dsa); ctor();}

  public void hueincrement(float h) {hueincrement = h;}

  private void ctor() {
    p.setStyle(Paint.Style.FILL);
    p.setColor(Color.GRAY);
  }

  public void init(Scryer scryer) {
    squares.clear();

    int l = 0, t = 0, r = 0, b = 0, color = 0,
    rmax = scryer.qtree().boundary().r(),
    bmax = scryer.qtree().boundary().b(),
    mindepth = scryer.hole().depth();

    float[] hsv = new float[3];

    for(Qtree node : scryer.qtree().nodes(scryer.hole().depth())) {
      l = resize(node.boundary().l(), width, rmax);
      t = resize(node.boundary().t(), height, bmax);
      r = resize(node.boundary().r(), width, rmax);
      b = resize(node.boundary().b(), height, bmax);

      Color.colorToHSV(color, hsv);
      hsv[0] = hueoffset + hueincrement * ((float) (node.depth() - mindepth));
      hsv[1] = saturation;
      hsv[2] = brightness + (1f-brightness)*((float) node.dots().size())/((float) Qtree.capacity());
      color = Color.HSVToColor(255, hsv);

      squares.add(new int[]{l, t, r, b, color});
      reinitialized = true;
    }
  }

  private int resize(int a, int size, int amax) {
    return a * size / amax;
  }

  @Override
  protected void onDraw(Canvas c) {
    width = c.getWidth();
    height = c.getHeight();

    if(squares.isEmpty()) return;
      //c.drawCircle(width/2, height/2, ((width > height) ? height/4 : width/4), p);

    if(reinitialized) {
      cache = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      Canvas newc = new Canvas(cache);
      for(int[] s : squares) {
        p.setColor(s[4]);
        newc.drawRect(s[0], s[1], s[2], s[3], p);
      }
      reinitialized = false;
    }

    c.drawBitmap(cache, 0, 0, null);
  }
}