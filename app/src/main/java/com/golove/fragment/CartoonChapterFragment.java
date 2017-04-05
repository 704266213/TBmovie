package com.golove.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.golove.R;
import com.golove.adapter.CartoonChapterAdapter;
import com.golove.model.CartoonChapterModel;
import com.golove.model.ComicModel;
import com.golove.ui.neterror.NetWorkErrorView;

import java.util.List;


/**
 *
 */
public class CartoonChapterFragment extends TabFragment<ComicModel> {

    private NetWorkErrorView netWorkErrorView;
    private RecyclerView recyclerView;
    private CartoonChapterAdapter cartoonChapterAdapter;

    public CartoonChapterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cartoon_chapter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        netWorkErrorView = (NetWorkErrorView) view.findViewById(R.id.netWorkErrorView);
        netWorkErrorView.setOnFreshListener(this);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View headView = layoutInflater.inflate(R.layout.cartoon_chapter_header, null);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        LinearLayoutManager filmReviewLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(filmReviewLayoutManager);
        recyclerView.setHasFixedSize(true);

        cartoonChapterAdapter = new CartoonChapterAdapter(layoutInflater);
        cartoonChapterAdapter.setHeadView(headView);
        recyclerView.setAdapter(cartoonChapterAdapter);

    }

    @Override
    public void onRequestCallBackSuccess(ComicModel comicModel) {
        netWorkErrorView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        cartoonChapterAdapter.addDataToList(comicModel.getComics());
    }

    @Override
    public void onRequestCallBackError() {
        netWorkErrorView.loadDataErrorView();
    }

    @Override
    public void onTabChange(int position) {

    }

    @Override
    public void requestData() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
}
