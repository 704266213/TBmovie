package com.golove.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.golove.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartoonChapterFragment extends TabFragment {


    public CartoonChapterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cartoon_chapter, container, false);
    }

    @Override
    public void onRequestCallBackSuccess(Object bean) {

    }

    @Override
    public void onRequestCallBackError() {

    }

    @Override
    public void onTabChange(int position) {

    }

    @Override
    public void requestData() {

    }
}
