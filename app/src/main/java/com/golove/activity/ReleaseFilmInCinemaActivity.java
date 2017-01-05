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
import com.golove.callback.RequestCallBack;
import com.golove.divider.FilmDivider;
import com.golove.model.CinemaModel;
import com.golove.model.ReleaseFilmInCinemaModel;
import com.golove.model.ResultStateModel;
import com.golove.request.BaseRequest;
import com.golove.ui.OnLoadDataListener;
import com.golove.ui.neterror.NetWorkErrorView;

import java.util.List;

/*
 * 影院列表
 */
public class ReleaseFilmInCinemaActivity extends BaseActivity<ResultStateModel<List<ReleaseFilmInCinemaModel>>> implements TabLayout.OnTabSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView title;
    private TabLayout tabLayout;
    private RecyclerView recyclerView;
    private NetWorkErrorView netWorkErrorView;
    private View line;

    private ReleaseFilmInCinemaAdapter releaseFilmInCinemaAdapter;

    private BaseRequest baseRequest;
    private List<ReleaseFilmInCinemaModel> releaseFilmInCinemaModels;


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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        initRefreshView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new FilmDivider(this, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.top_line), (int) getResources().getDimension(R.dimen.film_line_paddingLeft)));
        recyclerView.setHasFixedSize(true);

        releaseFilmInCinemaAdapter = new ReleaseFilmInCinemaAdapter();
        recyclerView.setAdapter(releaseFilmInCinemaAdapter);


        baseRequest = new BaseRequest();
        requestData(netWorkErrorView);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        updateAdapterData(position);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void initTabLayout() {
        if (releaseFilmInCinemaModels != null) {
            int size = releaseFilmInCinemaModels.size();
            for (int i = 0; i < size; i++) {
                ReleaseFilmInCinemaModel releaseFilmInCinemaModel = releaseFilmInCinemaModels.get(i);
                TabLayout.Tab tab = tabLayout.newTab().setText(releaseFilmInCinemaModel.getReleaseDate());
                tabLayout.addTab(tab, i, i == 0 ? true : false);
            }
            tabLayout.setOnTabSelectedListener(this);
            updateAdapterData(0);
        }
    }

    private void updateAdapterData(int position) {
        ReleaseFilmInCinemaModel releaseFilmInCinemaModel = releaseFilmInCinemaModels.get(position);
        if (releaseFilmInCinemaModel != null) {
            List<CinemaModel> cinemaModels = releaseFilmInCinemaModel.getCinemaList();
            if (cinemaModels != null) {
                recyclerView.scrollToPosition(0);
                releaseFilmInCinemaAdapter.updateAdapterData(cinemaModels);
            }
        }
    }

    /*
     * 下拉刷新回调
     */
    public void onRefresh() {
        swipeRefreshlayout.setRefreshing(false);
    }


    private void requestData(OnLoadDataListener onLoadDataListener) {
        String url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/releasefilmincinema.txt";
        RequestCallBack requestCallBack = new RequestCallBack(this, onLoadDataListener);
        baseRequest.sendRequest(url, requestCallBack);
    }


    @Override
    public void onRequestCallBackSuccess(ResultStateModel<List<ReleaseFilmInCinemaModel>> bean) {
        netWorkErrorView.setVisibility(View.GONE);
        tabLayout.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        swipeRefreshlayout.setVisibility(View.VISIBLE);
        releaseFilmInCinemaModels = bean.getResult();
        initTabLayout();
    }

    @Override
    public void onRequestCallBackError() {

    }
}
