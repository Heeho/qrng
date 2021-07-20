package ru.ltow.qrng;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.location.Location;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class QRNG extends Base {
  private SeekBar distanceSB;
  private SBToolTip ttipTV;
  private SBL sbl;
  private TextView resultTV;
  //private Squares squaresV;
  private Scryer scryer;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.qrng);

    int
    ndots = 32768,
    bpc = 2,
    distance = 1000,
    maxdistance = 10000;

    Locator.set(this);
    scryer = new Scryer(ndots, bpc, (double) distance);

    //views
    sbl = new SBL();

    distanceSB = findViewById(R.id.distanceSB);
    distanceSB.setMax(maxdistance);
    distanceSB.setProgress(distance);
    distanceSB.setOnSeekBarChangeListener(sbl);

    ttipTV = findViewById(R.id.ttipTV);
    ttipTV.track(distanceSB);

    resultTV = findViewById(R.id.resultTV);
    //squaresV = findViewById(R.id.squaresV);
  }

  public void anu(View v) {
    resultTV.setText(null);

    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        Location l = Locator.location();
        String s = null;

        if(l == null) {
          getText(R.string.nolocation).toString();
          //s = (AnuTests.resultLengthIsNbytes()) ? "ok" : "err";
          /*scryer.init();
          s = Stringer.join(getText(R.string.nolocation).toString(), scryer.grid());
          squaresV.init(scryer);*/
        } else {
          scryer.init();
          Anomalies a = scryer.anomalies(l);
          s = Stringer.join(
            getText(R.string.cluster).toString(), Maps.link(a.cluster()),
            getText(R.string.hole).toString(), Maps.link(a.hole())
          );
        }

        final String result = s;

        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            //squaresV.invalidate();
            resultTV.setText((CharSequence) result);
          }
        });
      } 
    });
    t.start();
  }

  private class SBL implements OnSeekBarChangeListener {
    @Override
    public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
      if(progress == 0) progress = 1;

      /*switch(sb.getId()) {
        case R.id.distanceSB:
          scryer.distance(progress);
          break;
        default: break;
      }*/
      scryer.distance(progress);
      ttipTV.track(sb);
    }

    @Override public void onStopTrackingTouch(SeekBar sb) {}
    @Override public void onStartTrackingTouch(SeekBar sb) {}
  }
}
