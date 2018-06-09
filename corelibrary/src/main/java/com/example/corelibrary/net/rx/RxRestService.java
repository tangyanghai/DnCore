package com.example.corelibrary.net.rx;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author : Administrator
 * @time : 10:55
 * @for :
 */
public interface RxRestService {
    @GET
    Observable<String> get(@Url String url, @QueryMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap HashMap<String, Object> map);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap HashMap<String, Object> map);

    /**
     * 下载
     * 直接到内存的,所以需要Streaming
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url, @QueryMap HashMap<String, Object> map);

    /**
     * 上传
     */
    @Multipart
    @POST
    Observable<String> upload(@Url String url, @Part MultipartBody.Part file);


    /**
     * 原始数据
     */
    @POST
    Observable<String> postRaw(@Url String url, @Body RequestBody body);

    /**
     * 原始数据
     */
    @PUT
    Observable<String> putRaw(@Url String url, @Body RequestBody body);

}
