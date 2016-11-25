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
import com.golove.callback.RequestCallBack;
import com.golove.divider.FilmDivider;
import com.golove.model.BannerModel;
import com.golove.model.FilmHotModel;
import com.golove.model.ResultStateModel;
import com.golove.request.BaseRequest;
import com.golove.ui.neterror.NetWorkErrorView;
import com.golove.ui.reflesh.PtrClassicFrameLayout;
import com.golove.ui.reflesh.PtrDefaultHandler;
import com.golove.ui.reflesh.PtrFrameLayout;
import com.golove.ui.reflesh.PtrHandler;

public class HitMoviesFragment extends TabFragment<ResultStateModel<FilmHotModel>> {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }


    private RecyclerView recyclerView;
    private FilmHitRecyclerAdapter filmHitRecyclerAdapter;
    private AppBarLayout appBarLayout;
    private PtrClassicFrameLayout ptrFrameLayout;
    private int verticalOffsetY;

    private View headView;
    private View footerView;
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
        netWorkErrorView.setOnFreshListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new FilmDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.top_line), (int) getResources().getDimension(R.dimen.film_line_paddingLeft)));

        recyclerView.setHasFixedSize(true);



        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
        headView = layoutInflater.inflate(R.layout.film_hit_headerview, null, false);
        footerView = layoutInflater.inflate(R.layout.footer_loading, null, false);
        filmHitRecyclerAdapter = new FilmHitRecyclerAdapter();
        filmHitRecyclerAdapter.setHeadView(headView);
        filmHitRecyclerAdapter.setFooterView(footerView);

        viewPager = (ViewPager) headView.findViewById(R.id.viewPager);
        indicators = (ViewGroup) headView.findViewById(R.id.indicators);
        mPagerAdapter = new LoopViewPagerAdapter(viewPager, indicators);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(mPagerAdapter);

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
                }, 3 * 1000);
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

    @Override
    public void onTabChange(int position) {
            requestData();
    }


    public void requestData() {
        String url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist1.txt";
        RequestCallBack requestCallBack = new RequestCallBack(this, netWorkErrorView);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.sendRequest(url, requestCallBack);
    }


    @Override
    public void onRequestCallBackSuccess(ResultStateModel<FilmHotModel> bean) {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        netWorkErrorView.setVisibility(View.GONE);

        FilmHotModel filmHotModel = bean.getResult();
        Log.e("XLog","=======" + filmHotModel.getBannerModels());
        for (BannerModel bannerModel : filmHotModel.getBannerModels()) {
                    Log.e("XLog","========WebUrl==========" + bannerModel.getWebUrl());
        }
        mPagerAdapter.addData(filmHotModel.getBannerModels());

        filmHitRecyclerAdapter.addData(filmHotModel.getFilmModels());
    }

    @Override
    public void onRequestCallBackError() {

    }



}
