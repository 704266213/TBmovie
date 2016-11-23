package com.golove.callback;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.golove.listener.OnRequestCallBackListener;
import com.golove.model.ResultStateModel;
import com.golove.ui.neterror.OnLoadDataListener;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class RequestCallBack<T extends ResultStateModel<T>> implements Callback {

    private OnRequestCallBackListener onRequestCallBackListener;
    private OnLoadDataListener onLoadDataListener;
    private Type type;
    private Handler handler;
    private Runnable errorRun = new Runnable() {

        public void run() {
            onLoadDataListener.loadErrorView();
            onRequestCallBackListener.onRequestCallBackError();
        }
    };

    public RequestCallBack(OnRequestCallBackListener onRequestCallBackListener, OnLoadDataListener onLoadDataListener) {
        this.onRequestCallBackListener = onRequestCallBackListener;
        this.onLoadDataListener = onLoadDataListener;
        handler = new Handler(Looper.getMainLooper());
        ParameterizedType pt = (ParameterizedType) onRequestCallBackListener.getClass().getGenericSuperclass();
        type = pt.getActualTypeArguments()[0];
        Log.e("XLog", "=======type===============" + type);
    }


    @Override
    public void onFailure(Call call, IOException e) {
        handler.post(errorRun);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();
        if (response.isSuccessful()) {
            T resultStateModel = JSON.parseObject(json, type);
            SuccessRun successRun = new SuccessRun(resultStateModel);
            handler.post(successRun);
        } else {
            handler.post(errorRun);
        }
    }

    private class SuccessRun implements Runnable {
        private T resultStateModel;

        public SuccessRun(T resultStateModel) {
            this.resultStateModel = resultStateModel;
        }

        public void run() {
            onRequestCallBackListener.onRequestCallBackSuccess(resultStateModel);
        }
    }
}
