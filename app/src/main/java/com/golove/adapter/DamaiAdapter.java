package com.golove.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.model.CartoonDetailModel;
import com.golove.viewhold.HeadViewHolder;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuhj on 2017/3/23.
 */

public class DamaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEAD = 0xff01;
    public static final int TYPE_TITLE = 0xff02;
    public static final int TYPE_TWO = 0xff03;
    public static final int TYPE_THREE = 0xff04;
    public static final int TYPE_TYPE3 = 0xff05;
    public static final int TYPE_TYPE4 = 0xff06;

    private LayoutInflater layoutInflater;
    private List<CartoonDetailModel> cartoonModels;


    public DamaiAdapter(Context context){
        layoutInflater =LayoutInflater.from(context);
        cartoonModels = new ArrayList<>();
    }

    public void addData(List<CartoonDetailModel> cartoonModels) {
        this.cartoonModels.addAll(cartoonModels);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_HEAD:
                View headView = layoutInflater.inflate(R.layout.cartoon_headview, parent, false);
                return new HeadViewHolder(headView);
            case TYPE_TITLE:
                View titleView = layoutInflater.inflate(R.layout.cartoon_title, parent, false);
                return new CartoonTitleViewHolder(titleView);
            default:
                View cartoonGridView = layoutInflater.inflate(R.layout.cartoon_grid, parent, false);
                return new CartoonGridViewHolder(cartoonGridView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0){
            CartoonDetailModel cartoonModel = cartoonModels.get(position - 1);
            if (holder instanceof CartoonTitleViewHolder){
                ((CartoonTitleViewHolder) holder).cartoonTitle.setText(cartoonModel.getCartoonTitle());
            }else if (holder instanceof CartoonGridViewHolder){
                CartoonGridViewHolder cartoonGridViewHolder = ((CartoonGridViewHolder) holder);
                cartoonGridViewHolder.title.setText(cartoonModel.getTitle());
                 Picasso.with(GoloveApplication.goloveApplication).load(cartoonModel.getCover_image_url()).into(cartoonGridViewHolder.cartoonImage);
            }
        }

    }


    @Override
    public int getItemCount() {
        return cartoonModels.size() + 1;
    }

    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_HEAD;
        } else {
            CartoonDetailModel cartoonModel = cartoonModels.get(position - 1);
            int type = cartoonModel.getCartoonLayout();
            switch (type) {
                case 0:
                    return TYPE_TITLE;
                case 1:
                    return TYPE_TWO;
                default:
                    return TYPE_THREE;
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        case TYPE_HEAD:
                            return 6;
                        case TYPE_TITLE:
                            return 6;
                        case TYPE_TWO:
                            return 3;
                        case TYPE_THREE:
                            return 2;
                        default:
                            return 6;
                    }
                }
            });
        }
    }


    public class CartoonTitleViewHolder extends RecyclerView.ViewHolder {

        TextView cartoonTitle;
        TextView more;
        public CartoonTitleViewHolder(View itemView) {
            super(itemView);
            cartoonTitle = (TextView) itemView.findViewById(R.id.cartoonTitle);
            more = (TextView) itemView.findViewById(R.id.more);
        }
    }

    public class CartoonGridViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartoonImage;
        private TextView title;
        public CartoonGridViewHolder(View itemView) {
            super(itemView);
            cartoonImage = (ImageView) itemView.findViewById(R.id.cartoonImage);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }



}
