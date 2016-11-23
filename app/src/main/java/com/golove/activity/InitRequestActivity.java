package com.golove.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.golove.listener.OnRequestCallBackListener;
import com.golove.model.ResultStateModel;

/**
*
* 类描述：
* 创建人：  alan
* 创建时间：16-4-16 下午9:08
* 修改备注：
* @version
*
*/
public abstract class InitRequestActivity extends AppCompatActivity implements OnRequestCallBackListener {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    /**
     * 方法描述：
     * 参数说明：
     * 返回值：
     */
    public <T extends ResultStateModel> void onInitRequestSuccess(T bean) {

    }

    /**
     * 方法描述：
     * 参数说明：
     * 返回值：
     */
    public void onInitRequestError() {

    }
}
