package com.example.corelibrary.net.callback;

/**
 * @author : Administrator
 * @time : 9:05
 * @for : 请求返回错误顶层接口
 */
public interface IError {
    void onError(int code,String reason);
}
