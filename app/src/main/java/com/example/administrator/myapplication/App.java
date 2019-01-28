package com.example.administrator.myapplication;

import android.app.Application;

import com.example.corelibrary.app.ProjectInit;
import com.example.corelibrary.net.interceptor.HttpLoggingInterceptor;
import com.example.corelibrary.net.interceptor.RetryInterceptor;

/**
 * @author : Administrator
 * @time : 10:36
 * @for :
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ProjectInit.init(this)
                .withApiHost("http://api.avatardata.cn/")
                .withApiTimeOut(60)
                //日志打印拦截器
                .withApiInterceptor(new HttpLoggingInterceptor("==网络日志=="))
                //重试拦截器
                .withApiInterceptor(new RetryInterceptor(3))
                .configure()
        ;
    }
}
