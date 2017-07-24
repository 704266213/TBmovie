package com.golove.listener;

import android.view.View;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2017-07-07 16:02
 * 修改备注：
 */

public class OnRecycleViewClickListener implements View.OnClickListener {

    private OnRecycleViewItemClickListener onRecycleViewItemCallBackListener;
    //点击的序号
    private int position;
    //recycleView的item对象
    private View itemView;


    public OnRecycleViewClickListener(OnRecycleViewItemClickListener onRecycleViewItemCallBackListener, View itemView, int position) {
        this.onRecycleViewItemCallBackListener = onRecycleViewItemCallBackListener;
        this.position = position;
        this.itemView = itemView;
    }


    @Override
    public void onClick(View v) {
        if (onRecycleViewItemCallBackListener != null) {
            onRecycleViewItemCallBackListener.onRecycleViewItemClick(itemView, position, v);
        }

    }

    public interface OnRecycleViewItemClickListener {

        void onRecycleViewItemClick(View itemView, int position, View clickView);

    }


}
