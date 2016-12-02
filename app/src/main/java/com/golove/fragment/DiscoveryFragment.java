package com.golove.fragment;


import android.os.Bundle;
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
import com.golove.callback.RequestCallBack;
import com.golove.divider.FilmDivider;
import com.golove.listener.OnLoadMoreListener;
import com.golove.loadmore.OnLinearLoadMoreListener;
import com.golove.model.FilmHotModel;
import com.golove.model.FilmModel;
import com.golove.model.ResultStateModel;
import com.golove.request.BaseRequest;
import com.golove.ui.OnLoadDataListener;
import com.golove.ui.footer.FooterView;
import com.golove.ui.neterror.NetWorkErrorView;

import java.util.List;


public class DiscoveryFragment extends MainFragment<ResultStateModel<FilmHotModel>> implements OnLoadMoreListener {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshlayout;
    private CinemaAdapter cinemaAdapter;
    private OnLinearLoadMoreListener onLinearLoadMoreListener;
    private BaseRequest baseRequest;


    private FooterView footerView;
    private int pageNo = 1;

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

        /*
         * 加载更多时，失败的回调设置
         */
        footerView = new FooterView(view.getContext());
        footerView.setOnLoadMoreListener(this);

        cinemaAdapter = new CinemaAdapter();
//        cinemaAdapter.setHeadView(headView);
        cinemaAdapter.setFooterView(footerView);
        recyclerView.setAdapter(cinemaAdapter);

        /*
         * 滚动到底部自动加载更多
         */
        onLinearLoadMoreListener = new OnLinearLoadMoreListener(footerView, this);
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
                pageNo = 1;
                requestData(footerView);
            }
        });

        baseRequest = new BaseRequest();
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
        swipeRefreshlayout.setVisibility(View.VISIBLE);
        netWorkErrorView.setVisibility(View.GONE);
        FilmHotModel filmHotModel = bean.getResult();

        List<FilmModel> filmModels = filmHotModel.getFilmModels();
        if (swipeRefreshlayout.isRefreshing()) {
            swipeRefreshlayout.setRefreshing(false);
            cinemaAdapter.addFreshData(filmModels);
        } else {
            if (filmModels.size() < 15) {
                footerView.loadNoDataOrNoMoreDataView();
            }
            cinemaAdapter.addData(filmModels);
            onLinearLoadMoreListener.isLoadingMore(false);
        }
        pageNo += 1;
    }

    @Override
    public void onRequestCallBackError() {
        if (swipeRefreshlayout.isRefreshing()) {
            Toast.makeText(getContext(), "下拉刷新失败，重试", Toast.LENGTH_SHORT).show();
            swipeRefreshlayout.setRefreshing(false);
        }
        onLinearLoadMoreListener.isLoadingMore(false);
    }

    @Override
    public void onLoadMore(boolean isLoadingMore) {
        Log.e("XLog", "=============加载数据================");
        requestData(footerView);
        onLinearLoadMoreListener.isLoadingMore(isLoadingMore);
    }

}
