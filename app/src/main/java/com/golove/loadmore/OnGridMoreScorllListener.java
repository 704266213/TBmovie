package com.golove.loadmore;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.golove.listener.OnLoadMoreListener;
import com.golove.ui.OnLoadDataListener;

public class OnGridMoreScorllListener extends OnLoadMoreScrollListener {

    public OnGridMoreScorllListener(OnLoadDataListener onLoadDataListener, OnLoadMoreListener onLoadMoreListener){
        super.onLoadMoreListener = onLoadMoreListener;
        super.onLoadDataListener = onLoadDataListener;
    }

    public int getLastVisibleItemPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int lastVisibleItemPosition = -1;
        if (layoutManager != null) {
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

}
