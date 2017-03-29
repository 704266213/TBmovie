package com.golove.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;

import com.golove.R;
import com.golove.listener.OnRequestCallBackListener;
import com.golove.model.ResultStateModel;
import com.golove.ui.neterror.NetWorkErrorView;

public abstract class MainFragment<T extends ResultStateModel> extends Fragment implements NetWorkErrorView.OnFreshListener, OnRequestCallBackListener<T> {

    //是否已经请求数据
    public boolean isRequest = false;
    protected SwipeRefreshLayout swipeRefreshlayout;


    protected NetWorkErrorView netWorkErrorView;

    public static MainFragment getFragmentInstance(int position) {
        MainFragment baseFragment = null;
        switch (position) {
            case 0:
                baseFragment = new FilmFragment();
                break;
            case 1:
                baseFragment = new CinemaFragment();
                break;
            case 2:
                baseFragment = new DamaiFragment();
//                baseFragment = new ShowFragment();
                break;
            case 3:
                baseFragment = new DiscoveryFragment();
                break;
            case 4:
                baseFragment = new MineFragment();
//                baseFragment = new ShowFragment();
                break;
        }
        return baseFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onReFresh() {
        requestData();
    }

    public void requestData() {

    }

    public void onRequestCallBackSuccess(T bean) {

    }

    public void onRequestCallBackError() {

    }


    public void initView(View view) {
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
    }

}
