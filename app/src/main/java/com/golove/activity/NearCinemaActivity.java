package com.golove.activity;

import android.content.Intent;
import android.os.Bundle;

import com.golove.R;

/*
 * 附近影院
 */
public class NearCinemaActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_cinema);

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
//        double latitude = intent.getDoubleExtra("latitude",0);
//        double longitude = intent.getDoubleExtra("longitude",0);


    }



}
