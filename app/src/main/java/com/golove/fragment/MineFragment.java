package com.golove.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.golove.R;
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

        requestData();
    }

    public void initRequest() {
        Log.d("Mine", "MineFragment is Init");
        requestData();
    }

    private String url = "https://raw.githubusercontent.com/704266213/TBmovie/master/banber1.json";
    private void requestData() {
        BaseRequest baseRequest = new BaseRequest(getActivity());
        baseRequest.sendRequest(url,this);
    }

    public void onReFresh() {
        url = "https://raw.githubusercontent.com/704266213/TBmovie/master/userInfo.json";
        netWorkErrorView.loadingView();
        requestData();
    }


}
