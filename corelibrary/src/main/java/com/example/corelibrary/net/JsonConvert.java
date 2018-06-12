package com.example.corelibrary.net;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @author : tyh
 * @time : 2018/06/11
 * @for :
 */
public class JsonConvert<T> implements IConvert<T> {

    private static class GsonHolder {
        private static Gson gson = new Gson();
    }

    Type type;

    public JsonConvert(Type cls) {
        this.type = cls;
    }

    @Override
    public T convert(String content) {
        // 使用Gson
//        return GsonHolder.gson.fromJson(content,type);
        //使用fastJson
        return JSON.parseObject(content, type);
    }
}
