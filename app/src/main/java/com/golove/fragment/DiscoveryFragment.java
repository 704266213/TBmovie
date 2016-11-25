package com.golove.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.golove.R;


public class DiscoveryFragment extends MainFragment {


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.discovery_fragment, container, false);
    }

    public void requestData() {
        Log.e("XLog","==========requestData==DiscoveryFragment========");
    }

}
