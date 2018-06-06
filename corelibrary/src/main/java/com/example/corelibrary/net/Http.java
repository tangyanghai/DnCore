package com.example.corelibrary.net;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.example.corelibrary.app.ConfigKeys;
import com.example.corelibrary.app.ProjectInit;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * @author : Administrator
 * @time : 11:19
 * @for :
 */
public class Http {
    public static final String TAG = "Http";
    private final Retrofit retrofit;
    private final RestService service;
    private static Gson gson;

    private Http() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(ProjectInit.<String>getConfigure(ConfigKeys.API_HOST.name()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        service = retrofit.create(RestService.class);
        gson = new Gson();
    }

    static class Holder {
        static Http instance = new Http();
    }

    private static RestService getRestService() {
        return Holder.instance.service;
    }

    public static Request get(String path) {
        return new Request(HttpMethod.GET).setPath(path);
    }

    public static class Request {
        //请求方式
        HttpMethod method;
        //地址
        String path;
        //参数
        HashMap<String, Object> map;

        private Request(HttpMethod method) {
            this.method = method;
            map = new HashMap<>();
        }

        public Request setPath(String path) {
            this.path = path;
            return this;
        }

        /**
         * 添加一组参数
         */
        public Request setParams(HashMap<String, Object> map) {
            this.map.putAll(map);
            return this;
        }

        /**
         * 添加一个参数
         */
        public Request add(String key, Object value) {
            map.put(key, value);
            return this;
        }

        /**
         * 开始请求
         */
        public <T> void excute(final CallBack<T> listener) {
            Call<String> call = getService();
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    handleResponse(response, listener);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.i(TAG, "onFailure: -->" + t.toString());
                }
            });
        }

        /**
         * 请求成功-->成功连接到服务器并收到服务器数据
         */
        private <T> void handleResponse(Response<String> response, CallBack<T> listener) {
            if (response == null) {
                listener.onFailed(-1, "数据异常");
                return;
            }
            String body = response.body();
            Log.i(TAG, "onResponse: -->" + body);
            if (body == null) {
                listener.onFailed(-1, "数据异常");
                return;
            }
            if (listener != null) {
                try {

                    T t = gson.fromJson(body, listener.getClassType());
//                    T t = JSONObject.parseObject(body, listener.getClassType());
                    listener.onSucceed(t);
                } catch (Exception e) {
                    listener.onFailed(-1, "数据异常");
                    e.printStackTrace();
                }
            }
        }

        /**
         * @return 对应类型的call
         */
        private Call<String> getService() {
            Call<String> call;
            switch (method) {
                case POST:
                    return getRestService().post(path, map);
                case GET:
                    return getRestService().get(path, map);
                case PUT:
                case DELETE:
                case UPLOAD:
                case PUT_RAW:
                case DOWNLOAD:
                case POST_RAW:
                    break;
            }
            return null;
        }

    }

    /**
     * 请求回调
     */
    public static abstract class CallBack<T> {
        Class<T> cls;

        public CallBack(Class<T> cls) {
            this.cls = cls;
        }

        public CallBack() {
        }

        private Type getClassType() {
            if (cls != null) {
                return cls;
            }
            Method[] declaredMethods = getClass().getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                if (declaredMethod.getName().equals("onSucceed")) {
                    Type[] genericParameterTypes = declaredMethod.getGenericParameterTypes();
                    return genericParameterTypes[0];
                } else {
                    continue;
                }
            }
            return null;
        }

        public abstract void onSucceed(T t);

        public abstract void onFailed(int errCode, String reson);
    }

    public static class Convert<T> implements IConvert<T> {
        public <T> Type getType() {
            Type genType = getClass().getGenericSuperclass();
            return ((ParameterizedType) genType).getActualTypeArguments()[0];
        }
    }

    public interface IConvert<T> {
        <T> Type getType();
    }


}
