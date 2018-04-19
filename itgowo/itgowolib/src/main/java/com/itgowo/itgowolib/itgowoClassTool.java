package com.itgowo.itgowolib;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class itgowoClassTool {
    /**
     * 获取创建对象时的泛型
     * 只允许是实现interface的对象  例如
     * 假设A是class，B是interface
     * 匿名类 new B<Entity>{}
     * public class A implements B<Entity>{}
     *
     * @param listener
     * @return
     */
    public static Type getObjectParameterizedType(Object listener) {
        ParameterizedType parameterizedType;
        try {
            parameterizedType = (ParameterizedType) listener.getClass().getGenericInterfaces()[0];
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                return actualTypeArgument;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
