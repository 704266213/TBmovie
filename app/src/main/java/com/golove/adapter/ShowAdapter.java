package com.golove.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.model.CartoonList;
import com.golove.viewhold.HeadViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2017-03-03 20:15
 * 修改备注：
 */

public class ShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEAD = 0xff01;
    public static final int TYPE_ITEM = 0xff02;

    private Context context;
    private LayoutInflater layoutInflater;
    private List<CartoonList> cartoonLists;
    private Picasso picasso;

//    private CartoonTwoColumnAdapter cartoonTwoColumnAdapter;



    public ShowAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        cartoonLists = new ArrayList<>();
        picasso = Picasso.with(GoloveApplication.goloveApplication);

//        cartoonTwoColumnAdapter = new CartoonTwoColumnAdapter(context);
    }


    public void addData(List<CartoonList> cartoonLists) {
        this.cartoonLists.addAll(cartoonLists);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartoonLists.size() + 1;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_ITEM;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View headView = layoutInflater.inflate(R.layout.cartoon_headview, parent, false);
            return new HeadViewHolder(headView);
        } else {
            View showItem = layoutInflater.inflate(R.layout.show_item, null, false);
            return new ShowViewHolder(showItem);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_HEAD) {

        } else if (itemViewType == TYPE_ITEM) {
            ShowViewHolder showViewHolder = (ShowViewHolder) holder;
            CartoonList cartoonInfo = cartoonLists.get(position - 1);


            showViewHolder.title.setText(cartoonInfo.getCartoonTitle());


            CartoonTwoColumnAdapter cartoonTwoColumnAdapter = new CartoonTwoColumnAdapter(context);

            showViewHolder.recyclerView.setNestedScrollingEnabled(true);
            showViewHolder.recyclerView.setAdapter(cartoonTwoColumnAdapter);

            cartoonTwoColumnAdapter.addData(cartoonInfo.getData());

//            Log.e("XLog","========recyclerView=============" + showViewHolder.recyclerView);
//            Log.e("XLog","========cartoonTwoColumnAdapter===============" + showViewHolder.cartoonTwoColumnAdapter);

        }
    }


    public class ShowViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView more;
        private RecyclerView recyclerView;
//        private CartoonTwoColumnAdapter cartoonTwoColumnAdapter;

        public ShowViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            more = (TextView) itemView.findViewById(R.id.more);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);


            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 6);
            recyclerView.setLayoutManager(gridLayoutManager);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    return 3;
                }
            });

//            cartoonTwoColumnAdapter = new CartoonTwoColumnAdapter(context);


        }
    }

}
