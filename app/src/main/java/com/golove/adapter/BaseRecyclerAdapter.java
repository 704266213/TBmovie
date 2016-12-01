package com.golove.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.golove.R;
import com.golove.loadmore.FooterViewHolder;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int IS_FOOTER = 2;
    private static final int IS_NORMAL = 1;
    protected List<T> listData = new ArrayList<>();
    private View footerView;

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
            return listData.size() + 1;
        }
    }

    public int getItemViewType(int position) {
        if (position == listData.size()) {
            return IS_FOOTER;
        } else {
            return IS_NORMAL;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == IS_FOOTER) {
            if (footerView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
                footerView = layoutInflater.inflate(R.layout.footer_loading, null, false);
            }
            return new FooterViewHolder(footerView);
        } else {
            return onCreateBodyViewHolder(viewGroup, viewType);
        }
    }


    public void onBindViewHolder(RecyclerView.ViewHolder recyclerViewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == IS_NORMAL) {
            onBodyBindViewHolder((VH) recyclerViewHolder, position);
        }
    }

    public abstract void onBodyBindViewHolder(VH recyclerViewHolder, int position);


    public abstract VH onCreateBodyViewHolder(ViewGroup viewGroup, int viewType);

}
