package ru.narbut.axel.gallery.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class DynamicHeightImageView extends AppCompatImageView {

  private double mHeightRatio;

  public DynamicHeightImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public DynamicHeightImageView(Context context) {
    super(context);
  }

  public void setHeightRatio(double ratio) {
    if (ratio != mHeightRatio) {
      mHeightRatio = ratio;
      requestLayout();
    }
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (mHeightRatio > 0.0) {

      int width = MeasureSpec.getSize(widthMeasureSpec);
      int height = (int) (width * mHeightRatio);
      setMeasuredDimension(width, height);
    }
    else {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
  }
}