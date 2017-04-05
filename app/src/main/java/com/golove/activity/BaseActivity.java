package com.golove.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;

import com.golove.R;
import com.golove.listener.OnRequestCallBackListener;
import com.golove.model.ResultStateModel;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class BaseActivity<T extends ResultStateModel> extends AppCompatActivity implements OnRequestCallBackListener<T> {

    protected SwipeRefreshLayout swipeRefreshlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RefWatcher refWatcher = LeakCanary.install(getApplication());
        refWatcher.watch(this);
    }

    public void onBack(View v) {
        finish();
    }

    /*
     * 初始化下拉刷新
     */
    protected void initRefreshView(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        swipeRefreshlayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshlayout);
        // 设定下拉圆圈的背景
        swipeRefreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshlayout.setColorSchemeResources(android.R.color.holo_red_light);
        swipeRefreshlayout.setProgressViewOffset(false, -100, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshlayout.setOnRefreshListener(onRefreshListener);
    }


    @Override
    public void onRequestCallBackSuccess(T bean) {

    }

    @Override
    public void onRequestCallBackError() {

    }
}
