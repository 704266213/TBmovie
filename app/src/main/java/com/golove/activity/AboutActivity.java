package com.golove.activity;

import android.os.Bundle;
import android.util.Log;

import com.golove.R;
import com.golove.model.ResultStateModel;
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
public class AboutActivity extends BaseActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

}
