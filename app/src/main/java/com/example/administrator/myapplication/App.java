package com.example.administrator.myapplication;

import android.app.Application;

import com.example.corelibrary.app.ProjectInit;
import com.example.corelibrary.net.RestCreator;

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
                .configure()
        ;
    }
}
