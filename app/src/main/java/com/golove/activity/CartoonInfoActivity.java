package com.golove.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.adapter.CartoonInfoAdapter;
import com.golove.callback.RequestCallBack;
import com.golove.fragment.CartoonChapterFragment;
import com.golove.fragment.CartoonCommentFragment;
import com.golove.fragment.TabFragment;
import com.golove.listener.OnRequestCallBackListener;
import com.golove.model.CartoonDetailModel;
import com.golove.model.ComicModel;
import com.golove.model.ResultStateModel;
import com.golove.request.DeaultSendRequest;
import com.golove.request.DefaultBuildRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartoonInfoActivity extends BaseActivity<ResultStateModel<ComicModel>> implements ViewPager.OnPageChangeListener, AppBarLayout.OnOffsetChangedListener {

    private Toolbar toolbar;
    private AppBarLayout appbarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView perfectBackground;
    private TextView title;
    private TextView follow;
    private TextView category;
    private TextView category2;
    private TextView category3;
    private TextView bigTitle;
    private TextView titleCenter;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private List<TabFragment> tabFragments;
    private Picasso picasso;
    private Drawable constraintLayoutDrawable;
    private List<TextView> categoryList;
    private int length;

    private CartoonInfoAdapter cartoonInfoAdapter;
    private OnRequestCallBackListener onRequestCallBackListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon_info);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        CartoonDetailModel cartoonDetailModel = (CartoonDetailModel) intent.getSerializableExtra("cartoonDetailModel");

        picasso = Picasso.with(GoloveApplication.goloveApplication);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appbarLayout = (AppBarLayout) findViewById(R.id.appbarLayout);
        appbarLayout.addOnOffsetChangedListener(this);
        perfectBackground = (ImageView) findViewById(R.id.perfectBackground);
//        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.constraintLayout);
//        constraintLayoutDrawable = constraintLayout.getBackground();
//        constraintLayoutDrawable.setAlpha(0);
        title = (TextView) findViewById(R.id.title);
        title.setText(cartoonDetailModel.getTitle());
        follow = (TextView) findViewById(R.id.follow);
        categoryList = new ArrayList<>();
        category = (TextView) findViewById(R.id.category);
        categoryList.add(category);
        category2 = (TextView) findViewById(R.id.category2);
        categoryList.add(category2);
        category3 = (TextView) findViewById(R.id.category3);
        categoryList.add(category3);
        String[] categorys = cartoonDetailModel.getCategory();
        length = categorys.length;
        int size = categoryList.size();
        for (int i = 0; i < size; i++) {
            if (i < length) {
                categoryList.get(i).setText(categorys[i]);
            } else {
                categoryList.get(i).setVisibility(View.GONE);
            }
        }
        bigTitle = (TextView) findViewById(R.id.bigTitle);
        bigTitle.setText(cartoonDetailModel.getTitle());
        titleCenter = (TextView) findViewById(R.id.titleCenter);
        titleCenter.setText(cartoonDetailModel.getTitle());

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        tabFragments = new ArrayList<>();
        cartoonInfoAdapter = new CartoonInfoAdapter(getSupportFragmentManager());
        TabFragment cartoonCommentFragment = new CartoonCommentFragment();
        tabFragments.add(cartoonCommentFragment);
        cartoonInfoAdapter.addFrag(cartoonCommentFragment, "详情");

        CartoonChapterFragment cartoonChapterFragment = new CartoonChapterFragment();
        onRequestCallBackListener = cartoonChapterFragment;
        cartoonInfoAdapter.addFrag(cartoonChapterFragment, "选集");
        tabFragments.add(cartoonChapterFragment);

        viewpager.setAdapter(cartoonInfoAdapter);
        viewpager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewpager);

        picasso.load(cartoonDetailModel.getPic())
                .fit()
                .into(perfectBackground);

        requestData();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        int top = bigTitle.getTop();
        int height = toolbar.getHeight();
//        final int newAlpha = (int) (percentage * 255);
//        constraintLayoutDrawable.setAlpha(newAlpha);

        Log.e("XLog", "=========top==================" + top);
        Log.e("XLog", "=========verticalOffset==================" + verticalOffset);
        if (Math.abs(verticalOffset) >= top - height) {
            title.setVisibility(View.VISIBLE);
            titleCenter.setVisibility(View.GONE);
            bigTitle.setVisibility(View.GONE);
            for (int i = 0; i < 3; i++) {
                TextView tx = categoryList.get(i);
                if (i < length) {
                    tx.setVisibility(View.GONE);
                } else {
                    tx.setVisibility(View.GONE);
                }
            }
        } else {
            title.setVisibility(View.GONE);
            titleCenter.setVisibility(View.GONE);
            bigTitle.setVisibility(View.VISIBLE);
            for (int i = 0; i < 3; i++) {
                TextView tx = categoryList.get(i);
                if (i < length) {
                    tx.setVisibility(View.VISIBLE);
                } else {
                    tx.setVisibility(View.GONE);
                }
            }
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void requestData() {
        DefaultBuildRequest defaultBuildRequest = new DefaultBuildRequest("comics.txt", "GET", null);
        DeaultSendRequest deaultSendRequest = new DeaultSendRequest(defaultBuildRequest);
        RequestCallBack requestCallBack = new RequestCallBack(this);
        deaultSendRequest.sendRequest(requestCallBack);
    }

    @Override
    public void onRequestCallBackSuccess(ResultStateModel<ComicModel> comicModelResultStateModel) {
        onRequestCallBackListener.onRequestCallBackSuccess(comicModelResultStateModel.getResult());
    }

    @Override
    public void onRequestCallBackError() {
        onRequestCallBackListener.onRequestCallBackError();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tabFragments != null) {
            tabFragments.clear();
            tabFragments = null;
        }
        if (categoryList != null) {
            categoryList.clear();
            categoryList = null;
        }
        if (cartoonInfoAdapter != null){
            cartoonInfoAdapter.onDestory();
            cartoonInfoAdapter = null;
        }
    }
}
