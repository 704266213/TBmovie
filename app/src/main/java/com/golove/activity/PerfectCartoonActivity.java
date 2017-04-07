package com.golove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.adapter.PerfectCartoonAdapter;
import com.golove.callback.RequestCallBack;
import com.golove.divider.HorizontalDividerItemDecoration;
import com.golove.listener.OnItemClickListener;
import com.golove.model.CartoonDetailModel;
import com.golove.model.CartoonInfoModel;
import com.golove.model.PerfectCartoonModel;
import com.golove.model.ResultStateModel;
import com.golove.request.BaseRequest;
import com.golove.ui.OnLoadDataListener;
import com.golove.ui.neterror.NetWorkErrorView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PerfectCartoonActivity extends BaseActivity<ResultStateModel<PerfectCartoonModel>> implements AppBarLayout.OnOffsetChangedListener, NetWorkErrorView.OnFreshListener, OnItemClickListener {

    private ImageView perfectBackground;
    private RecyclerView recyclerView;
    private NetWorkErrorView netWorkErrorView;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView title;
    private BaseRequest baseRequest;
    private Picasso picasso;
    private PerfectCartoonAdapter perfectCartoonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_cartoon);
        initView();
    }

    private void initView() {
        picasso = Picasso.with(GoloveApplication.goloveApplication);
        netWorkErrorView = (NetWorkErrorView) findViewById(R.id.netWorkErrorView);
        netWorkErrorView.setOnFreshListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        perfectBackground = (ImageView) findViewById(R.id.perfectBackground);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbarLayout);
        appBarLayout.addOnOffsetChangedListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        HorizontalDividerItemDecoration horizontalDividerItemDecoration = new HorizontalDividerItemDecoration.Builder(this)
                .drawable(R.drawable.perfect_divider)
                .build();

        recyclerView.addItemDecoration(horizontalDividerItemDecoration);

        perfectCartoonAdapter = new PerfectCartoonAdapter(this, this);
        recyclerView.setAdapter(perfectCartoonAdapter);

        baseRequest = new BaseRequest();
        requestData(netWorkErrorView);
    }

    @Override
    public void onReFresh() {
        requestData(netWorkErrorView);
    }

    private void requestData(OnLoadDataListener onLoadDataListener) {
        String url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/perfect_cartoon.txt";
        RequestCallBack requestCallBack = new RequestCallBack(this, onLoadDataListener);
        baseRequest.sendRequest(url, requestCallBack);
    }


    @Override
    public void onRequestCallBackSuccess(ResultStateModel<PerfectCartoonModel> perfectCartoonModelResultStateModel) {
        netWorkErrorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

        PerfectCartoonModel perfectCartoonModel = perfectCartoonModelResultStateModel.getResult();
        picasso.load(perfectCartoonModel.getSurface_image())
                .fit()
                .into(perfectBackground);

        List<CartoonInfoModel> cartoonInfoModels = perfectCartoonModel.getTopics();
        perfectCartoonAdapter.addData(cartoonInfoModels);
    }

    @Override
    public void onRequestCallBackError() {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int appBarLayoutHeight = appBarLayout.getHeight();
        int toolbarHeight = toolbar.getHeight();
        int offset = toolbarHeight - appBarLayoutHeight;
        if (verticalOffset == offset) {
            title.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
        }
    }


    @Override
    public void onItemClick(View item, int position) {
        Intent intent = new Intent(this, CartoonInfoActivity.class);
        CartoonInfoModel cartoonInfoModel = perfectCartoonAdapter.getData(position);
        CartoonDetailModel cartoonDetailModel = new CartoonDetailModel();
        cartoonDetailModel.setTitle(cartoonInfoModel.getTitle());
        cartoonDetailModel.setCategory(cartoonInfoModel.getCategory());
        cartoonDetailModel.setPic(cartoonInfoModel.getCover_image_url());
        intent.putExtra("cartoonDetailModel",cartoonDetailModel);
        startActivity(intent);
    }
}
