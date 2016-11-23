package com.golove.activity;

import android.os.Bundle;
import android.util.Log;

import com.golove.R;
import com.golove.param.BuildBannerParamListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 类描述：
 * 创建人：
 * 创建时间：16-4-16 下午8:58
 * 修改备注：
 */
public class AboutActivity extends InitRequestActivity implements BuildBannerParamListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        requestData();
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient mOkHttpClient = new OkHttpClient();
//    RequestBody formBody = new FormBody.Builder()
//            .add("size", "10")
//            .build();
//    Request request = new Request.Builder()
//            .url("http://api.1-blog.com/biz/bizserver/article/list.do")
//            .post(formBody)
//            .build();
//    Call call = mOkHttpClient.newCall(request);
//   public RequestBody bulidParam(){
//       RequestBody body = RequestBody.create(JSON, "123");
//       return body;
//   }


    public RequestBody buildBannerParamListener() {
        return null;
    }

    private void requestData() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://raw.githubusercontent.com/704266213/TBmovie/master/banber.json")
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               String json =  response.body().string();
                Log.e("XLog", "==================" + json)  ;
            }


        });

    }

    @Override
    public void onRequestCallBackSuccess(Object bean) {

    }

    @Override
    public void onRequestCallBackError() {

    }
}
