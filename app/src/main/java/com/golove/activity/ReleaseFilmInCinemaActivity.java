package com.golove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.golove.R;
import com.golove.adapter.ReleaseFilmInCinemaAdapter;
import com.golove.divider.FilmDivider;
import com.golove.ui.neterror.NetWorkErrorView;

/*
 * 影院列表
 */
public class ReleaseFilmInCinemaActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView title;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private NetWorkErrorView netWorkErrorView;
    private View line;

    private ReleaseFilmInCinemaAdapter releaseFilmInCinemaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_film_in_cinema);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String fileName = intent.getStringExtra("fileName");
        title = (TextView) findViewById(R.id.title);
        title.setText(fileName);
        netWorkErrorView = (NetWorkErrorView) findViewById(R.id.netWorkErrorView);
        line = findViewById(R.id.line);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        TabLayout.Tab tab = tabLayout.newTab().setText("今天 01-03");
        tabLayout.addTab(tab, 0, true);
        TabLayout.Tab tab1 = tabLayout.newTab().setText("明天 01-04");
        tabLayout.addTab(tab1, 1, false);
        TabLayout.Tab tab2 = tabLayout.newTab().setText("后台 01-05");
        tabLayout.addTab(tab2, 2, false);
        TabLayout.Tab tab3 = tabLayout.newTab().setText("周五 01-06");
        tabLayout.addTab(tab3, 3, false);
        TabLayout.Tab tab4 = tabLayout.newTab().setText("周六 01-07");
        tabLayout.addTab(tab4, 4, false);
        TabLayout.Tab tab5 = tabLayout.newTab().setText("周日 01-08");
        tabLayout.addTab(tab5, 5, false);
        tabLayout.setOnTabSelectedListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        initRefreshView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new FilmDivider(this, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.top_line), (int) getResources().getDimension(R.dimen.film_line_paddingLeft)));
        recyclerView.setHasFixedSize(true);


        releaseFilmInCinemaAdapter = new ReleaseFilmInCinemaAdapter();
        recyclerView.setAdapter(releaseFilmInCinemaAdapter);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        recyclerView.scrollToPosition(0);
        if (position == 0) {
            releaseFilmInCinemaAdapter.updateAdapterData(20);
        } else if (position == 5) {
            releaseFilmInCinemaAdapter.updateAdapterData(0);
        } else {
            releaseFilmInCinemaAdapter.updateAdapterData(position * 4);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /*
     * 下拉刷新回调
     */
    public void onRefresh() {
        swipeRefreshlayout.setRefreshing(false);
    }
}
