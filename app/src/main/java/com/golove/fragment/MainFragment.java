package com.golove.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.golove.listener.OnRequestCallBackListener;
import com.golove.model.ResultStateModel;
import com.golove.ui.neterror.NetWorkErrorView;

public abstract class MainFragment<T extends ResultStateModel> extends Fragment implements NetWorkErrorView.OnFreshListener, OnRequestCallBackListener<T> {

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
                break;
            case 3:
                baseFragment = new DiscoveryFragment();
                break;
            case 4:
                baseFragment = new MineFragment();
                break;
        }
        return baseFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onReFresh() {
        netWorkErrorView.loadingView();
    }

//    public abstract void requestData();

    //    public abstract void  onRequestCallBackSuccess(T bean);


    public void onRequestCallBackSuccess(T bean) {

    }

    public void onRequestCallBackError() {

    }

}
