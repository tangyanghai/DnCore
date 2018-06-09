package com.example.corelibrary.net.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Administrator
 * @time : 9:10
 * @for : 集成了自己定义的接口的回调
 */
public class RequestCallBack implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public RequestCallBack(IRequest request, ISuccess success, IFailure failure, IError error) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (REQUEST != null) {
            REQUEST.onEnd();
        }
        if (response.isSuccessful()) {
            //请求成功了
            if (call.isExecuted()) {//为什么要判断执行了?
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (REQUEST != null) {
            REQUEST.onEnd();
        }
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
    }
}
