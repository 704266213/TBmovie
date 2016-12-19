package com.golove.fragment;


import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.adapter.CinemaAdapter;
import com.golove.callback.RequestCallBack;
import com.golove.divider.FilmDivider;
import com.golove.listener.OnLoadMoreListener;
import com.golove.loadmore.OnLinearLoadMoreListener;
import com.golove.model.FilmHotModel;
import com.golove.model.FilmModel;
import com.golove.model.ResultStateModel;
import com.golove.popwindow.CinemaLocalPopWindow;
import com.golove.popwindow.FilterPopWindow;
import com.golove.request.BaseRequest;
import com.golove.service.LocationService;
import com.golove.ui.OnLoadDataListener;
import com.golove.ui.footer.FooterView;
import com.golove.ui.neterror.NetWorkErrorView;
import com.golove.uitls.XLog;

import java.util.List;

/*
 * 影院
 */
public class CinemaFragment extends MainFragment<ResultStateModel<FilmHotModel>> implements View.OnClickListener ,OnLoadMoreListener {

    private TextView location;
    private ImageButton search;
    private ImageButton filter;
    private View mainLine;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshlayout;
    private CinemaAdapter cinemaAdapter;
    private OnLinearLoadMoreListener onLinearLoadMoreListener;
    private BaseRequest baseRequest;


    private FooterView footerView;
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
//        discoveryAdapter.setHeadView(headView);
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
        String url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/data/filmlist" + pageNo + ".txt";
        RequestCallBack requestCallBack = new RequestCallBack(this, onLoadDataListener);
        baseRequest.sendRequest(url, requestCallBack);
    }

    @Override
    public void onRequestCallBackSuccess(ResultStateModel<FilmHotModel> bean) {
        swipeRefreshlayout.setVisibility(View.VISIBLE);
        netWorkErrorView.setVisibility(View.GONE);
        FilmHotModel filmHotModel = bean.getResult();

        List<FilmModel> filmModels = filmHotModel.getFilmModels();
        if (swipeRefreshlayout.isRefreshing()) {
            swipeRefreshlayout.setRefreshing(false);
            cinemaAdapter.addFreshData(filmModels);
            onLinearLoadMoreListener.setHasMore(true);
        } else {
            if (filmModels.size() < 15) {
                footerView.loadNoDataOrNoMoreDataView();
                onLinearLoadMoreListener.setHasMore(false);
            }
            cinemaAdapter.addData(filmModels);
            onLinearLoadMoreListener.isLoadingMore(false);
        }
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
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(location.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(location.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
//                sb.append(location.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(location.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(location.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(location.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(location.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(location.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(location.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(location.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(location.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
//                sb.append(location.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(location.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                    for (int i = 0; i < location.getPoiList().size(); i++) {
                        Poi poi = (Poi) location.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(location.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(location.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(location.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
//                    sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (location.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(location.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(location.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                XLog.e("XLog",sb.toString());
            }
        }

    };

}
