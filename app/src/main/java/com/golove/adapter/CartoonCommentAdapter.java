package com.golove.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.model.CartoonChapterModel;
import com.golove.model.CartoonUserModel;
import com.golove.model.ReviewModel;
import com.golove.ui.roundedimageview.RoundedTransformationBuilder;
import com.golove.viewhold.FooterViewHolder;
import com.golove.viewhold.HeadViewHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by shuhj on 2017/4/7.
 */

public class CartoonCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int IS_FOOTER = 3;
    private static final int IS_HEADER = 2;
    public static final int IS_NORMAL = 1;

    private List<ReviewModel> reviewModels;
    protected View headView;
    private View footerView;
    private LayoutInflater layoutInflater;
    private SimpleDateFormat simpleDateFormat;
    private Picasso picasso;
    private final Transformation mTransformation;

    public CartoonCommentAdapter(LayoutInflater layoutInflater) {
        reviewModels = new ArrayList<>();
        this.layoutInflater = layoutInflater;
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        picasso = Picasso.with(GoloveApplication.goloveApplication);
        mTransformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(30)
                .oval(false)
                .build();
    }

    public void setHeadView(View headView) {
        this.headView = headView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    public void addDataToList(List<ReviewModel> reviewModels) {
        this.reviewModels.addAll(reviewModels);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == IS_HEADER) {
            return new HeadViewHolder(headView);
        } else if (viewType == IS_NORMAL) {
            View view = layoutInflater.inflate(R.layout.cartoon_comment_item, parent, false);
            return new CartoonCommentAdapter.CartoonCommentViewHold(view);
        } else {
            return new FooterViewHolder(footerView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == IS_NORMAL) {
            ReviewModel reviewModel = reviewModels.get(position - 1);
            CartoonUserModel userModel = reviewModel.getUser();
            CartoonCommentAdapter.CartoonCommentViewHold cartoonCommentViewHold = (CartoonCommentAdapter.CartoonCommentViewHold) holder;

            cartoonCommentViewHold.nickname.setText(userModel.getNickname());
            cartoonCommentViewHold.likesCount.setText(String.valueOf(reviewModel.getLikes_count()));
            cartoonCommentViewHold.content.setText(reviewModel.getContent());
            Date date = new Date(reviewModel.getCreated_at() * 1000);
            cartoonCommentViewHold.updatedTime.setText(simpleDateFormat.format(date));
            picasso.load(userModel.getAvatar_url())
                    .fit()
                    .transform(mTransformation)
                    .into(cartoonCommentViewHold.userImage);
        }
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else if (position == getItemCount() - 1) {
            return IS_FOOTER;
        } else {
            return IS_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return reviewModels.size() + 2;
    }

    public class CartoonCommentViewHold extends RecyclerView.ViewHolder {

        private ImageView userImage;
        private TextView nickname;
        private TextView updatedTime;
        private TextView likesCount;
        private TextView content;

        public CartoonCommentViewHold(View itemView) {
            super(itemView);
            userImage = (ImageView) itemView.findViewById(R.id.userImage);
            nickname = (TextView) itemView.findViewById(R.id.nickname);
            updatedTime = (TextView) itemView.findViewById(R.id.updatedTime);
            likesCount = (TextView) itemView.findViewById(R.id.likesCount);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

}
