package com.golove.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.model.CartoonDetailModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2017-03-03 22:11
 * 修改备注：
 */

public class CartoonTwoColumnAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<CartoonDetailModel> cartoonDetailModels;
    private Picasso picasso;


    public CartoonTwoColumnAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        cartoonDetailModels = new ArrayList<>();
        picasso = Picasso.with(GoloveApplication.goloveApplication);
    }

    public void addData(List<CartoonDetailModel> cartoonModels) {
//        this.cartoonDetailModels.clear();
//        this.cartoonDetailModels.addAll(cartoonModels);
//        notifyDataSetChanged();

        int newSize = cartoonModels.size();
        int oldSize = this.cartoonDetailModels.size();
        if(newSize < oldSize){
            notifyItemMoved(newSize,oldSize);
        }

        this.cartoonDetailModels.clear();
        this.cartoonDetailModels.addAll(cartoonModels);
        notifyDataSetChanged();


    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cartoonGridView = layoutInflater.inflate(R.layout.cartoon_two_column, parent, false);
        return new CartoonTwoColumnViewHolder(cartoonGridView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CartoonDetailModel cartoonModel = cartoonDetailModels.get(position);
        String title = cartoonModel.getTitle();
        String description = cartoonModel.getDescription();

        CartoonTwoColumnViewHolder cartoonTwoColumnViewHolder = ((CartoonTwoColumnViewHolder) holder);
        cartoonTwoColumnViewHolder.title.setText(title);
        cartoonTwoColumnViewHolder.description.setText(description);
        picasso.load(cartoonModel.getPic())
                .into(cartoonTwoColumnViewHolder.cartoonImage);

    }


    @Override
    public int getItemCount() {
        return cartoonDetailModels.size();
    }

    public class CartoonTwoColumnViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartoonImage;
        private TextView title;
        private TextView description;

        public CartoonTwoColumnViewHolder(View itemView) {
            super(itemView);
            cartoonImage = (ImageView) itemView.findViewById(R.id.cartoonImage);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }
}
