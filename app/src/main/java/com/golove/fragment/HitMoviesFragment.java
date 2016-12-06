package com.golove.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.adapter.FilmHitRecyclerAdapter;
import com.golove.adapter.LoopViewPagerAdapter;
import com.golove.callback.RequestCallBack;
import com.golove.divider.FilmDivider;
import com.golove.listener.OnLoadMoreListener;
import com.golove.loadmore.OnLinearLoadMoreListener;
import com.golove.model.BannerModel;
import com.golove.model.FilmHotModel;
import com.golove.model.FilmModel;
import com.golove.model.ResultStateModel;
import com.golove.request.BaseRequest;
import com.golove.ui.OnLoadDataListener;
import com.golove.ui.footer.FooterView;
import com.golove.ui.neterror.NetWorkErrorView;
import com.golove.ui.reflesh.PtrClassicFrameLayout;
import com.golove.ui.reflesh.PtrDefaultHandler;
import com.golove.ui.reflesh.PtrFrameLayout;
import com.golove.ui.reflesh.PtrHandler;

import java.util.List;

public class HitMoviesFragment extends TabFragment<ResultStateModel<FilmHotModel>> implements FilmHitRecyclerAdapter.OnBuyTicketListener, OnLoadMoreListener {


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

    private OnLinearLoadMoreListener onLinearLoadMoreListener;
    private BaseRequest baseRequest;
    private int pageNo = 1;

    private View headView;
    private FooterView footerView;
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

         /*
         * 加载更多时，失败的回调设置
         */
        footerView = new FooterView(view.getContext());
        footerView.setOnLoadMoreListener(this);

        filmHitRecyclerAdapter = new FilmHitRecyclerAdapter();
        filmHitRecyclerAdapter.setHeadView(headView);
        filmHitRecyclerAdapter.setFooterView(footerView);
        filmHitRecyclerAdapter.setOnBuyTicketListener(this);

        viewPager = (ViewPager) headView.findViewById(R.id.viewPager);
        indicators = (ViewGroup) headView.findViewById(R.id.indicators);
        mPagerAdapter = new LoopViewPagerAdapter(viewPager, indicators);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(mPagerAdapter);

        recyclerView.setAdapter(filmHitRecyclerAdapter);

        /*
         * 滚动到底部自动加载更多
         */
        onLinearLoadMoreListener = new OnLinearLoadMoreListener(footerView, this);
        recyclerView.addOnScrollListener(onLinearLoadMoreListener);

        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                HitMoviesFragment.this.verticalOffsetY = verticalOffset;
            }
        });
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptrFrameLayout);
        ptrFrameLayout.disableWhenHorizontalMove(false);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return verticalOffsetY == 0 && PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                pageNo = 1;
                requestData(footerView);
            }
        });

        baseRequest = new BaseRequest();
        requestData();
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

    }


    public void requestData() {
        requestData(netWorkErrorView);
    }

    private void requestData(OnLoadDataListener onLoadDataListener) {
        String url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist" + pageNo + ".txt";
        RequestCallBack requestCallBack = new RequestCallBack(this, onLoadDataListener);
        baseRequest.sendRequest(url, requestCallBack);
    }


    @Override
    public void onRequestCallBackSuccess(ResultStateModel<FilmHotModel> bean) {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        netWorkErrorView.setVisibility(View.GONE);
        FilmHotModel filmHotModel = bean.getResult();

        List<BannerModel> bannerModels = filmHotModel.getBannerModels();
        if (bannerModels != null) {
            mPagerAdapter.addData(bannerModels);
        }

        List<FilmModel> filmModels = filmHotModel.getFilmModels();
        if (ptrFrameLayout.isRefreshing()) {
            ptrFrameLayout.refreshComplete();
            filmHitRecyclerAdapter.addFreshData(filmModels);
            onLinearLoadMoreListener.setHasMore(true);
        } else {
            if (filmModels.size() < 15) {
                footerView.loadNoDataOrNoMoreDataView();
                onLinearLoadMoreListener.setHasMore(false);
            }
            filmHitRecyclerAdapter.addData(filmModels);
            onLinearLoadMoreListener.isLoadingMore(false);
        }
        pageNo += 1;
    }

    @Override
    public void onRequestCallBackError() {
        if (ptrFrameLayout.isRefreshing()) {
            ptrFrameLayout.refreshComplete();
            Toast.makeText(getContext(), "下拉刷新失败，请重试", Toast.LENGTH_SHORT).show();
        }
        onLinearLoadMoreListener.isLoadingMore(false);
    }

    @Override
    public void onLoadMore() {
        requestData(footerView);
    }


    @Override
    public void buyTickey(FilmModel filmModel) {
        Toast.makeText(GoloveApplication.goloveApplication, "购买电影名称：" + filmModel.getFilmName(), Toast.LENGTH_SHORT).show();
    }


}
