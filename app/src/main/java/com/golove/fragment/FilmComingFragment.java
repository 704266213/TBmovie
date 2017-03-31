package com.golove.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.adapter.FilmComingAdapter;
import com.golove.adapter.FilmHitRecyclerAdapter;
import com.golove.adapter.FilmReviewAdapter;
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
import com.golove.ui.reflesh.PtrClassicFrameLayout;
import com.golove.ui.reflesh.PtrDefaultHandler;
import com.golove.ui.reflesh.PtrFrameLayout;
import com.golove.ui.reflesh.PtrHandler;
import com.golove.ui.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.golove.ui.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import java.util.List;


/**
 * 即将上映的电影
 */
public class FilmComingFragment extends TabFragment<ResultStateModel<FilmHotModel>> implements FilmHitRecyclerAdapter.OnBuyTicketListener, OnLoadMoreListener {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private RecyclerView recyclerView;
    private FilmComingAdapter filmComingAdapter;
    private AppBarLayout appBarLayout;
    private PtrClassicFrameLayout ptrFrameLayout;
    private int verticalOffsetY;
    private NetWorkErrorView netWorkErrorView;

    private View headView;
    private RadioGroup radioGroup;
    private RecyclerView filmReviewList;
    private FilmReviewAdapter filmReviewAdapter;
    private FooterView footerView;

    private OnLinearLoadMoreListener onLinearLoadMoreListener;
    private BaseRequest baseRequest;
    private int pageNo = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_film_coming, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        headView = LayoutInflater.from(view.getContext()).inflate(R.layout.film_guide, null, false);
        radioGroup = (RadioGroup) headView.findViewById(R.id.radioGroup);
        filmReviewList = (RecyclerView) headView.findViewById(R.id.filmReviewList);
        filmReviewList.requestDisallowInterceptTouchEvent(false);

        final LinearLayoutManager filmReviewLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        filmReviewList.setLayoutManager(filmReviewLayoutManager);
        filmReviewList.setHasFixedSize(true);

        filmReviewAdapter = new FilmReviewAdapter();
        filmReviewList.setAdapter(filmReviewAdapter);

        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                FilmComingFragment.this.verticalOffsetY = verticalOffset;
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


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        netWorkErrorView = (NetWorkErrorView) view.findViewById(R.id.netWorkErrorView);
        netWorkErrorView.setOnFreshListener(this);

        /*
         * 加载更多时，失败的回调设置
         */
        footerView = new FooterView(view.getContext());
        footerView.setOnLoadMoreListener(this);

        filmComingAdapter = new FilmComingAdapter();
        filmComingAdapter.setHeadView(headView);
        filmComingAdapter.setFooterView(footerView);

        // filmComingAdapter.setOnBuyTicketListener(this);
        recyclerView.setAdapter(filmComingAdapter);


        // Set layout manager
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
         /*
         * 滚动到底部自动加载更多
         */
        onLinearLoadMoreListener = new OnLinearLoadMoreListener(footerView, this);
        recyclerView.addOnScrollListener(onLinearLoadMoreListener);


        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(filmComingAdapter);
        recyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        recyclerView.addItemDecoration(new FilmDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.top_line), (int) getResources().getDimension(R.dimen.film_line_paddingLeft)));

        baseRequest = new BaseRequest();
    }


    @Override
    public void onTabChange(int position) {
        requestData(netWorkErrorView);
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

        List<FilmModel> filmModels = filmHotModel.getFilmModels();
        if (ptrFrameLayout.isRefreshing()) {
            ptrFrameLayout.refreshComplete();
            filmComingAdapter.addFreshData(filmModels);
            onLinearLoadMoreListener.setHasMore(true);
        } else {
            if (filmModels.size() < 15) {
                footerView.loadNoDataOrNoMoreDataView();
                onLinearLoadMoreListener.setHasMore(false);
            }
            filmComingAdapter.addData(filmModels);
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
