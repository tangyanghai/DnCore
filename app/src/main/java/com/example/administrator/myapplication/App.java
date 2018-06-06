package com.example.administrator.myapplication;

import android.app.Application;

import com.example.corelibrary.app.ProjectInit;

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
                .configure();
    }
}
