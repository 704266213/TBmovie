package com.golove.ui;


public interface OnLoadDataListener {

    //正在加载数据的视图
    void loadDataErrorView();

    //加载失败的视图
    void loadingDataView();

    //没有数据的视图或者没有更多数据
    void loadNoDataOrNoMoreDataView();

}
