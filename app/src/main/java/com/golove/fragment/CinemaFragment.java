package com.golove.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.activity.NearCinemaActivity;
import com.golove.adapter.CinemaAdapter;
import com.golove.adapter.CinemaHeaderAdapter;
import com.golove.adapter.FilmReviewAdapter;
import com.golove.callback.RequestCallBack;
import com.golove.divider.FilmDivider;
import com.golove.listener.OnLoadMoreListener;
import com.golove.loadmore.OnLinearLoadMoreListener;
import com.golove.loadmore.OnLoadMoreScrollListener;
import com.golove.model.CinemaModel;
import com.golove.model.FilmHotModel;
import com.golove.model.FilmModel;
import com.golove.model.ResultStateModel;
import com.golove.popwindow.CinemaLocalPopWindow;
import com.golove.popwindow.FilterPopWindow;
import com.golove.request.BaseRequest;
import com.golove.service.LocationService;
import com.golove.ui.OnLoadDataListener;
import com.golove.ui.circlerecyclerview.CircleRecyclerView;
import com.golove.ui.circlerecyclerview.CircularViewMode;
import com.golove.ui.footer.FooterView;
import com.golove.ui.neterror.NetWorkErrorView;
import com.golove.uitls.XLog;

import java.util.ArrayList;
import java.util.List;

import static com.golove.loadmore.OnLoadMoreScrollListener.*;

/*
 * 影院
 */
public class CinemaFragment extends MainFragment<ResultStateModel<List<CinemaModel>>> implements View.OnClickListener ,OnLoadMoreListener {

    private TextView location;
    private ImageButton search;
    private ImageButton filter;
    private View mainLine;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshlayout;
    private CinemaAdapter cinemaAdapter;
    private OnLinearLoadMoreListener onLinearLoadMoreListener;
    private BaseRequest baseRequest;

    private View headView;
    private CinemaHeaderAdapter cinemaHeaderAdapter;
    private CircleRecyclerView circleRecyclerView;
    private TextView openMap;
    private TextView locationDetail;


    private FooterView footerView;
    private double latitude;
    private double longitude;
    private int pageNo = 1;

    private LocationService locationService;

    private View parentView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cinema_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        locationService = ((GoloveApplication) (getActivity().getApplication())).locationService;

        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        locationService.start();// 定位SDK
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();// 定位SDK
    }

    private void initView(View view){
        parentView = view;
        location = (TextView) view.findViewById(R.id.location);
        location.setOnClickListener(this);
        search = (ImageButton) view.findViewById(R.id.search);
        search.setOnClickListener(this);
        filter = (ImageButton) view.findViewById(R.id.filter);
        filter.setOnClickListener(this);
        mainLine = view.findViewById(R.id.main_line);
        openMap = (TextView) view.findViewById(R.id.openMap);
        openMap.setOnClickListener(this);
        locationDetail = (TextView) view.findViewById(R.id.locationDetail);


        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
        headView = layoutInflater.inflate(R.layout.cinema_headerview, null, false);
        LinearLayout indicators = (LinearLayout)headView.findViewById(R.id.indicators);
        circleRecyclerView = (CircleRecyclerView)headView.findViewById(R.id.circleRecyclerView);
        final LinearLayoutManager filmReviewLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        circleRecyclerView.setLayoutManager(filmReviewLayoutManager);
        CircularViewMode mItemViewMode = new CircularViewMode();
        circleRecyclerView.setViewMode(mItemViewMode);
        circleRecyclerView.setNeedCenterForce(true);
        circleRecyclerView.setNeedLoop(true);
        cinemaHeaderAdapter = new CinemaHeaderAdapter(indicators);
        circleRecyclerView.setAdapter(cinemaHeaderAdapter);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        netWorkErrorView = (NetWorkErrorView) view.findViewById(R.id.netWorkErrorView);
        netWorkErrorView.setOnFreshListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new FilmDivider(getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.top_line), (int) getResources().getDimension(R.dimen.film_line_paddingLeft)));
        recyclerView.setHasFixedSize(true);

        /*
         * 加载更多时，失败的回调设置
         */
        footerView = new FooterView(view.getContext());
        footerView.setOnLoadMoreListener(this);

        cinemaAdapter = new CinemaAdapter();
        cinemaAdapter.setHeadView(headView);
        cinemaAdapter.setFooterView(footerView);
        recyclerView.setAdapter(cinemaAdapter);

        /*
         * 滚动到底部自动加载更多
         */
        onLinearLoadMoreListener = new OnLinearLoadMoreListener(footerView, this);
        recyclerView.addOnScrollListener(onLinearLoadMoreListener);

        swipeRefreshlayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshlayout);
        // 设定下拉圆圈的背景
        swipeRefreshlayout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        swipeRefreshlayout.setColorSchemeResources(android.R.color.holo_red_light);

        // 设置手指在屏幕下拉多少距离会触发下拉刷新
//        swipeRefreshlayout.setDistanceToTriggerSync(400);
        swipeRefreshlayout.setProgressViewOffset(false, -100, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        swipeRefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                requestData(footerView);
            }
        });

        baseRequest = new BaseRequest();
    }

    public void requestData() {
        requestData(netWorkErrorView);
    }

    private void requestData(OnLoadDataListener onLoadDataListener) {
        String url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/cinemalist" + pageNo + ".txt";
        RequestCallBack requestCallBack = new RequestCallBack(this, onLoadDataListener);
        baseRequest.sendRequest(url, requestCallBack);
    }

    @Override
    public void onRequestCallBackSuccess(ResultStateModel<List<CinemaModel>> bean) {
        swipeRefreshlayout.setVisibility(View.VISIBLE);
        netWorkErrorView.setVisibility(View.GONE);
        List<CinemaModel> cinemaModels = bean.getResult();

        if (swipeRefreshlayout.isRefreshing()) {
            swipeRefreshlayout.setRefreshing(false);
            cinemaAdapter.addFreshData(cinemaModels);
            onLinearLoadMoreListener.setHasMore(true);
        } else {
            if (cinemaModels.size() < 15) {
                footerView.loadNoDataOrNoMoreDataView();
                onLinearLoadMoreListener.setHasMore(false);
            }
            cinemaAdapter.addData(cinemaModels);
            onLinearLoadMoreListener.isLoadingMore(false);
        }

        List<String> list = new ArrayList<>();
        list.add("https://raw.githubusercontent.com/704266213/data/master/WebContent/img/banner1.png");
        list.add("https://raw.githubusercontent.com/704266213/data/master/WebContent/img/banner2.png");
        list.add("https://raw.githubusercontent.com/704266213/data/master/WebContent/img/banner3.png");
        list.add("https://raw.githubusercontent.com/704266213/data/master/WebContent/img/banner4.png");
        cinemaHeaderAdapter.addDataToList(list);

        pageNo += 1;
    }

    @Override
    public void onRequestCallBackError() {
        if (swipeRefreshlayout.isRefreshing()) {
            Toast.makeText(getContext(), "下拉刷新失败，请重试", Toast.LENGTH_SHORT).show();
            swipeRefreshlayout.setRefreshing(false);
        }
        onLinearLoadMoreListener.isLoadingMore(false);
    }

    @Override
    public void onLoadMore() {
        Log.e("XLog", "=============加载数据================");
        requestData(footerView);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location:
                CinemaLocalPopWindow cinemaLocalPopWindow = new CinemaLocalPopWindow(getContext());
                cinemaLocalPopWindow.showAtLocation(parentView, Gravity.BOTTOM,0,0);
                break;
            case R.id.filter:
                FilterPopWindow filterPopWindow = new FilterPopWindow(getContext());
                filterPopWindow.showAsDropDown(mainLine);
                break;
            case R.id.search:

                break;
            case R.id.openMap:
                Intent intent = new Intent(getContext(), NearCinemaActivity.class);
                intent.putExtra("latitude" ,latitude);
                intent.putExtra("longitude" ,longitude);
                startActivity(intent);
                break;
        }


    }



    /*****
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                latitude = location.getLatitude();
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                longitude = location.getLongitude();
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                String address = "我在："+location.getCity() + location.getDistrict() + location.getStreet() + location.getLocationDescribe();
                locationDetail.setText(address);
//                XLog.e("XLog",sb.toString());
            }
        }

    };





}
