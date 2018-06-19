package com.example.corelibrary.net;

import android.content.Context;

import com.example.corelibrary.app.ConfigKeys;
import com.example.corelibrary.app.Configurator;
import com.example.corelibrary.app.ProjectInit;
import com.example.corelibrary.net.rx.RxRestService;
import com.example.corelibrary.utils.HttpsUtils;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
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
                        .build();
    }

    /**
     * 产生一个全局的 RestService
     */
    private static class ServiceHolder {
        private static final RestService SERVICE =
                getRetrofit().create(RestService.class);
    }

    private static class RxServiceHolder {
        private static final RxRestService RX_REST_SERVICE =
                getRetrofit().create(RxRestService.class);
    }

    /**
     * @return 全局的OkHttpClient
     */
    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = OkHttpHolder.CLIENT.newBuilder();

        //设置拦截器
        List<Interceptor> interceptors = Configurator.getInstance().getApiInterceptors();
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        //设置超时时间
        long timeOut = Configurator.getInstance().getTimeOut();
        builder.connectTimeout(timeOut, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);

        //设置https
        //        //https相关设置，以下几种方案根据需要自己设置
        //        //方法一：信任所有证书,不安全有风险

        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //        //方法二：自定义信任规则，校验服务端证书
        //        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"),
        // "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//                builder.hostnameVerifier(new SafeHostnameVerifier());

        return builder.build();
    }

    /**
     * @return 全局的Retrofit客户端
     */
    public static Retrofit getRetrofit() {
        return RetrofitHoler.RETROFIT;
    }

    /**
     * @return 全局的RestService
     */
    public static RestService getServcie() {
        return ServiceHolder.SERVICE;
    }

    /**
     * @return 全局的RxRestService
     */
    public static RxRestService getRxServcie() {
        return RxServiceHolder.RX_REST_SERVICE;
    }


    /**
     * 包装全局属性
     */
    public static HashMap<String, Object> creatParams(HashMap<String, Object> params) {

        HashMap<String, Object> common_params = Configurator.getInstance().getApiCommonParams();

        if (common_params != null) {
            if (params == null) {
                return common_params;
            }

            params.putAll(common_params);
        }
        return params;
    }

}
