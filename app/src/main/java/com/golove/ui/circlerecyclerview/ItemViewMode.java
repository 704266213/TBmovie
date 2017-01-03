package com.golove.ui.circlerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/9/14
 */
public interface ItemViewMode {
    void applyToView(View v, RecyclerView parent);
}
