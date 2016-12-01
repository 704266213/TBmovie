package com.golove.loadmore;

import android.support.v7.widget.RecyclerView;

import com.golove.listener.OnLoadMoreListener;

public abstract class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;
    /**
     * 是否正在加载
     */
    protected OnLoadMoreListener onLoadMoreListener;

    private boolean isLoading = false;

    public void isLoading(boolean loadingMore) {
        isLoading = loadingMore;
    }

    /**
     * 当前滑动的状态
     */
    private int currentScrollState;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!isLoading) {
            lastVisibleItemPosition = getLastVisibleItemPosition(recyclerView);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!isLoading) {
            currentScrollState = newState;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1) {
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                    isLoading = true;
                }
            }
        }
    }


    public abstract int getLastVisibleItemPosition(RecyclerView recyclerView);

}
