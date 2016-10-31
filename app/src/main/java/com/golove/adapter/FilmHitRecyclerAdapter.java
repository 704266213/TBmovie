package com.golove.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.golove.R;

import java.util.List;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-08-08 15:28
 * 修改备注：
 */
public class FilmHitRecyclerAdapter extends RecyclerView.Adapter<FilmHitRecyclerAdapter.RecyclerViewHolder> {

    private static final int IS_HEADER = 2;
    private static final int IS_FOOTER = 3;
    private static final int IS_NORMAL = 1;

    private View headView;

    List<String> mListData;

    public FilmHitRecyclerAdapter(List<String> mListData) {
        this.mListData = mListData;
    }

    public void setHeadView(View headView) {
        this.headView = headView;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else if (position > 0 && position < mListData.size()) {
            return IS_NORMAL;
        } else {
            return IS_FOOTER;
        }
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == IS_HEADER) {
            if(headView != null){
                view = headView;
            }

        } else if (viewType == IS_FOOTER) {

        } else if (viewType == IS_NORMAL) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_hit_list_item, viewGroup, false);
        }

        return new RecyclerViewHolder(view, viewType);
    }


    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == IS_NORMAL) {
//            recyclerViewHolder.name.setText(mListData.get(position));
        } else if (viewType == IS_HEADER) {

        } else {

        }

    }

    @Override
    public int getItemCount() {
        return mListData == null ? 1 : mListData.size() + 1;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public RecyclerViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == IS_NORMAL) {
                name = (TextView) itemView.findViewById(R.id.name);
            } else if (viewType == IS_HEADER) {


            } else {

            }
        }
    }
}
