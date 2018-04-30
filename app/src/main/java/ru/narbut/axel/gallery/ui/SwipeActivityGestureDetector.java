package ru.narbut.axel.gallery.ui;

import android.app.Activity;
import android.view.MotionEvent;

public class SwipeActivityGestureDetector extends android.view.GestureDetector.SimpleOnGestureListener {

  private static final int SWIPE_MIN_DISTANCE = 120;
  private static final int SWIPE_MAX_OFF_PATH = 250;
  private static final int SWIPE_THRESHOLD_VELOCITY = 200;
  private final Activity activity;

  public SwipeActivityGestureDetector(Activity activity){
    this.activity = activity;
  }

  @Override
  public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    try {
      if (Math.abs(e2.getY() - e1.getY()) > SWIPE_MIN_DISTANCE && (Math.abs(e1.getX() - e2.getX()) < SWIPE_MAX_OFF_PATH) && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
        activity.onBackPressed();
      }
    } catch (Exception e) {
      // nothing
    }
    return false;
  }

  @Override
  public boolean onDown(MotionEvent e) {
    return true;
  }
}