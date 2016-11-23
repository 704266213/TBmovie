package com.golove.callback;

import com.golove.listener.OnRequestCallBackListener;
import com.golove.ui.neterror.OnLoadDataListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class RequestCallBack implements Callback {

    private OnRequestCallBackListener onRequestCallBackListener;
    private OnLoadDataListener onLoadDataListener;
    public RequestCallBack(OnRequestCallBackListener onRequestCallBackListener,OnLoadDataListener onLoadDataListener){
        this.onRequestCallBackListener = onRequestCallBackListener;
        this.onLoadDataListener = onLoadDataListener;
    }


    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }
}
