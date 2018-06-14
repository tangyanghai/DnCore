package com.example.corelibrary.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;

/**
 * @author : Administrator
 * @time : 9:51
 * @for : 全局配置--底层
 */
public class Configurator {

    private static HashMap<Object, Object> CONFIGS = new HashMap<>();
    private static List<Interceptor> INTERCEPTERS = new ArrayList<>();
    private boolean hasInterceptors = false;

    //<editor-fold desc="全局配置单例">
    private Configurator() {
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);
    }

    private static class Holder {
        private static Configurator instance = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.instance;
    }
    //</editor-fold>

    //<editor-fold desc="配置流程">

    /**
     * 配置host
     */
    public final Configurator withApiHost(String host) {
        CONFIGS.put(ConfigKeys.API_HOST, host);
        return this;
    }

    /**
     * 配置网络请求重试次数--还未完成
     */
    public Configurator withApiRetry(int retryTimes) {
        CONFIGS.put(ConfigKeys.API_RETRY_TIMES,retryTimes);
        return this;
    }

    /**
     * 配置网络请求拦截器
     */
    public final Configurator withApiIntercepters(ArrayList<Interceptor> intercepters){
        INTERCEPTERS.addAll(intercepters);
        CONFIGS.put(ConfigKeys.API_INTERCEPTER,intercepters);
        return this;
    }

    /**
     * 配置网络请求超时时间
     */
    public final Configurator withApiTimeOut(int timeOut){
        CONFIGS.put(ConfigKeys.API_TIMEOUT,timeOut);
        return this;
    }

    /**
     * 配置网络请求共同参数
     */
    public final Configurator withApiCommenParams(HashMap<String,Object> commenParams){
        CONFIGS.put(ConfigKeys.API_COMMEN_PARAMS,commenParams);
        return this;
    }

    public static boolean hasIntercepters(){
        return INTERCEPTERS.size()>0;
    }

    /**
     * 检查配置是否完成
     */
    private void checkConfigReady() {
        boolean isReady = (boolean) CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configurator is not ready, please call configure()");
        }
    }

    /**
     * 配置完成
     * 最终调用这个方法之后,全局配置才会生效
     */
    public final void configure() {
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(), true);
    }
    //</editor-fold>

    //<editor-fold desc="获取配置信息">
    final HashMap<Object, Object> getCONFIGS() {
        return CONFIGS;
    }

    final <T> T getConfigurator(Object key) {
        checkConfigReady();
        final Object o = CONFIGS.get(key);
        if (o == null) {
            throw new NullPointerException(key.toString() + " is NULL");
        }
        return (T) CONFIGS.get(key);
    }
    //</editor-fold>
}
