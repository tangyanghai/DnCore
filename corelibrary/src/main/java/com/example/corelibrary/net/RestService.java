package com.example.corelibrary.net;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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
public interface RestService {
    @GET
    Call<String> get(@Url String url, @QueryMap HashMap<String, Object> map);

    @FormUrlEncoded
    @POST
    Call<String> post(@Url String url, @FieldMap HashMap<String, Object> map);

    @FormUrlEncoded
    @PUT
    Call<String> put(@Url String url,@FieldMap HashMap<String,Object> map);

    @DELETE
    Call<String> delete(@Url String url,@QueryMap HashMap<String,Object> map);

    /**
     * 下载
     * 直接到内存的,所以需要Streaming
     */
    @Streaming
    @GET
    Call<String> download(@Url String url,@QueryMap HashMap<String,Object> map);

    /**
     * 上传
     */
    @Multipart
    @POST
    Call<String> upload(@Url String url, @Part MultipartBody.Part file);


    /**
     * 原始数据
     */
    @POST
    Call<String> postRaw(@Url String url, @Body RequestBody body);

    /**
     * 原始数据
     */
    @PUT
    Call<String> putRaw(@Url String url,@Body RequestBody body);

}
