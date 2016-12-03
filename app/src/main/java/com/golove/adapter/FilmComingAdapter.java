package com.golove.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.golove.R;
import com.golove.ui.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-08-08 17:41
 * 修改备注：
 */
public class FilmComingAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private ArrayList<String> items = new ArrayList<String>();

    public FilmComingAdapter() {
        setHasStableIds(true);
    }

    public void add(String object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, String object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<? extends String> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(String... items) {
        addAll(Arrays.asList(items));
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
//        return position;
        Log.e("XLog","===================" + getItem(position).hashCode());
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.film_hit_list_item, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        TextView textView = (TextView) holder.itemView;
//        textView.setText(getItem(position));
    }

    @Override
    public long getHeaderId(int position) {
//        if (position == 0) {
//            return -1;
//        } else {
        Log.e("XLog","===================" + getItem(position).charAt(0));
        Log.e("XLog","======position=============" + position);

        return getItem(position).charAt(0);
//        }
//        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_coming_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(String.valueOf(getItem(position).charAt(0)));
    }


}



