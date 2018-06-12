package com.example.corelibrary.rxbus;

import android.support.annotation.Nullable;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : tyh
 * @time : 2018/06/12
 * @for : 带有tag标记的事件
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RxEventWithTag {
    String value();
}
