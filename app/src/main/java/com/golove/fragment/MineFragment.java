package com.golove.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.golove.BuildConfig;
import com.golove.R;
import com.golove.interceptor.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MineFragment extends MainFragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mine_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestData();
    }

    public void initRequest() {
        Log.d("Mine", "MineFragment is Init");
        requestData();
    }



    Interceptor mTokenInterceptor = new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            return chain.proceed(originalRequest);
//            if (Your.sToken == null || alreadyHasAuthorizationHeader(originalRequest)) {
//                return chain.proceed(originalRequest);
//            }
//            Request authorised = originalRequest.newBuilder()
//                    .header("Authorization", Your.sToken)
//                    .build();
//            return chain.proceed(authorised);
        }
    };

    private void requestData() {

//        OkHttpClient client = new OkHttpClient();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG
                ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url("https://raw.githubusercontent.com/704266213/TBmovie/master/banber.json")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
            }

        });
    }

}
