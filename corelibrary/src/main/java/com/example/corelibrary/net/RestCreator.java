package com.example.corelibrary.net;

import android.content.Context;

import com.example.corelibrary.app.ConfigKeys;
import com.example.corelibrary.app.ProjectInit;
import com.example.corelibrary.net.rx.RxRestService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author : Administrator
 * @time : 9:39
 * @for : 全局变量创建者
 */
public class RestCreator {



    /**
     * 产生一个全局的Retrofit客户端
     */
    private static class RetrofitHoler {
        private static final Retrofit RETROFIT = new Retrofit.Builder()
                .baseUrl(ProjectInit.<String>getConfigure(ConfigKeys.API_HOST))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
    }


    /**
     * 产生一个全局的OkHttpClient,
     * 可以取全局变量{@link ProjectInit#init(Context)}的时候设置的参数进行配置
     */
    private static class OkHttpHolder {
        private static final OkHttpClient CLIENT =
                new OkHttpClient
                        .Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .build();
    }

    /**
     * 产生一个全局的 RestService
     */
    private static class ServiceHolder{
        private static final RestService SERVICE =
            getRetrofit().create(RestService.class);
    }

    private static class RxServiceHolder{
        private static final RxRestService RX_REST_SERVICE = getRetrofit().create(RxRestService.class);
    }

    /**
     * @return 全局的OkHttpClient
     */
    public static OkHttpClient getOkHttpClient(){
        return OkHttpHolder.CLIENT;
    }

    /**
     * @return 全局的Retrofit客户端
     */
    public static Retrofit getRetrofit(){
        return RetrofitHoler.RETROFIT;
    }

    /**
     * @return 全局的RestService
     */
    public static RestService getServcie(){
        return ServiceHolder.SERVICE;
    }

    /**
     * @return 全局的RxRestService
     */
    public static RxRestService getRxServcie() {
        return RxServiceHolder.RX_REST_SERVICE;
    }


}
