package ru.narbut.axel.gallery.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

// for fab on scroll
public class ScrollingHideBehavior<V extends View>  extends  CoordinatorLayout.Behavior<V> {

  public ScrollingHideBehavior(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
    return super.layoutDependsOn(parent, child, dependency);
  }

  @Override
  public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
      @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
  }

  @Override
  public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child,
      @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
    super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
    int bottomMargin = lp.bottomMargin;
    child.setTranslationY(Math.max(0f,Math.min((float) child.getHeight()+ bottomMargin,child.getTranslationY()+ dy)));
  }
}
