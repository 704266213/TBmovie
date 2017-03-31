package com.golove.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.R;
import com.golove.adapter.CartoonInfoAdapter;
import com.golove.fragment.CartoonChapterFragment;
import com.golove.fragment.CartoonCommentFragment;
import com.golove.fragment.TabFragment;

import java.util.ArrayList;
import java.util.List;

public class CartoonInfoActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,AppBarLayout.OnOffsetChangedListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartoon_info);
        initView();
    }

    private void initView() {
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appbarLayout = (AppBarLayout) findViewById(R.id.appbarLayout);
        appbarLayout.addOnOffsetChangedListener(this);
        perfectBackground = (ImageView) findViewById(R.id.perfectBackground);
        title = (TextView) findViewById(R.id.title);
        follow = (TextView) findViewById(R.id.follow);
        category = (TextView) findViewById(R.id.category);
        category2 = (TextView) findViewById(R.id.category2);
        category3 = (TextView) findViewById(R.id.category3);
        bigTitle = (TextView) findViewById(R.id.bigTitle);
        titleCenter = (TextView) findViewById(R.id.titleCenter);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewpager = (ViewPager) findViewById(R.id.viewpager);


        tabFragments = new ArrayList<>();
        CartoonInfoAdapter cartoonInfoAdapter = new CartoonInfoAdapter(getSupportFragmentManager());
        TabFragment cartoonCommentFragment = new CartoonCommentFragment();
        tabFragments.add(cartoonCommentFragment);
        cartoonInfoAdapter.addFrag(cartoonCommentFragment, "详情");

        CartoonChapterFragment cartoonChapterFragment = new CartoonChapterFragment();
        cartoonInfoAdapter.addFrag(cartoonChapterFragment, "选集");
        tabFragments.add(cartoonChapterFragment);

        viewpager.setAdapter(cartoonInfoAdapter);
        viewpager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;

        int appBarLayoutHeight = appBarLayout.getHeight();
//        bigTitle.setTranslationY();
        int top = bigTitle.getTop();


        if(Math.abs(verticalOffset) >= top){
            titleCenter.setVisibility(View.VISIBLE);
            bigTitle.setVisibility(View.GONE);
            category.setVisibility(View.INVISIBLE);
            category2.setVisibility(View.GONE);
            category3.setVisibility(View.GONE);
            collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);
            collapsingToolbarLayout.setTitle("百合零距离");

            translationView(verticalOffset + top + bigTitle.getBottom());

        } else {
            titleCenter.setVisibility(View.GONE);
            bigTitle.setVisibility(View.VISIBLE);
            category.setVisibility(View.VISIBLE);
            category2.setVisibility(View.VISIBLE);
            category3.setVisibility(View.VISIBLE);
        }

    }

    private void translationView(float offset) {
        titleCenter.setTranslationY(offset);
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


}
