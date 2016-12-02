package com.golove.loadmore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.golove.listener.OnLoadMoreListener;
import com.golove.ui.OnLoadDataListener;


public  class OnLinearLoadMoreListener extends OnLoadMoreScrollListener {


    public OnLinearLoadMoreListener(OnLoadDataListener onLoadDataListener, OnLoadMoreListener onLoadMoreListener){
        super.onLoadMoreListener = onLoadMoreListener;
        super.onLoadDataListener = onLoadDataListener;
    }

    public int getLastVisibleItemPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int lastVisibleItemPosition = -1;
        if (layoutManager != null) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }
        return lastVisibleItemPosition;
    }

}
