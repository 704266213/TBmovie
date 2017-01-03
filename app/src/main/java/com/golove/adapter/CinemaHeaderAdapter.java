package com.golove.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.golove.GoloveApplication;
import com.golove.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shuhj on 2016/12/20.
 */

public class CinemaHeaderAdapter extends RecyclerView.Adapter<CinemaHeaderAdapter.CinemaHeaderViewHolder> {

    private List<String> bannerImages = new ArrayList<>();
    private int size ;
    private LinearLayout indicators;
    private int lastPosition;

    public CinemaHeaderAdapter(LinearLayout indicators){
        this.indicators = indicators;
    }

    public void addDataToList(List<String> bannerImages) {
        this.bannerImages.clear();
        this.bannerImages.addAll(bannerImages);
        size = bannerImages.size();
        initIndicators();
        notifyDataSetChanged();
    }

    private void initIndicators() {
        if (indicators.getChildCount() != bannerImages.size() && bannerImages.size() > 1) {
            indicators.removeAllViews();
            Resources res = indicators.getResources();
            int size = res.getDimensionPixelOffset(R.dimen.indicator_size);
            int margin = res.getDimensionPixelOffset(R.dimen.indicator_margin);
            for (int i = 0; i < bannerImages.size() ; i++) {
                ImageView indicator = new ImageView(indicators.getContext());
                indicator.setAlpha(180);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
                lp.setMargins(margin, 0, 0, 0);
                lp.gravity = Gravity.CENTER;
                indicator.setLayoutParams(lp);
                Drawable drawable = res.getDrawable(R.drawable.selector_indicator);
                indicator.setImageDrawable(drawable);
                indicators.addView(indicator);
            }
        }
    }

    @Override
    public CinemaHeaderAdapter.CinemaHeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item, parent, false);
        return new CinemaHeaderAdapter.CinemaHeaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CinemaHeaderAdapter.CinemaHeaderViewHolder holder, int position) {
        if(size != 0){
            int relPosition = position % size;
            String url  = bannerImages.get(relPosition);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                indicators.getChildAt(lastPosition).setActivated(false);
                indicators.getChildAt(relPosition).setActivated(true);
                lastPosition = relPosition;
            }
            Picasso.with(GoloveApplication.goloveApplication).load(url).into(holder.bannerImage);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class CinemaHeaderViewHolder extends RecyclerView.ViewHolder {

        private ImageView bannerImage;
        private LinearLayout indicators;

        public CinemaHeaderViewHolder(View itemView) {
            super(itemView);
            bannerImage = (ImageView) itemView;
        }

    }
}
