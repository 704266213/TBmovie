package com.golove.request;

import com.golove.listener.InitRequestListener;
import com.golove.param.BuildParamListener;

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
 * 创建人：alan
 * 创建时间：2016-04-04 23:17
 * 修改备注：
 */
public class BaseRequest {


    public void get(String url) {
        //创建okHttpClient对象
        OkHttpClient client = new OkHttpClient();
        //创建一个Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String result = response.body().string();

           client.newCall(request).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {

                }

                public void onResponse(Call call, Response response) throws IOException {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    public void post(String url, final BuildParamListener buildParamListener, final InitRequestListener initRequestListener){
        OkHttpClient client = new OkHttpClient();
        //创建一个Request
        RequestBody body = RequestBody.create(JSON, "");
        Request request = new Request.Builder()
                .url(url)
                .post(buildParamListener.bulidParam())
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, IOException e) {
                initRequestListener.onInitRequestError();
            }

            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
//                initRequestListener.onInitRequestSuccess("");

            }
        });


    }

}
