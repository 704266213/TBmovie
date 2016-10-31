package com.golove.adapter;

import android.widget.BaseAdapter;

import com.golove.model.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：适配器的基础类，
 * 创建人：alan
 * 创建时间：2016-04-04 22:32
 * 修改备注：
 */
public abstract class AbstartBaseAdapter<T extends BaseBean> extends BaseAdapter {

    private List<T> listData = new ArrayList<T>();

    /**
     * 方法描述：添加数据，并更新数据列表
     * 参数说明：
     * 返回值：
     */
    public void addData(List<T> data){
        if(data != null){
            listData.addAll(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 方法描述：刷新数据列表
     * 参数说明：
     * 返回值：
     */
    public void reflashData(List<T> data){
        if(data != null){
            listData.clear();
            addData(data);
        }
    }


    public int getCount() {
        return listData.size();
    }

    public T getItem(int position) {
        return listData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


}
