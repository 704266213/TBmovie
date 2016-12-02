package com.golove.loadmore;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


public abstract class OnStaggeredLoadMoreScollerListener extends OnLoadMoreScrollListener {

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    public int getLastVisibleItemPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        if (lastPositions != null) {
            lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
        }
        staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
        int lastVisibleItemPosition = findMax(lastPositions);
        return lastVisibleItemPosition;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

}
