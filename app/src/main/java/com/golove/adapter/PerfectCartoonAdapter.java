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
import com.golove.listener.OnItemClickListener;
import com.golove.model.CartoonInfoModel;
import com.golove.transformation.PicassoRoundTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2017-03-03 23:04
 * 修改备注：
 */

public class PerfectCartoonAdapter extends RecyclerView.Adapter<PerfectCartoonAdapter.PerfectCartoonViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<CartoonInfoModel> cartoonInfoModels;
    private Picasso picasso;
    private OnItemClickListener onItemClickListener;

    public PerfectCartoonAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        layoutInflater = LayoutInflater.from(context);
        cartoonInfoModels = new ArrayList<>();
        picasso = Picasso.with(GoloveApplication.goloveApplication);
    }

    public void addData(List<CartoonInfoModel> cartoonInfoModels) {
        this.cartoonInfoModels.addAll(cartoonInfoModels);
        notifyDataSetChanged();
    }

    @Override
    public PerfectCartoonAdapter.PerfectCartoonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.perfect_cartoon_item, parent, false);
        return new PerfectCartoonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PerfectCartoonAdapter.PerfectCartoonViewHolder holder, final int position) {
        CartoonInfoModel cartoonInfoModel = cartoonInfoModels.get(position);
        holder.title.setText(cartoonInfoModel.getTitle());
        holder.description.setText(cartoonInfoModel.getDescription());
        String[] categoryArray = cartoonInfoModel.getCategory();
        int length = categoryArray.length;
        int size = holder.categorys.size();
        for (int i = 0; i < size; i++) {
            TextView categoryText = holder.categorys.get(i);
            if (i < length) {
                categoryText.setVisibility(View.VISIBLE);
                categoryText.setText(categoryArray[i]);
            } else {
                categoryText.setVisibility(View.GONE);
            }
        }
        picasso.load(cartoonInfoModel.getCover_image_url())
                .fit()
                .transform(new PicassoRoundTransform())
                .placeholder(R.drawable.placeholder_vertical)
                .into(holder.cartoonImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartoonInfoModels.size();
    }


    public class PerfectCartoonViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartoonImage;
        private TextView title;
        private List<TextView> categorys;
        private TextView description;
        private View itemView;

        public PerfectCartoonViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            categorys = new ArrayList<>();
            cartoonImage = (ImageView) itemView.findViewById(R.id.cartoonImage);
            title = (TextView) itemView.findViewById(R.id.title);
            TextView category = (TextView) itemView.findViewById(R.id.category);
            categorys.add(category);
            TextView category2 = (TextView) itemView.findViewById(R.id.category2);
            categorys.add(category2);
            TextView category3 = (TextView) itemView.findViewById(R.id.category3);
            categorys.add(category3);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

}
