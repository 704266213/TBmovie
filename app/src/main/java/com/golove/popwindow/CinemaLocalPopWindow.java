package com.golove.popwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.golove.R;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-12-12 00:01
 * 修改备注：
 */

public class CinemaLocalPopWindow extends PopupWindow {

    private View filterView;

    public CinemaLocalPopWindow(Context context){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        filterView = layoutInflater.inflate(R.layout.cinema_location,null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(filterView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出窗体可点击
//        this.setFocusable(true);
//        this.setOutsideTouchable(true);
        // 实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
//        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.cinemaPopWindowAnimation);

    }


}
