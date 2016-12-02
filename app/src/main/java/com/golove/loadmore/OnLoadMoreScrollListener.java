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

    private boolean isLoadingMore = false;

    public void isLoadingMore(boolean loadingMore) {
        isLoadingMore = loadingMore;
    }

    /**
     * 当前滑动的状态
     */
    private int currentScrollState;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!isLoadingMore) {
            lastVisibleItemPosition = getLastVisibleItemPosition(recyclerView);
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (!isLoadingMore) {
            currentScrollState = newState;
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0 && currentScrollState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItemPosition) >= totalItemCount - 1) {
                //自动显示加载更多布局的加载中的布局
                if(onLoadDataListener != null){
                    onLoadDataListener.loadingDataView();
                }
                if (onLoadMoreListener != null) {
                    isLoadingMore = true;
                    onLoadMoreListener.onLoadMore(isLoadingMore);
                }
            }
        }
    }


    public abstract int getLastVisibleItemPosition(RecyclerView recyclerView);

}
