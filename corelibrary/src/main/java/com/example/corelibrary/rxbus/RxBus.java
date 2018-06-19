package com.example.corelibrary.rxbus;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.text.TextUtils;

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
    public <R extends Result> void processChain(Function<String, R> function) {
        processChain(function, null);
    }

    /**
     * 处理事件
     */
    @SuppressLint("CheckResult")
    public <R extends Result> void processChain(Function<String, R> function, final String tag) {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                .map(function)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        if (result.data == null) {
                            return;
                        }
                        send(result.data, tag);
                    }
                });
    }

    private static final String TAG = "======RxBus======";


    /**
     * 发送数据到订阅者
     */
    public void send(Object data) {
        send(data, null);
    }

    /**
     * 发送数据到所有订阅者
     */
    public void send(Object data, @Nullable String tag) {
        for (Object subscriber : subscribers) {
            handleSubscriber(data, tag, subscriber);
        }
    }

    /**
     * 将数据发送到订阅者
     */
    private void handleSubscriber(Object data, @Nullable String tag, Object subscriber) {
        Annotation annotation;
        //获取所有方法
        Method[] declaredMethods = subscriber.getClass().getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            //获取到
            annotation = declaredMethod.getAnnotation(tag == null ? RxEvent.class : RxEventWithTag.class);
            if (annotation != null) {
                try {
                    if (tag == null || TextUtils.equals(tag, ((RxEventWithTag) annotation).value())) {
                        declaredMethod.invoke(subscriber, data);
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

    /**
     * {@link #processChain(Function, String)}
     * 用于Function的返回参数-->因为返回参数不能为空,所以,外面包裹一层之后再返回,就不会报错了
     */
    public static final class Result {
        Object data;

        public Result() {
        }

        public Result(Object data) {
            this.data = data;
        }

       public void setData(Object data){
            this.data  = data;
       }
    }

    public abstract static class RxBusFunction implements Function<String, Result> {
        Result result = new Result();

        @Override
        public Result apply(String s) throws Exception {
            realApply(s,result);
            return result;
        }

        protected abstract void realApply(String s, Result result);

    }

}
