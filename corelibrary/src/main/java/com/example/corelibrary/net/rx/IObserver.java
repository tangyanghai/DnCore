package com.example.corelibrary.net.rx;

/**
 * @author : tyh
 * @time : 2018/06/11
 * @for :
 */
public abstract class IObserver<T> {
    protected abstract void onSucceed(T t);
    public abstract void onFailure(Throwable t);
}
