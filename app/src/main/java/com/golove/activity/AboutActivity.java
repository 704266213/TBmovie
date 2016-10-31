package com.golove.activity;

import android.os.Bundle;

import com.golove.R;
import com.golove.param.BuildBannerParamListener;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
*
* 类描述：
* 创建人：
* 创建时间：16-4-16 下午8:58
* 修改备注：
* @version
*
*/
public class AboutActivity extends InitRequestActivity implements BuildBannerParamListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


    }


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient mOkHttpClient=new OkHttpClient();
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
}
