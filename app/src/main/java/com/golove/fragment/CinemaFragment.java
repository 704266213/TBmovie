package com.golove.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.golove.FilterPopWindow;
import com.golove.R;
import com.golove.ui.neterror.NetWorkErrorView;

/*
 * 影院
 */
public class CinemaFragment extends MainFragment implements View.OnClickListener {

    private TextView location;
    private ImageButton search;
    private ImageButton filter;
    private View mainLine;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cinema_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view){
        location = (TextView) view.findViewById(R.id.location);
        location.setOnClickListener(this);
        search = (ImageButton) view.findViewById(R.id.search);
        search.setOnClickListener(this);
        filter = (ImageButton) view.findViewById(R.id.filter);
        filter.setOnClickListener(this);


        mainLine = (View) view.findViewById(R.id.main_line);
        netWorkErrorView = (NetWorkErrorView) view.findViewById(R.id.netWorkErrorView);
    }

    public void requestData() {
        Log.e("XLog","==========requestData=CinemaFragment=========");
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location:

                break;
            case R.id.filter:
                FilterPopWindow filterPopWindow = new FilterPopWindow(getContext());
                filterPopWindow.showAsDropDown(mainLine);
                break;
            case R.id.search:

                break;

        }


    }
}
