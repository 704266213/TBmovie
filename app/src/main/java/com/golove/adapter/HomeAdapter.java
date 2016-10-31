package com.golove.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.golove.model.GoodsBean;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-04-04 22:50
 * 修改备注：
 */
public class HomeAdapter extends AbstartBaseAdapter<GoodsBean> {

    public View getView(int position, android.view.View convertView, ViewGroup parent) {
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.listitem, null);
//        }
        return convertView;
    }
}
