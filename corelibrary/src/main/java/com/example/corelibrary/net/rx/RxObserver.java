package com.example.corelibrary.net.rx;

import android.util.Log;

import com.example.corelibrary.net.JsonConvert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author : tyh
 * @time : 2018/06/11
 * @for :
 */
public abstract class RxObserver<T> extends IObserver<T> implements Observer<String> {
    public static final String TAG = "=======>Rx网络请求";

    @Override
    public void onSubscribe(Disposable d) {
        //可以在这里来进行弹出dialog
        Log.i(TAG, "onSubscribe: 开始请求");
    }

    @Override
    public void onNext(String content) {
        Log.i(TAG, "onNext: 开始解析");
        try {
            Type genType = getClass().getGenericSuperclass();
            Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            JsonConvert<T> convert = new JsonConvert(type);
            T t = convert.convert(content);
            onSucceed(t);
        } catch (Exception e) {
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e);
    }

    @Override
    public void onComplete() {
        Log.i(TAG, "onComplete: 请求完成");
    }
}
