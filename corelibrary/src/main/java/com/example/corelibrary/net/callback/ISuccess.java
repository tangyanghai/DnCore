package com.example.corelibrary.net.callback;

/**
 * @author : Administrator
 * @time : 9:04
 * @for :请求成功回调顶层接口
 */
public interface ISuccess<T> {
    void onSuccess(T t);
}
