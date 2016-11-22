package com.golove.model;

/**
 * 类描述：
 * 创建人：
 * 创建时间：16-4-16 下午8:48
 * 修改备注：
 */
public class ResultStateModel<T> {

    public int state;
    public String message;
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
