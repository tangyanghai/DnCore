package com.example.corelibrary.rxbus;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : tyh
 * @time : 2018/06/12
 * @for : 事件总线
 */
public class RxBus {
    /**
     * 注册对象集合
     */
    private Set<Object> subscribers;

    private static volatile RxBus instance;

    private RxBus() {
        //初始化set  会牺牲掉一部分性能,但是线程安全
        subscribers = new CopyOnWriteArraySet<>();
    }

    /**
     * @return 获取单例
     */
    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    /**
     * 注册
     */
    public void regist(Object object) {
        subscribers.add(object);
    }

    /**
     * 反注册
     */
    public void unRegist(Object object) {
        subscribers.remove(object);
    }

    /**
     * 处理事件
     */
    public void processChain(Function function) {
        processChain(function, null);
    }

    /**
     * 处理事件
     */
    public void processChain(Function function, final String tag) {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .map(function)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object data) throws Exception {
                        if (data == null) {
                            return;
                        }
                        send(data, tag);
                    }
                });
    }

    private static final String TAG = "======RxBus======";

    /**
     * 将得到的数据,发送到订阅者
     */
    private void send(Object data, @Nullable String tag) {
        Annotation annotation;
        for (Object subscriber : subscribers) {
            Log.i(TAG, "send: 外层在循环");
            Class<?> cls = subscriber.getClass();
            Method[] declaredMethods = cls.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                Log.i(TAG, "send: 内层在循环");
                annotation = declaredMethod.getAnnotation(tag == null ? RxEvent.class : RxEventWithTag.class);
                if (annotation != null) {
                    try {
                        if (tag == null || TextUtils.equals(tag, ((RxEventWithTag) annotation).value())) {
                            declaredMethod.invoke(subscriber, data);
                            Log.i(TAG, "send: 内层在循环打断");
                            break;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
