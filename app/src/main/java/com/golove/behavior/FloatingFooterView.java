package com.golove.behavior;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.golove.R;

/**
 *
 */

public class FloatingFooterView extends CoordinatorLayout.Behavior<View> {

    private Handler handler;
    private boolean isShow = true;

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
    }

    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        hide(child);
    }

    // 页面停止滑动。
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, final View child, View target) {
        RecyclerView recyclerView = (RecyclerView) target.findViewById(R.id.recyclerView);
        if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            Log.e("XLog", "===========停止滚动事件================");
            handler.removeCallbacksAndMessages(null);
            handler.postDelayed(new Runnable() {
                public void run() {
                    show(child);
                }
            }, 500);
        }
    }


    private void hide(final View view) {
        Animation hideTranslateAnimation = new TranslateAnimation(0, 0, 0, view.getHeight());
        //初始化 Translate动画
//        AnimationSet set = new AnimationSet(true);
//        set.addAnimation(hideTranslateAnimation);
        //设置动画时间 (作用到每个动画)
        hideTranslateAnimation.setDuration(500);
        hideTranslateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int left = view.getLeft();
                int top = view.getTop();
                int right = view.getRight();
                int bottom = view.getBottom();
                int height = view.getHeight();
                view.clearAnimation();
                view.layout(left, top + height, right, bottom);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(hideTranslateAnimation);
    }


    private void show(final View view) {
        TranslateAnimation showTranslateAnimation = new TranslateAnimation(0, 0, view.getHeight(), 0);
//        AnimationSet set = new AnimationSet(true);
//        set.addAnimation(showTranslateAnimation);
        //设置动画时间 (作用到每个动画)
        showTranslateAnimation.setDuration(500);
        showTranslateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(showTranslateAnimation);
    }
}