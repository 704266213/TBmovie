package com.golove.behavior;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 *
 */

public class FloatingFooterView extends CoordinatorLayout.Behavior<View> {

    private Handler handler;
    private boolean isScoller = false;

    public FloatingFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        handler = new Handler();
    }

    //1.判断滑动的方向 我们需要垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    public void onNestedScrollAccepted(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        // Do nothing
        Log.e("XLog", "===========接收滚动事件================");


    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        if (!isScoller) {
            hide(child);
            isScoller = true;
        }
    }

    // 页面停止滑动。
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, final View child, View target) {
        Log.e("XLog", "===========停止滚动事件================");

        isScoller = false;
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            public void run() {
                show(child);
            }
        }, 2000);
    }


    private void hide(final View view) {
        Animation hideTranslateAnimation = new TranslateAnimation(0, 0, 0, 2 * view.getHeight());
        if (!hideTranslateAnimation.hasStarted()) {
            //初始化 Translate动画
            AnimationSet set = new AnimationSet(true);
            set.addAnimation(hideTranslateAnimation);
            //设置动画时间 (作用到每个动画)
            set.setDuration(500);
            view.startAnimation(set);
        }

    }


    private void show(final View view) {
        TranslateAnimation showTranslateAnimation = new TranslateAnimation(0, 0, view.getHeight(), 0);
        if (showTranslateAnimation.hasStarted()) {
            AnimationSet set = new AnimationSet(true);
            set.addAnimation(showTranslateAnimation);
            //设置动画时间 (作用到每个动画)
            set.setDuration(500);
            view.startAnimation(set);
        }

    }
}