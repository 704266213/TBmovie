package com.golove.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.golove.R;
import com.golove.adapter.ShowAdapter;
import com.golove.callback.RequestCallBack;
import com.golove.divider.FilmDivider;
import com.golove.model.CartoonList;
import com.golove.model.ResultStateModel;
import com.golove.request.BaseRequest;
import com.golove.ui.OnLoadDataListener;
import com.golove.ui.neterror.NetWorkErrorView;

import java.util.List;

/**
 * 演出界面
 */
public class ShowFragment extends MainFragment<ResultStateModel<List<CartoonList>>>{


    private RecyclerView recyclerView;
    private ShowAdapter showAdapter;
    private BaseRequest baseRequest;

    public ShowFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.initView(view);
        initView(view);
    }

    public void initView(View view) {


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new FilmDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 50, getResources().getColor(R.color.cartoon_line)));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        netWorkErrorView = (NetWorkErrorView) view.findViewById(R.id.netWorkErrorView);
        netWorkErrorView.setOnFreshListener(this);


        showAdapter = new ShowAdapter(getActivity());
        recyclerView.setAdapter(showAdapter);


        baseRequest = new BaseRequest();
    }

    public void requestData() {
        requestData(netWorkErrorView);
    }

    private void requestData(OnLoadDataListener onLoadDataListener) {
        String url = "https://raw.githubusercontent.com/704266213/data/master/show_test.txt";
        RequestCallBack requestCallBack = new RequestCallBack(this, onLoadDataListener);
        baseRequest.sendRequest(url, requestCallBack);
    }

    @Override
    public void onRequestCallBackSuccess(ResultStateModel<List<CartoonList>> bean) {
        swipeRefreshlayout.setVisibility(View.VISIBLE);
        netWorkErrorView.setVisibility(View.GONE);
        if (swipeRefreshlayout.isRefreshing()) {
            swipeRefreshlayout.setRefreshing(false);
        }

        List<CartoonList> cartoonLists = bean.getResult();
        showAdapter.addData(cartoonLists);
    }

    @Override
    public void onRequestCallBackError() {
        if (swipeRefreshlayout.isRefreshing()) {
            Toast.makeText(getContext(), "下拉刷新失败，请重试", Toast.LENGTH_SHORT).show();
            swipeRefreshlayout.setRefreshing(false);
        }
    }


}
