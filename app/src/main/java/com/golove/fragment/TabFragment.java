package com.golove.fragment;


import android.support.v4.app.Fragment;
import android.util.Log;

import com.golove.listener.OnRequestCallBackListener;
import com.golove.model.ResultStateModel;
import com.golove.ui.neterror.NetWorkErrorView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class TabFragment<T extends ResultStateModel> extends Fragment implements NetWorkErrorView.OnFreshListener, OnRequestCallBackListener<T> {


    public NetWorkErrorView netWorkErrorView;

    protected boolean isSwitch = false;

    public abstract void onTabChange(int position);

    public abstract void requestData();

    public void onReFresh() {
        requestData();
    }


}
