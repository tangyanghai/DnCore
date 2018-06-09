package com.example.corelibrary.net;

import com.example.corelibrary.net.callback.IError;
import com.example.corelibrary.net.callback.IFailure;
import com.example.corelibrary.net.callback.IRequest;
import com.example.corelibrary.net.callback.ISuccess;
import com.example.corelibrary.net.callback.RequestCallBack;

import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * @author : Administrator
 * @time : 11:00
 * @for : 请求实际发生的类
 */
public class RestClient {

    //网络请求的参数
    private final String URL;
    private final HashMap<String, Object> PARAMS;
    private final RequestBody BODY;

    //回调
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    private final HttpMethod METHOD;
    protected RestClient(String URL, HashMap<String, Object> PARAMS,
                         RequestBody BODY, IRequest REQUEST,
                         ISuccess SUCCESS, IFailure FAILURE,
                         IError ERROR,HttpMethod METHOD) {
        this.URL = URL;
        this.PARAMS = PARAMS;
        this.BODY = BODY;
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
        this.METHOD = METHOD;
    }

    /**
     * 生成builder
     */
    private static RestCreatorBuilder create() {
        return new RestCreatorBuilder();
    }

    public  static RestCreatorBuilder get(String url){
         return create().method(HttpMethod.GET).url(url);
    }

    public static RestCreatorBuilder post(String url){
        return create().method(HttpMethod.POST).url(url);
    }

    public static RestCreatorBuilder put(String url){
        return create().method(HttpMethod.PUT).url(url);

    }

    public static RestCreatorBuilder delete(String url){
        return create().method(HttpMethod.DELETE).url(url);
    }

    /**
     * 真正执行网络请求的地方
     */
    protected void excute(){
        RestService service = RestCreator.getServcie();
        Call call = null;
        switch (METHOD){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
        }

        if (call!=null) {
            if (REQUEST!=null) {
                REQUEST.onStart();
            }
            call.enqueue(new RequestCallBack(REQUEST, SUCCESS,FAILURE,ERROR));
        }
    }

}
