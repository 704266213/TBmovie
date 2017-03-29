package com.golove.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    public static final int TYPE_ONE = 0xff05;

    private Context context;
    private LayoutInflater layoutInflater;
    private List<CartoonDetailModel> cartoonModels;
    private Picasso picasso;
    private int widthTwo;
    private int heightTwo;
    private int widthThree;
    private int heightThree;


    public DamaiAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        cartoonModels = new ArrayList<>();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        widthTwo = (width - 20) / 2 ;
        heightTwo = (int) (widthTwo / 5 * 3);
        widthThree = width / 3;
        heightThree = (int) (widthThree / 0.75);
        picasso = Picasso.with(GoloveApplication.goloveApplication);
    }

    public void addData(List<CartoonDetailModel> cartoonModels) {
        this.cartoonModels.addAll(cartoonModels);
        notifyDataSetChanged();
    }

    public int getPostionInType(int position) {
        return cartoonModels.get(position).getPosition();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEAD:
                View headView = layoutInflater.inflate(R.layout.cartoon_headview, parent, false);
                return new HeadViewHolder(headView);
            case TYPE_TITLE:
                View titleView = layoutInflater.inflate(R.layout.cartoon_title, parent, false);
                return new CartoonTitleViewHolder(titleView);
            case TYPE_TWO:
                View cartoonGridView = layoutInflater.inflate(R.layout.cartoon_two_column, parent, false);
                return new CartoonTwoColumnViewHolder(cartoonGridView);
            case TYPE_THREE:
                View cartoonThreeColumn = layoutInflater.inflate(R.layout.cartoon_three_column, parent, false);
                return new CartoonThreeColumnViewHolder(cartoonThreeColumn);
            case TYPE_ONE:
                View cartoonOneColumn = layoutInflater.inflate(R.layout.cartoon_one_column, parent, false);
                return new CartoonOneColumnViewHolder(cartoonOneColumn);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position != 0) {
            CartoonDetailModel cartoonModel = cartoonModels.get(position - 1);
            String title = cartoonModel.getTitle();
            if (holder instanceof CartoonTitleViewHolder) {
                CartoonTitleViewHolder cartoonTitleViewHolder = (CartoonTitleViewHolder) holder;
                cartoonTitleViewHolder.cartoonTitle.setText(cartoonModel.getTitle());
                cartoonTitleViewHolder.line.setVisibility(position == 1 ? View.GONE : View.VISIBLE);
            } else if (holder instanceof CartoonTwoColumnViewHolder) {
                CartoonTwoColumnViewHolder cartoonTwoColumnViewHolder = ((CartoonTwoColumnViewHolder) holder);
                cartoonTwoColumnViewHolder.title.setText(title);
                cartoonTwoColumnViewHolder.description.setText(cartoonModel.getRecommended_text());
                picasso.load(cartoonModel.getPic())
                        .resize(widthTwo, heightTwo)
                        .placeholder(R.drawable.placeholder_horizontal)
                        .into(cartoonTwoColumnViewHolder.cartoonImage);
            } else if (holder instanceof CartoonThreeColumnViewHolder) {
                CartoonThreeColumnViewHolder cartoonThreeColumnViewHolder = ((CartoonThreeColumnViewHolder) holder);
                cartoonThreeColumnViewHolder.title.setText(cartoonModel.getTitle());
                StringBuilder category = new StringBuilder();
                for (String str : cartoonModel.getCategory()) {
                    category.append(str).append(" ");
                }
                cartoonThreeColumnViewHolder.description.setText(category.toString().trim());
                picasso.load(cartoonModel.getPic())
                        .resize(widthThree, heightThree)
                        .placeholder(R.drawable.placeholder_vertical)
                        .into(cartoonThreeColumnViewHolder.cartoonImage);
            } else if (holder instanceof CartoonOneColumnViewHolder) {
                CartoonOneColumnViewHolder cartoonOneColumnViewHolder = ((CartoonOneColumnViewHolder) holder);
                cartoonOneColumnViewHolder.title.setText(cartoonModel.getTitle());
                cartoonOneColumnViewHolder.description.setText(cartoonModel.getRecommended_text());
                cartoonOneColumnViewHolder.category.setText(cartoonModel.getLabel_text());
                picasso.load(cartoonModel.getPic())
                        .fit()
                        .into(cartoonOneColumnViewHolder.cartoonImage);
            }


        }

    }


    @Override
    public int getItemCount() {
        return cartoonModels.size() + 1;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        } else {
            CartoonDetailModel cartoonModel = cartoonModels.get(position - 1);
            int type = cartoonModel.getItemType();
            switch (type) {
                case 0:
                    return TYPE_TITLE;
                case 1:
                    return TYPE_TWO;
                case 2:
                    return TYPE_THREE;
                case 3:
                    return TYPE_ONE;
                default:
                    return TYPE_ONE;
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_HEAD:
                            return 6;
                        case TYPE_TITLE:
                            return 6;
                        case TYPE_TWO:
                            return 3;
                        case TYPE_THREE:
                            return 2;
                        case TYPE_ONE:
                            return 6;
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
        View line;

        public CartoonTitleViewHolder(View itemView) {
            super(itemView);
            line = itemView.findViewById(R.id.line);
            cartoonTitle = (TextView) itemView.findViewById(R.id.cartoonTitle);
            more = (TextView) itemView.findViewById(R.id.more);
        }
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

    public class CartoonThreeColumnViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartoonImage;
        private TextView title;
        private TextView description;

        public CartoonThreeColumnViewHolder(View itemView) {
            super(itemView);
            cartoonImage = (ImageView) itemView.findViewById(R.id.cartoonImage);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }

    public class CartoonOneColumnViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartoonImage;
        private TextView title;
        private TextView category;
        private TextView description;

        public CartoonOneColumnViewHolder(View itemView) {
            super(itemView);
            cartoonImage = (ImageView) itemView.findViewById(R.id.cartoonImage);
            title = (TextView) itemView.findViewById(R.id.title);
            category = (TextView) itemView.findViewById(R.id.category);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }

}
