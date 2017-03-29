package com.golove.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.golove.adapter.DamaiAdapter;

/**
 * Created by shuhj on 2017/3/24.
 */

public class CartoonDivider extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[] { android.R.attr.listDivider };
    private Drawable mDivider;

    public CartoonDivider(Context context){
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        //先初始化一个Paint来简单指定一下Canvas的颜色，就黑的吧！
        Paint paint = new Paint();
        paint.setColor(parent.getContext().getResources().getColor(android.R.color.black));
        //获得RecyclerView中总条目数量
        int childCount = parent.getChildCount();
        //遍历一下
        for (int i = 0; i < childCount; i++) {
            if (i == 0) {
                //如果是第一个条目，那么我们就不画边框了
                continue;
            }
            //获得子View，也就是一个条目的View，准备给他画上边框
            View childView = parent.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(childView);

            if(viewHolder instanceof DamaiAdapter.CartoonTwoColumnViewHolder){
                //先获得子View的长宽，以及在屏幕上的位置，方便我们得到边框的具体坐标
                final View child = parent.getChildAt(i);

                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = child.getTop() - params.topMargin;
                final int bottom = child.getBottom() + params.bottomMargin;
//                final int left = child.getRight() + params.rightMargin;
//                final int right = left + mDivider.getIntrinsicWidth();
                final int left = child.getRight() + 10;
                final int right = left + 10;

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
//        parent.getChildViewHolder()
    }
}
