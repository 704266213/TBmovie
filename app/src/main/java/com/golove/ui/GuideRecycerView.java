package com.golove.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-12-12 22:56
 * 修改备注：
 */

public class GuideRecycerView extends RecyclerView {


    private float startX;
    private float startY;
    private int touchSlop;
    private boolean isChildHandle;
    private View touchView;
    private float distanceX;
    private float distanceY;

    private int position;

    public GuideRecycerView(Context context) {
        super(context);
    }

    public GuideRecycerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GuideRecycerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                distanceX = 0;
                distanceY = 0;
                // 获取按下的那个View
                position = pointToPosition((int) startX, (int) startY);
                touchView = getChildAt(position);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("XLog","================" + position);
//                if (position == 0) {
                    // 获取当前手指位置
                    float endY = ev.getY();
                    float endX = ev.getX();
                    distanceX = Math.abs(endX - startX);
                    distanceY = Math.abs(endY - startY);
                    // 如果X轴位移大于Y轴位移，那么将事件交给child处理。
                    if (distanceX > touchSlop && distanceX < distanceY) {
                        Log.e("XLog","================" + getParent().getParent().getParent());
                        getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
//                        return false;
                    }
//                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                break;

        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 当前手指位置的position(屏幕上显示的第一个Item为0)
     */
    private Rect touchFrame;

    private int pointToPosition(int x, int y) {
        Rect frame = touchFrame;
        if (frame == null) {
            touchFrame = new Rect();
            frame = touchFrame;
        }
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return i;
                }
            }
        }
        return -1;
    }
}
