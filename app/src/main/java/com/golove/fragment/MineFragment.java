package com.golove.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.golove.R;
import com.golove.callback.RequestCallBack;
import com.golove.model.ResultStateModel;
import com.golove.model.UserInfoModel;
import com.golove.request.BaseRequest;
import com.golove.ui.neterror.NetWorkErrorView;

public class MineFragment extends MainFragment<ResultStateModel<UserInfoModel>> {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mine_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        netWorkErrorView = (NetWorkErrorView) view.findViewById(R.id.netWorkErrorView);
        netWorkErrorView.setOnFreshListener(this);
    }

    private String url = "https://raw.githubusercontent.com/704266213/TBmovie/master/banber1.json";

    public void requestData() {
        RequestCallBack requestCallBack = new RequestCallBack(this, netWorkErrorView);
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.sendRequest(url, requestCallBack);
        url = "https://raw.githubusercontent.com/704266213/TBmovie/master/userInfo.json";
    }

    @Override
    public void onRequestCallBackSuccess(ResultStateModel<UserInfoModel> bean) {
        ResultStateModel<UserInfoModel> resultStateModel = bean;
        Log.e("XLog", "=======state===============" + resultStateModel.state);
        Log.e("XLog", "=======message===============" + resultStateModel.message);
        UserInfoModel userInfoModel = resultStateModel.getResult();
        Log.e("XLog", "=======Name===============" + userInfoModel.getName());
        Log.e("XLog", "=======Sex===============" + userInfoModel.getSex());
        Log.e("XLog", "=======HeadURL===============" + userInfoModel.getHeadURL());
        Log.e("XLog", "=======end===============");
        netWorkErrorView.setVisibility(View.GONE);
    }

    public void onRequestCallBackError() {

    }


}
