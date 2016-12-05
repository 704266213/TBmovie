package com.golove.loadmore;

import android.support.v7.widget.RecyclerView;

import com.golove.listener.OnLoadMoreListener;
import com.golove.ui.OnLoadDataListener;

public abstract class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;
    /**
     * 是否正在加载
     */
    protected OnLoadMoreListener onLoadMoreListener;
    protected OnLoadDataListener onLoadDataListener;

    //是否正在加载数据
    private boolean isLoadingMore = false;
    //是否有更多数据
    private boolean hasMore = true;

    public void isLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    /**
     * 当前滑动的状态
     */
    private int currentScrollState;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (hasMore) {
            lastVisibleItemPosition = getLastVisibleItemPosition(recyclerView);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (hasMore) {
            currentScrollState = newState;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1) {
                //自动显示加载更多布局的加载中的布局
                if(onLoadDataListener != null && hasMore){
                    onLoadDataListener.loadingDataView();
                }
                if (onLoadMoreListener != null && !isLoadingMore) {
                    isLoadingMore = true;
                    onLoadMoreListener.onLoadMore();
                }
            }
        }
    }


    public abstract int getLastVisibleItemPosition(RecyclerView recyclerView);

}
