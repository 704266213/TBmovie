package com.golove.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.golove.R;
import com.golove.adapter.FilmHitRecyclerAdapter;
import com.golove.adapter.LoopViewPagerAdapter;
import com.golove.divider.FilmDivider;
import com.golove.model.Bannerbean;
import com.golove.ui.neterror.NetWorkErrorView;
import com.golove.ui.reflesh.PtrClassicFrameLayout;
import com.golove.ui.reflesh.PtrDefaultHandler;
import com.golove.ui.reflesh.PtrFrameLayout;
import com.golove.ui.reflesh.PtrHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HitMoviesFragment extends TabFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public HitMoviesFragment() {

    }

    public static HitMoviesFragment newInstance(String param1, String param2) {
        HitMoviesFragment fragment = new HitMoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }


    private String mItemData = "Lorem Ipsum is simply dummy text of the printing and "
            + "typesetting industry Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.";

    private RecyclerView recyclerView;
    private FilmHitRecyclerAdapter filmHitRecyclerAdapter;
    private AppBarLayout appBarLayout;
    private PtrClassicFrameLayout ptrFrameLayout;
    private int verticalOffsetY;
    private NetWorkErrorView netWorkErrorView;

    private View headView;
    private ViewPager viewPager;
    private ViewGroup indicators;
    private LoopViewPagerAdapter mPagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hit_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        netWorkErrorView = (NetWorkErrorView) view.findViewById(R.id.netWorkErrorView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new FilmDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.top_line),(int)getResources().getDimension(R.dimen.film_line_paddingLeft)));

        recyclerView.setHasFixedSize(true);

        String[] listItems = mItemData.split(" ");

        List<String> list = new ArrayList<>();
        Collections.addAll(list, listItems);

        headView = LayoutInflater.from(view.getContext()).inflate(R.layout.film_hit_headerview, null, false);
        filmHitRecyclerAdapter = new FilmHitRecyclerAdapter(list);
        filmHitRecyclerAdapter.setHeadView(headView);

        viewPager = (ViewPager) headView.findViewById(R.id.viewPager);
        indicators = (ViewGroup) headView.findViewById(R.id.indicators);
        mPagerAdapter = new LoopViewPagerAdapter(viewPager, indicators);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(mPagerAdapter);
        mPagerAdapter.setList(initBanner());

        recyclerView.setAdapter(filmHitRecyclerAdapter);


        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e("TAG", "====Height==========" + appBarLayout.getHeight());
                HitMoviesFragment.this.verticalOffsetY = verticalOffset;
            }
        });
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptrFrameLayout);
        ptrFrameLayout.disableWhenHorizontalMove(true);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return verticalOffsetY == 0 && PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                },3*1000);
            }
        });
    }


    public void onResume() {
        super.onResume();
        if (mPagerAdapter != null) {
            mPagerAdapter.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPagerAdapter != null) {
            mPagerAdapter.stop();
        }
    }


    private List<Bannerbean> initBanner(){
        List<Bannerbean> bannerbeens = new ArrayList<>();
        Bannerbean bannerbean = new Bannerbean();
        bannerbean.setUrl("https://raw.githubusercontent.com/704266213/data/master/WebContent/img/banner1.png");
        bannerbeens.add(bannerbean);

        Bannerbean bannerbean1 = new Bannerbean();
        bannerbean1.setUrl("https://raw.githubusercontent.com/704266213/data/master/WebContent/img/banner2.png");
        bannerbeens.add(bannerbean1);

        Bannerbean bannerbean2 = new Bannerbean();
        bannerbean2.setUrl("https://raw.githubusercontent.com/704266213/data/master/WebContent/img/banner3.png");
        bannerbeens.add(bannerbean2);

        Bannerbean bannerbean3 = new Bannerbean();
        bannerbean3.setUrl("https://raw.githubusercontent.com/704266213/data/master/WebContent/img/banner4.png");
        bannerbeens.add(bannerbean3);


        return bannerbeens;
    }


    @Override
    public void onTabChange(int position) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.setVisibility(View.VISIBLE);
                netWorkErrorView.setVisibility(View.GONE);
            }
        },3*1000);

    }
}
