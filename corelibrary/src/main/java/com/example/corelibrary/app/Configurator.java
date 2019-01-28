package com.example.corelibrary.app;

import android.content.Context;

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

    private static HashMap<String, Object> CONFIGS = new HashMap<>();
    private static List<Interceptor> INTERCEPTERS = new ArrayList<>();
    private HashMap<String, Object> api_comment_params = new HashMap<>();

    //<editor-fold desc="全局配置单例">
    private Configurator() {
        CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);
        CONFIGS.put(ConfigKeys.API_TIMEOUT.name(),30L);
    }


    private static class Holder {
        private static Configurator instance = new Configurator();
    }

    public static Configurator getInstance() {
        return Holder.instance;
    }
    //</editor-fold>

    //<editor-fold desc="配置流程">

    public final Configurator withAppContext(Context context) {
        CONFIGS.put(ConfigKeys.APPLICATION_CONTEXT.name(), context);
        return this;
    }

    /**
     * 配置host
     */
    public final Configurator withApiHost(String host) {
        CONFIGS.put(ConfigKeys.API_HOST.name(), host);
        return this;
    }

    /**
     * 配置网络请求重试次数--还未完成
     */
    public Configurator withApiRetry(int retryTimes) {
        CONFIGS.put(ConfigKeys.API_RETRY_TIMES.name(), retryTimes);
        return this;
    }

    /**
     * 配置网络请求拦截器
     */
    public final Configurator withApiIntercepters(ArrayList<Interceptor> intercepters) {
        INTERCEPTERS.addAll(intercepters);
        CONFIGS.put(ConfigKeys.API_INTERCEPTER.name(), INTERCEPTERS);
        return this;
    }

    /**
     * 配置单独的网络请求拦截器
     */
    public final Configurator withApiInterceptor(Interceptor interceptor) {
        INTERCEPTERS.add(interceptor);
        CONFIGS.put(ConfigKeys.API_INTERCEPTER.name(), INTERCEPTERS);
        return this;
    }

    /**
     * 配置网络请求超时时间
     */
    public final Configurator withApiTimeOut(long timeOut) {
        CONFIGS.put(ConfigKeys.API_TIMEOUT.name(), timeOut);
        return this;
    }

    /**
     * 配置网络请求共同参数--共同的
     */
    public final Configurator withApiCommenParams(HashMap<String, Object> commenParams) {
        api_comment_params.putAll(commenParams);
        CONFIGS.put(ConfigKeys.API_COMMEN_PARAMS.name(), api_comment_params);
        return this;
    }

    /**
     * 配置网络请求共同参数--单独的
     */
    public final Configurator withApiCommenParam(String key, Object value) {
        if (key != null) {
            api_comment_params.put(key, value);
            CONFIGS.put(ConfigKeys.API_COMMEN_PARAMS.name(), api_comment_params);
        }
        return this;
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
    final HashMap<String, Object> getCONFIGS() {
        return CONFIGS;
    }

    /**
     * @return 网络请求全局属性
     */
    public HashMap<String, Object> getApiCommonParams() {
        return api_comment_params;
    }

    /**
     * @return 网络请求拦截器
     */
    public List<Interceptor> getApiInterceptors() {
        return INTERCEPTERS;
    }

    /**
     * @return 网络请求超时时间
     */
    public long getTimeOut() {
        return (long) CONFIGS.get(ConfigKeys.API_TIMEOUT.name());
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
