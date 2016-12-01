package com.golove.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.golove.R;
import com.golove.adapter.CinemaAdapter;
import com.golove.adapter.FilmHitRecyclerAdapter;
import com.golove.callback.RequestCallBack;
import com.golove.divider.FilmDivider;
import com.golove.listener.OnLoadMoreListener;
import com.golove.loadmore.OnLinearLoadMoreListener;
import com.golove.model.BannerModel;
import com.golove.model.FilmHotModel;
import com.golove.model.FilmModel;
import com.golove.model.ResultStateModel;
import com.golove.request.BaseRequest;
import com.golove.ui.neterror.NetWorkErrorView;

import java.util.List;


public class DiscoveryFragment extends MainFragment<ResultStateModel<FilmHotModel>> implements OnLoadMoreListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshlayout;
    private CinemaAdapter cinemaAdapter;
    private OnLinearLoadMoreListener onLinearLoadMoreListener;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discovery_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    public void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        netWorkErrorView = (NetWorkErrorView) view.findViewById(R.id.netWorkErrorView);
        netWorkErrorView.setOnFreshListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new FilmDivider(getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.top_line), (int) getResources().getDimension(R.dimen.film_line_paddingLeft)));

        recyclerView.setHasFixedSize(true);

        cinemaAdapter = new CinemaAdapter();
//        cinemaAdapter.setFooterView(footerView);
        recyclerView.setAdapter(cinemaAdapter);
        onLinearLoadMoreListener = new OnLinearLoadMoreListener(this);
        recyclerView.addOnScrollListener(onLinearLoadMoreListener);


        swipeRefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshlayout);
        // 设定下拉圆圈的背景
        swipeRefreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshlayout.setColorSchemeResources(android.R.color.holo_red_light);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
//        swipeRefreshlayout.setDistanceToTriggerSync(400);
        swipeRefreshlayout.setProgressViewOffset(false, -100, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        swipeRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist1.txt";
                requestData();
            }
        });

    }

    String url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist1.txt";
    public void requestData() {
        RequestCallBack requestCallBack = new RequestCallBack(this, netWorkErrorView);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.sendRequest(url, requestCallBack);
    }

    @Override
    public void onRequestCallBackSuccess(ResultStateModel<FilmHotModel> bean) {
        swipeRefreshlayout.setVisibility(View.VISIBLE);
        netWorkErrorView.setVisibility(View.GONE);
        FilmHotModel filmHotModel = bean.getResult();

        List<FilmModel> filmModels = filmHotModel.getFilmModels();
        if(swipeRefreshlayout.isRefreshing()){
            swipeRefreshlayout.setRefreshing(false);
            cinemaAdapter.addFreshData(filmModels);
        } else {
            cinemaAdapter.addData(filmModels);
            onLinearLoadMoreListener.isLoading(false);
        }
    }

    @Override
    public void onRequestCallBackError() {

    }

    @Override
    public void onLoadMore() {
        Log.e("XLog","=============加载数据================");
        url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist2";
        requestData();
    }
}
