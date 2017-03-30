package com.golove.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.adapter.DamaiAdapter;
import com.golove.callback.RequestCallBack;
import com.golove.divider.GridSpacingItemDecoration;
import com.golove.model.CartoonBannerModel;
import com.golove.model.CartoonModel;
import com.golove.model.CartoonTabModel;
import com.golove.model.ResultStateModel;
import com.golove.request.BaseRequest;
import com.golove.ui.OnLoadDataListener;
import com.golove.ui.neterror.NetWorkErrorView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DamaiFragment extends MainFragment<ResultStateModel<CartoonModel>> {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshlayout;
    private DamaiAdapter damaiAdapter;
    private BaseRequest baseRequest;
    private List<ImageView> tabs;
    private Picasso picasso;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.damai_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    public void initView(View view) {
        picasso = Picasso.with(GoloveApplication.goloveApplication);

        tabs = new ArrayList<>();
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View headView = layoutInflater.inflate(R.layout.cartoon_headview, null, false);
        ImageView weekRank = (ImageView) headView.findViewById(R.id.weekRank);
        tabs.add(weekRank);
        ImageView newCartoon = (ImageView) headView.findViewById(R.id.newCartoon);
        tabs.add(newCartoon);
        ImageView overCartoon = (ImageView) headView.findViewById(R.id.overCartoon);
        tabs.add(overCartoon);
        ImageView cartoonCategory = (ImageView) headView.findViewById(R.id.cartoonCategory);
        tabs.add(cartoonCategory);


        netWorkErrorView = (NetWorkErrorView) view.findViewById(R.id.netWorkErrorView);
        netWorkErrorView.setOnFreshListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),6);
        recyclerView.setLayoutManager(gridLayoutManager);
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration.Builder(getActivity(),2)
                .hasHeader()
                .setSpanCount(2)
                .setH_spacing(60)
                .build();
        recyclerView.addItemDecoration(itemDecoration);
        damaiAdapter = new DamaiAdapter(getActivity(),headView);
        recyclerView.setAdapter(damaiAdapter);

        swipeRefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshlayout);
        // 设定下拉圆圈的背景
        swipeRefreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshlayout.setColorSchemeResources(android.R.color.holo_red_light);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
//        swipeRefreshlayout.setDistanceToTriggerSync(400);
        swipeRefreshlayout.setProgressViewOffset(false, -100, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });

        baseRequest = new BaseRequest();
    }

    public void requestData() {
        requestData(netWorkErrorView);
    }

    private void requestData(OnLoadDataListener onLoadDataListener) {
        String url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/show.txt";
        RequestCallBack requestCallBack = new RequestCallBack(this, onLoadDataListener);
        baseRequest.sendRequest(url, requestCallBack);
    }

    @Override
    public void onRequestCallBackSuccess(ResultStateModel<CartoonModel> cartoonModelResultStateModel) {
        swipeRefreshlayout.setVisibility(View.VISIBLE);
        netWorkErrorView.setVisibility(View.GONE);
        CartoonModel cartoonModel = cartoonModelResultStateModel.getResult();
        if (swipeRefreshlayout.isRefreshing()) {
            swipeRefreshlayout.setRefreshing(false);
        }

        List<CartoonTabModel> cartoonTabModels = cartoonModel.getTabs();
        int size = cartoonTabModels.size();
        int tabSize = tabs.size();
        for(int i = 0 ; i < size ; i++){
            if(i < tabSize){
                CartoonTabModel cartoonTabModel = cartoonTabModels.get(i);
                picasso.load(cartoonTabModel.getPic())
                        .fit()
                        .placeholder(R.drawable.placeholder_vertical)
                        .into(tabs.get(i));
            }
        }

        damaiAdapter.addData(cartoonModel.getCartoons());
    }

    @Override
    public void onRequestCallBackError() {
        if (swipeRefreshlayout.isRefreshing()) {
            Toast.makeText(getContext(), "下拉刷新失败，请重试", Toast.LENGTH_SHORT).show();
            swipeRefreshlayout.setRefreshing(false);
        }
    }


}
