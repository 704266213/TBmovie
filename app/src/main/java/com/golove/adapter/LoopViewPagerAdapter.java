package com.golove.adapter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.golove.R;
import com.golove.model.Bannerbean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/9/7.
 */
public class LoopViewPagerAdapter extends BaseLoopPagerAdapter {

    private List<Bannerbean> bannerBeanList;

    private final ViewGroup mIndicators;

    private int mLastPosition;

    public LoopViewPagerAdapter(ViewPager viewPager, ViewGroup indicators) {
        super(viewPager);
        mIndicators = indicators;
        bannerBeanList = new ArrayList<>();
    }

    public void setList(List<Bannerbean> dataList) {
        bannerBeanList.clear();
        bannerBeanList.addAll(dataList);
        notifyDataSetChanged();
    }

    /**
     * oh shit! An indicator view is badly needed!
     * this shit have no animation at all.
     */
    private void initIndicators() {
        if (mIndicators.getChildCount() != bannerBeanList.size() && bannerBeanList.size() > 1) {
            mIndicators.removeAllViews();
            Resources res = mIndicators.getResources();
            int size = res.getDimensionPixelOffset(R.dimen.indicator_size);
            int margin = res.getDimensionPixelOffset(R.dimen.indicator_margin);
            for (int i = 0; i < getPagerCount(); i++) {
                ImageView indicator = new ImageView(mIndicators.getContext());
                indicator.setAlpha(180);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
                lp.setMargins(margin, 0, 0, 0);
                lp.gravity = Gravity.CENTER;
                indicator.setLayoutParams(lp);
                Drawable drawable = res.getDrawable(R.drawable.selector_indicator);
                indicator.setImageDrawable(drawable);
                mIndicators.addView(indicator);
            }
        }
    }

    @Override
    public void notifyDataSetChanged() {
        initIndicators();
        super.notifyDataSetChanged();
    }

    @Override
    public int getPagerCount() {
        return bannerBeanList.size();
    }

    @Override
    public Bannerbean getItem(int position) {
        return bannerBeanList.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_item, parent, false);
            holder = new ViewHolder();
            holder.ivBanner = (ImageView) convertView.findViewById(R.id.ivBanner);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bannerbean bannerbean = bannerBeanList.get(position);
        Log.e("TAG","======url========" + bannerbean.getUrl());
        Picasso.with(parent.getContext()).load(bannerbean.getUrl()).into(holder.ivBanner);
        return convertView;
    }

    @Override
    public void onPageItemSelected(int position) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mIndicators.getChildAt(mLastPosition).setActivated(false);
            mIndicators.getChildAt(position).setActivated(true);
        }
        mLastPosition = position;
    }

    public static class ViewHolder {
        ImageView ivBanner;
    }
}
