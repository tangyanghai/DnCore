package com.example.corelibrary.net.rx;

import com.example.corelibrary.net.HttpMethod;
import com.example.corelibrary.net.RestCreator;
import com.example.corelibrary.net.RestCreatorBuilder;
import com.example.corelibrary.net.RestService;
import com.example.corelibrary.net.callback.IError;
import com.example.corelibrary.net.callback.IFailure;
import com.example.corelibrary.net.callback.IRequest;
import com.example.corelibrary.net.callback.ISuccess;
import com.example.corelibrary.net.callback.RequestCallBack;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author : Administrator
 * @time : 11:00
 * @for : 请求实际发生的类
 */
public class RxRestClient {

    //网络请求的参数
    private final String URL;
    private final HashMap<String, Object> PARAMS;
    private final RequestBody BODY;

    private final HttpMethod METHOD;

    protected RxRestClient(String URL, HashMap<String, Object> PARAMS,
                           RequestBody BODY, HttpMethod METHOD) {
        this.URL = URL;
        this.PARAMS = PARAMS;
        this.BODY = BODY;

        this.METHOD = METHOD;
    }

    /**
     * 生成builder
     */
    private static RxRestCreatorBuilder create() {
        return new RxRestCreatorBuilder();
    }

    public static RxRestCreatorBuilder get(String url) {
        return create().method(HttpMethod.GET).url(url);
    }

    public static RxRestCreatorBuilder post(String url) {
        return create().method(HttpMethod.POST).url(url);
    }

    public static RxRestCreatorBuilder put(String url) {
        return create().method(HttpMethod.PUT).url(url);

    }

    public static RxRestCreatorBuilder delete(String url) {
        return create().method(HttpMethod.DELETE).url(url);
    }

    /**
     * 真正执行网络请求的地方
     */
    protected Observable<String> excute() {
        RxRestService service = RestCreator.getRxServcie();
        Observable<String> observable = null;

        switch (METHOD) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
        }
        //如果都不是,就直接返回一个发送空值的Observable
        return observable;
    }

}
