package com.golove.parse;

import java.lang.reflect.Type;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-04-04 22:27
 * 修改备注：
 */
public class BaseParse<T> {

    public BaseParse(){
       Type type = getClass().getGenericSuperclass();

    }

}
