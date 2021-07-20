package ru.ltow.qrng;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends Base {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.about);
    ((TextView) findViewById(R.id.aboutTV)).setText(getText(R.string.about));
  }

  public void close(View v) {
    finish();
  }
}