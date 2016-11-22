package com.golove.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.golove.model.ResultStateModel;
import com.golove.model.UserInfoModel;
import com.golove.ui.neterror.NetWorkErrorView;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public abstract class MainFragment<T extends ResultStateModel> extends Fragment implements Callback,NetWorkErrorView.OnFreshListener {

    protected NetWorkErrorView netWorkErrorView;


    public static MainFragment getFragmentInstance(int position){
        MainFragment baseFragment = null;
        switch (position)
        {
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

    @Override
    public void onFailure(Call call, IOException e) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                netWorkErrorView.loadErrorView();
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        String json = response.body().string();
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        Type type = pt.getActualTypeArguments()[0];
        Log.e("XLog","=======type===============" + type);
        if(response.isSuccessful()){
            T resultStateModel = JSON.parseObject(json,type);
            Log.e("XLog","=======state===============" + resultStateModel.state);
            Log.e("XLog","=======message===============" + resultStateModel.message);
            UserInfoModel userInfoModel = (UserInfoModel)resultStateModel.getResult();
            Log.e("XLog","=======Name===============" + userInfoModel.getName());
            Log.e("XLog","=======Sex===============" + userInfoModel.getSex());
            Log.e("XLog","=======HeadURL===============" + userInfoModel.getHeadURL());
            Log.e("XLog","=======end===============" );
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    netWorkErrorView.loadErrorView();
                }
            });

        }

    }

    public void onReFresh() {

    }


}
