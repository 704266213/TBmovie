package com.golove.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 *
 */

public class BannerView extends RelativeLayout {

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //自定义ViewPager宽高比
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getMeasuredWidth();
        int height = 0;
        if (width != 0) {
            height = MeasureSpec.makeMeasureSpec((width * 4 / 10), MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, height);
    }
}
