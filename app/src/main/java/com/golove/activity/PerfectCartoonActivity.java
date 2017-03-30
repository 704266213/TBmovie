package com.golove.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.adapter.PerfectCartoonAdapter;
import com.golove.callback.RequestCallBack;
import com.golove.divider.HorizontalDividerItemDecoration;
import com.golove.model.CartoonInfoModel;
import com.golove.model.PerfectCartoonModel;
import com.golove.model.ResultStateModel;
import com.golove.request.BaseRequest;
import com.golove.ui.OnLoadDataListener;
import com.golove.ui.neterror.NetWorkErrorView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class PerfectCartoonActivity extends BaseActivity<ResultStateModel<PerfectCartoonModel>> {


    private ImageView perfectBackground;
    private RecyclerView recyclerView;
    private NetWorkErrorView netWorkErrorView;
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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        perfectBackground = (ImageView) findViewById(R.id.perfectBackground);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        HorizontalDividerItemDecoration horizontalDividerItemDecoration =new HorizontalDividerItemDecoration.Builder(this)
                .drawable(R.drawable.perfect_divider)
                .build();

        recyclerView.addItemDecoration(horizontalDividerItemDecoration);

        perfectCartoonAdapter = new PerfectCartoonAdapter(this);
        recyclerView.setAdapter(perfectCartoonAdapter);

        baseRequest = new BaseRequest();
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
}
