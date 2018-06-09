package com.example.corelibrary.net.callback;

/**
 * @author : Administrator
 * @time : 9:08
 * @for : 请求流程回调
 */
public interface IRequest {
    /**
     * 请求开始
     */
    void onStart();

    /**
     * 请求完成
     */
    void onEnd();
}
