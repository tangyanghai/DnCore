package com.example.corelibrary.app;

import android.content.Context;

import java.util.HashMap;

/**
 * @author : Administrator
 * @time : 9:37
 * @for : 工程配置--给外部用户使用
 */
public class ProjectInit {
    /**
     * 初始化工程
     * 调用之后,继续调用{@link Configurator}的相关配置方法,配置全局变量
     */
    public static Configurator init(Context context) {
        HashMap<Object, Object> configs = Configurator
                .getInstance()
                .withApiTimeOut(30)
                .withAppContext(context)
                .getCONFIGS();
//                configs.put(ConfigKeys.API_COMMEN_PARAMS,null);
        return Configurator.getInstance();
    }


    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfigure(Object o) {
        return getConfigurator().getConfigurator(o);
    }

    public static <T> T getConfigure(Object o, T defValue) {
        T t = getConfigurator().getConfigurator(o);
        if (t == null) {
            t = defValue;
        }
        return t;
    }


    public static Context getApplicationContext() {
        return getConfigure(ConfigKeys.APPLICATION_CONTEXT);
    }

}
