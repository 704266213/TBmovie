package com.golove.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-08-08 20:57
 * 修改备注：
 */
public class ViewPagerRecycleView extends ViewPager {
    public ViewPagerRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPagerRecycleView(Context context) {
        super(context);
    }

    private int lastX;
    private int lastY;

//    public boolean onInterceptTouchEvent(MotionEvent e) {
//        int curX = (int) e.getX();
//        int curY = (int) e.getY();

//        switch (e.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int offersetX = Math.abs(curX - lastX);
//                int offersetY = Math.abs(curY - lastY);
//                if (offersetX < offersetY) {
////                    requestDisallowInterceptTouchEvent(true);
//                    return false;
//                }
//                lastX = curX;
//                lastY = curY;
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
//        return super.onInterceptTouchEvent(e);
//    }

}
