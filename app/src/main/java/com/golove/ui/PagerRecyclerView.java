package com.golove.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 项目名称：integrationwall
 * 类的描述：
 * 创建人：alan
 * 创建时间：2016/8/24 10:59
 */
public class PagerRecyclerView extends RecyclerView {
    public PagerRecyclerView(Context context) {
        super(context);
    }

    public PagerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PagerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private int lastX;
    private int lastY;

//    public boolean dispatchTouchEvent(MotionEvent e) {
//        int curX = (int) e.getX();
//        int curY = (int) e.getY();
//
//        Log.e("TAG","====Parent======" + getParent().getParent().getParent());
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offersetX = Math.abs(curX - lastX);
//                int offersetY = Math.abs(curY - lastY);
//                if (offersetX < offersetY) {
//                    getParent().getParent().getParent().requestDisallowInterceptTouchEvent(true);
//                }
//                lastX = curX;
//                lastY = curY;
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
//        return super.dispatchTouchEvent(e);
//    }

}
