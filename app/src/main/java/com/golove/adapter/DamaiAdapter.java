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
import com.golove.transformation.PicassoRoundTransform;
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
    public static final int TYPE_FULL = 0xff06;

    private com.golove.listener.OnItemClickListener onItemClickListener;

    private Context context;
    private LayoutInflater layoutInflater;
    private List<CartoonDetailModel> cartoonModels;
    private Picasso picasso;
    private int widthTwo;
    private int heightTwo;
    private int widthThree;
    private int heightThree;
    private View headView;

    public DamaiAdapter(Context context,View headView,com.golove.listener.OnItemClickListener onItemClickListener) {
        this.context = context;
        this.headView = headView;
        this.onItemClickListener = onItemClickListener;
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

    public CartoonDetailModel getItem(int position){
        return cartoonModels.get(position);
    }

    public int getPostionInType(int position) {
        return cartoonModels.get(position).getPosition();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEAD:
                if (headView == null){
                     headView = layoutInflater.inflate(R.layout.cartoon_headview, parent, false);
                }
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
            case TYPE_FULL:
                View cartoonFullColumn = layoutInflater.inflate(R.layout.cartoon_full_column, parent, false);
                return new CartoonFullColumnViewHolder(cartoonFullColumn);
        }
        return null;
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
                cartoonTitleViewHolder.itemView.setOnClickListener(new OnItemClickListener(position));
            } else if (holder instanceof CartoonTwoColumnViewHolder) {
                CartoonTwoColumnViewHolder cartoonTwoColumnViewHolder = ((CartoonTwoColumnViewHolder) holder);
                cartoonTwoColumnViewHolder.title.setText(title);
                cartoonTwoColumnViewHolder.description.setText(cartoonModel.getRecommended_text());
                ViewGroup.LayoutParams layoutParams = cartoonTwoColumnViewHolder.cartoonImage.getLayoutParams();
                layoutParams.height = heightTwo;//设置图片的高度
                layoutParams.width = widthTwo; //设置图片的宽度
                cartoonTwoColumnViewHolder.cartoonImage.setLayoutParams(layoutParams);
                picasso.load(cartoonModel.getPic())
                        .resize(widthTwo, heightTwo)
                        .placeholder(R.drawable.placeholder_horizontal)
                        .into(cartoonTwoColumnViewHolder.cartoonImage);
                cartoonTwoColumnViewHolder.itemView.setOnClickListener(new OnItemClickListener(position));
            } else if (holder instanceof CartoonThreeColumnViewHolder) {
                CartoonThreeColumnViewHolder cartoonThreeColumnViewHolder = ((CartoonThreeColumnViewHolder) holder);
                cartoonThreeColumnViewHolder.title.setText(cartoonModel.getTitle());
                StringBuilder category = new StringBuilder();
                for (String str : cartoonModel.getCategory()) {
                    category.append(str).append(" ");
                }
                cartoonThreeColumnViewHolder.description.setText(category.toString().trim());
                ViewGroup.LayoutParams layoutParams = cartoonThreeColumnViewHolder.cartoonImage.getLayoutParams();
                layoutParams.height = heightThree;//设置图片的高度
                layoutParams.width = widthThree; //设置图片的宽度
                cartoonThreeColumnViewHolder.cartoonImage.setLayoutParams(layoutParams);
                picasso.load(cartoonModel.getPic())
                        .resize(widthThree, heightThree)
                        .placeholder(R.drawable.placeholder_vertical)
                        .into(cartoonThreeColumnViewHolder.cartoonImage);
                cartoonThreeColumnViewHolder.itemView.setOnClickListener(new OnItemClickListener(position));
            } else if (holder instanceof CartoonOneColumnViewHolder) {
                CartoonOneColumnViewHolder cartoonOneColumnViewHolder = ((CartoonOneColumnViewHolder) holder);
                cartoonOneColumnViewHolder.title.setText(cartoonModel.getTitle());
                cartoonOneColumnViewHolder.description.setText(cartoonModel.getRecommended_text());
                String [] categoryArray = cartoonModel.getCategory();
                int length = categoryArray.length;
                int size = cartoonOneColumnViewHolder.categorys.size();
                for(int i = 0 ; i < size ; i++){
                    TextView categoryText = cartoonOneColumnViewHolder.categorys.get(i);
                    if ( i < length ){
                        categoryText.setVisibility(View.VISIBLE);
                        categoryText.setText(categoryArray[i]);
                    } else {
                        categoryText.setVisibility(View.GONE);
                    }
                }
                picasso.load(cartoonModel.getPic())
                        .fit()
                        .transform(new PicassoRoundTransform())
                        .placeholder(R.drawable.placeholder_vertical)
                        .into(cartoonOneColumnViewHolder.cartoonImage);
                cartoonOneColumnViewHolder.itemView.setOnClickListener(new OnItemClickListener(position));

            } else if (holder instanceof CartoonFullColumnViewHolder) {
                CartoonFullColumnViewHolder cartoonFullColumnViewHolder = ((CartoonFullColumnViewHolder) holder);
                cartoonFullColumnViewHolder.title.setText(cartoonModel.getTitle());
                cartoonFullColumnViewHolder.recommend.setText(cartoonModel.getRecommended_text());
                cartoonFullColumnViewHolder.commentCount.setText(String.valueOf(cartoonModel.getComments_count()));
                picasso.load(cartoonModel.getPic())
                        .placeholder(R.drawable.placeholder_vertical)
                        .fit()
                        .into(cartoonFullColumnViewHolder.cartoonImage);
                cartoonFullColumnViewHolder.itemView.setOnClickListener(new OnItemClickListener(position));
            }
        }

    }


    @Override
    public int getItemCount() {
        return cartoonModels.size() + 1;
    }

    public int getItemViewType(int position) {
        int itemType = 0;
        if (position == 0) {
            itemType =  TYPE_HEAD;
        } else {
            CartoonDetailModel cartoonModel = cartoonModels.get(position - 1);
            int type = cartoonModel.getItemType();
            switch (type) {
                case 0:
                    itemType = TYPE_TITLE;
                    break;
                case 1:
                    itemType =  TYPE_TWO;
                    break;
                case 2:
                    itemType =  TYPE_THREE;
                    break;
                case 3:
                    itemType =  TYPE_ONE;
                    break;
                case 4:
                    itemType =  TYPE_FULL;
                    break;
            }
        }
        return itemType;
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
                    int spanSize = 6;
                    switch (type) {
                        case TYPE_HEAD:
                            spanSize = 6;
                            break;
                        case TYPE_TITLE:
                            spanSize = 6;
                            break;
                        case TYPE_TWO:
                            spanSize = 3;
                            break;
                        case TYPE_THREE:
                            spanSize = 2;
                            break;
                        case TYPE_ONE:
                            spanSize = 6;
                            break;
                        case TYPE_FULL:
                            spanSize = 6;
                            break;
                    }
                    return spanSize;
                }
            });
        }
    }


    public class CartoonTitleViewHolder extends RecyclerView.ViewHolder {

        TextView cartoonTitle;
        TextView more;
        View line;
        private View itemView;

        public CartoonTitleViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            line = itemView.findViewById(R.id.line);
            cartoonTitle = (TextView) itemView.findViewById(R.id.cartoonTitle);
            more = (TextView) itemView.findViewById(R.id.more);
        }
    }


    public class CartoonTwoColumnViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartoonImage;
        private TextView title;
        private TextView description;
        private View itemView;

        public CartoonTwoColumnViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            cartoonImage = (ImageView) itemView.findViewById(R.id.cartoonImage);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }

    public class CartoonThreeColumnViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartoonImage;
        private TextView title;
        private TextView description;
        private View itemView;

        public CartoonThreeColumnViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            cartoonImage = (ImageView) itemView.findViewById(R.id.cartoonImage);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }

    public class CartoonOneColumnViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartoonImage;
        private TextView title;
        private List<TextView> categorys;
        private TextView description;
        private View itemView;

        public CartoonOneColumnViewHolder(View itemView) {
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

    public class CartoonFullColumnViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartoonImage;
        private TextView recommend;
        private TextView commentCount;
        private TextView title;
        private View itemView;


        public CartoonFullColumnViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            cartoonImage = (ImageView) itemView.findViewById(R.id.cartoonImage);
            recommend = (TextView) itemView.findViewById(R.id.recommend);
            commentCount = (TextView) itemView.findViewById(R.id.commentCount);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    public class OnItemClickListener implements View.OnClickListener{

        private int position;
        public OnItemClickListener(int position){
            this.position = position;
        }

        public void onClick(View v) {
            if (onItemClickListener != null){
                onItemClickListener.onItemClick(v,position - 1);
            }

        }
    }

}
