package com.golove.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.golove.ui.footer.FooterView;
import com.golove.viewhold.FooterViewHolder;
import com.golove.viewhold.HeadViewHolder;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int IS_HEADER = 2;
    private static final int IS_FOOTER = 3;
    private static final int IS_NORMAL = 1;

    protected List<T> listData = new ArrayList<>();

    protected View headView;
    private View footerView;

    public void setHeadView(View headView) {
        this.headView = headView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    public void addData(List<T> listData) {
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public void addFreshData(List<T> listData) {
        this.listData.clear();
        addData(listData);
    }

    @Override
    public int getItemCount() {
        if (listData.size() == 0) {
            return 0;
        } else {
            int size = listData.size();
            if (headView != null) {
                return size + 2;
            } else {
                return size + 1;
            }
        }
    }

    public int getItemViewType(int position) {
        if (headView != null) {
            if (position == 0) {
                return IS_HEADER;
            } else if (listData.size() + 1 == position) {
                return IS_FOOTER;
            }
        } else {
            if (listData.size() == position) {
                return IS_FOOTER;
            }
        }
        return IS_NORMAL;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == IS_FOOTER) {
            if (footerView == null) {
                footerView = new FooterView(viewGroup.getContext());
            }
            return new FooterViewHolder(footerView);
        } else if (viewType == IS_HEADER) {
            return new HeadViewHolder(headView);
        } else {
            return onCreateBodyViewHolder(viewGroup, viewType);
        }
    }


    public void onBindViewHolder(RecyclerView.ViewHolder recyclerViewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == IS_NORMAL) {
            int realPosition = headView == null ? position : position - 1;
            onBodyBindViewHolder((VH) recyclerViewHolder, realPosition);
        }
    }

    public abstract void onBodyBindViewHolder(VH recyclerViewHolder, int position);


    public abstract VH onCreateBodyViewHolder(ViewGroup viewGroup, int viewType);

}
