package com.example.corelibrary.net.rx;

import android.text.TextUtils;

import com.example.corelibrary.net.HttpMethod;
import com.example.corelibrary.net.RestCreator;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author : Administrator
 * @time : 11:08
 * @for :
 */
public class RxRestCreatorBuilder {
    //网络请求的参数
    private String mUrl;
    private HashMap<String, Object> mParams;
    private RequestBody mBody;

    private HttpMethod mMethod;

    public RxRestCreatorBuilder url(String url) {
        this.mUrl = url;
        mParams = new HashMap<>();
        return this;
    }

    public RxRestCreatorBuilder params(HashMap<String, Object> params) {
        this.mParams.putAll(params);
        return this;
    }

    /**
     * 单个添加属性
     */
    public RxRestCreatorBuilder add(String key, Object value) {
        if (TextUtils.isEmpty(key)) {
            return this;
        }

        mParams.put(key, value);
        return this;
    }

    public RxRestCreatorBuilder row(String raw) {
        this.mBody = RequestBody.create(
                MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }


    public RxRestCreatorBuilder method(HttpMethod method) {
        this.mMethod = method;
        return this;
    }

    private RxRestClient build() {
        //将全局属性放进属性之中
        mParams = RestCreator.createParams(mParams);
        return new RxRestClient(mUrl, mParams, mBody, mMethod);
    }

    /**
     * 执行网络请求
     */
    public Observable<String> excute() {
        return build().excute();
    }

}

