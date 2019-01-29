package com.example.corelibrary.net;

import android.text.TextUtils;

import com.example.corelibrary.net.callback.IError;
import com.example.corelibrary.net.callback.IFailure;
import com.example.corelibrary.net.callback.IRequest;
import com.example.corelibrary.net.callback.ISuccess;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author : Administrator
 * @time : 11:08
 * @for :
 */
public class RestCreatorBuilder {
    //网络请求的参数
    private String mUrl;
    private HashMap<String, Object> mParams;
    private RequestBody mBody;

    //回调
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private IError mError;
    private HttpMethod mMethod;

    public RestCreatorBuilder url(String url) {
        this.mUrl = url;
        mParams = new HashMap<>();
        return this;
    }

    public RestCreatorBuilder params(HashMap<String, Object> params) {
        this.mParams.putAll(params);
        return this;
    }

    /**
     * 单个添加属性
     */
    public RestCreatorBuilder add(String key, Object value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }

        mParams.put(key, value);
        return this;
    }

    public RestCreatorBuilder row(String raw) {
        this.mBody = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public RestCreatorBuilder request(IRequest request) {
        this.mRequest = request;
        return this;
    }

    public RestCreatorBuilder success(ISuccess success) {
        this.mSuccess = success;
        return this;
    }

    public RestCreatorBuilder failure(IFailure failure) {
        this.mFailure = failure;
        return this;
    }

    public RestCreatorBuilder error(IError error) {
        this.mError = error;
        return this;
    }

    public RestCreatorBuilder method(HttpMethod method) {
        this.mMethod = method;
        return this;
    }

    private RestClient build() {
        //添加全局属性
        mParams = RestCreator.creatParams(mParams);
        return new RestClient(mUrl, mParams, mBody, mRequest, mSuccess, mFailure, mError, mMethod);
    }

    /**
     * 执行网络请求
     */
    public void excute() {
        build().excute();
    }
}

