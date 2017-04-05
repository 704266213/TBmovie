package com.golove.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.model.CartoonChapterModel;
import com.golove.viewhold.HeadViewHolder;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shuhj on 2017/4/5.
 */

public class CartoonChapterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int IS_HEADER = 2;
    public static final int IS_NORMAL = 1;

    private List<CartoonChapterModel> cartoonChapterModels;
    protected View headView;
    private LayoutInflater layoutInflater;
    private SimpleDateFormat simpleDateFormat;
    private Picasso picasso;


    public CartoonChapterAdapter(LayoutInflater layoutInflater) {
        cartoonChapterModels = new ArrayList<>();
        this.layoutInflater = layoutInflater;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        picasso = Picasso.with(GoloveApplication.goloveApplication);
    }

    public void setHeadView(View headView) {
        this.headView = headView;
    }

    public void addDataToList(List<CartoonChapterModel> cartoonChapterModels) {
        this.cartoonChapterModels.addAll(cartoonChapterModels);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IS_HEADER) {
            return new HeadViewHolder(headView);
        } else {
            View view = layoutInflater.inflate(R.layout.cartoon_chapter_item, parent, false);
            return new CartoonChapterViewHold(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0) {
            CartoonChapterModel cartoonChapterModel = cartoonChapterModels.get(position - 1);
            CartoonChapterViewHold cartoonChapterViewHold = (CartoonChapterViewHold) holder;
            cartoonChapterViewHold.chapter.setText(cartoonChapterModel.getTitle());
            cartoonChapterViewHold.likesCount.setText(String.valueOf(cartoonChapterModel.getLikes_count()));

            Date date = new Date(cartoonChapterModel.getUpdated_at() * 1000);
            cartoonChapterViewHold.updatedTime.setText(simpleDateFormat.format(date));
            picasso.load(cartoonChapterModel.getCover_image_url())
                    .into(cartoonChapterViewHold.cartoonImage);
        }
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else {
            return IS_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return cartoonChapterModels.size() + 1;
    }

    public class CartoonChapterViewHold extends RecyclerView.ViewHolder {

        private ImageView cartoonImage;
        private TextView chapter;
        private TextView updatedTime;
        private TextView likesCount;

        public CartoonChapterViewHold(View itemView) {
            super(itemView);

            cartoonImage = (ImageView) itemView.findViewById(R.id.cartoonImage);
            chapter = (TextView) itemView.findViewById(R.id.chapter);
            updatedTime = (TextView) itemView.findViewById(R.id.updatedTime);
            likesCount = (TextView) itemView.findViewById(R.id.likesCount);
        }
    }

}
