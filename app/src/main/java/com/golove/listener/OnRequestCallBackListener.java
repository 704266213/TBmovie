package com.golove.listener;

import com.golove.model.ResultStateModel;

/**
 * 类描述：进入界面时的初始化请求的回调接口
 * 创建人：alan
 * 创建时间：2016-04-04 22:46
 * 修改备注：
 */
public interface OnRequestCallBackListener<T extends ResultStateModel> {

    /**
     * 方法描述：初始化请求成功时的回调
     * 参数说明：result网络请求成功时的回调结果
     * 返回值：
     */
     <T> void onRequestCallBackSuccess(T bean);


     void onRequestCallBackError();


}
