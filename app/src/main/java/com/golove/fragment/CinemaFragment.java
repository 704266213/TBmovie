package com.golove.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.golove.R;

public class CinemaFragment extends MainFragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cinema_fragment, container, false);
    }


    public void initRequest(){
        Log.d("Message", "CinemaFragment is Init");
    }


}
