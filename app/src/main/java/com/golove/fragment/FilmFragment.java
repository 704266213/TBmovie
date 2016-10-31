package com.golove.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.golove.R;
import com.golove.adapter.FilmAdapter;

import java.util.ArrayList;
import java.util.List;

public class FilmFragment extends MainFragment implements ViewPager.OnPageChangeListener {

    private Context context;
    private AppBarLayout appBarLayout;
    private View mainLine;
    private TabLayout tabLayout;
    private ViewPager viewpager;

    private List<TabFragment> tabFragments;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.film_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appBarLayout = (AppBarLayout) view.findViewById(R.id.appBarLayout);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewpager = (ViewPager) view.findViewById(R.id.viewpager);

        setupViewPager(viewpager);
    }

    private void setupViewPager(ViewPager viewPager) {
        tabFragments = new ArrayList<>();
        FilmAdapter adapter = new FilmAdapter(getFragmentManager());
        TabFragment hitMoviesFragment = new HitMoviesFragment();
        tabFragments.add(hitMoviesFragment);
        hitMoviesFragment.isSwitch = true;
        hitMoviesFragment.onTabChange(0);

        adapter.addFrag(hitMoviesFragment, "正在热映");
        FilmComingFragment filmComingFragment = new FilmComingFragment();
        adapter.addFrag(filmComingFragment, "即将上映");
        tabFragments.add(filmComingFragment);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewpager);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        context = context;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabFragment tabFragment = tabFragments.get(position);
        if(!tabFragment.isSwitch){
            tabFragment.onTabChange(position);
            tabFragment.isSwitch = true;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
