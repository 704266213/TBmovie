package com.golove.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 项目名称：
 * 类的描述：
 * 创建人：alan
 * 创建时间：2016/8/24 10:59
 */
public class PagerRecyclerView extends RecyclerView {


    private float startX;
    private float startY;
    private int touchSlop;
    private float distanceX;
    private float distanceY;

    public PagerRecyclerView(Context context) {
        super(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public PagerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public PagerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
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
                break;
            case MotionEvent.ACTION_MOVE:
                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                distanceX = Math.abs(endX - startX);
                distanceY = Math.abs(endY - startY);
                // 如果X轴位移大于Y轴位移，那么将事件交给child处理。
                Log.e("XLog", "================" + (distanceY > touchSlop && distanceY > distanceX));

                if (distanceY > touchSlop && distanceY > distanceX) {
                    Log.e("XLog", "================" + getParent().getParent().getParent());
                    getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
                startY = ev.getY();
                startX = ev.getX();
                break;

            case MotionEvent.ACTION_UP:

                break;

        }

        return super.onInterceptTouchEvent(ev);
    }


}
