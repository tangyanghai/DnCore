package com.example.corelibrary.utils;

import android.widget.RelativeLayout;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 创建人: tyh
 * 创建时间: 2019/1/28
 * 描述:
 */
public class ReflectUtils {


    public static Type getClassTypeOfMethod(Class c, String methodName) {
        Method[] declaredMethods = c.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().equals(methodName)) {
                Type[] genericParameterTypes = declaredMethod.getGenericParameterTypes();
                return genericParameterTypes[0];
            } else {
                continue;
            }
        }
        return null;
    }


}
