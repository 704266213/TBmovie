package com.golove.loadmore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.golove.listener.OnLoadMoreListener;


public  class OnLinearLoadMoreListener extends OnLoadMoreScrollListener {


    public OnLinearLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        super.onLoadMoreListener = onLoadMoreListener;
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
