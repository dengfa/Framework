/**
 * Copyright (C) 2015 The AndroidSupport Project
 */
package com.hyena.framework.debug;

import com.hyena.framework.clientlog.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射相关控制类
 * @author yangzc on 15/9/7.
 */
public class InvokeHelper {

    /*
     * 获得字段值
     */
    public static Object getFieldValue(Object object, String name) {
        try {
            Field field = getDeclaredField(object.getClass(), name);
            if(field != null) {
                field.setAccessible(true);
                return field.get(object);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 设置字段值
     */
    public static void setFieldValue(Object object, String name, Object value){
        try {
            Field field = getDeclaredField(object.getClass(), name);
            if(field != null) {
                field.setAccessible(true);
                field.set(object, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /*
     * 设置字段值
     */
    public static void setFieldValue(Class clz, Object object, String name, Object value){
        try {
            Field field = getDeclaredField(clz, name);
            if(field != null) {
                field.setAccessible(true);
                field.set(object, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /*
     * 获得参数Field
     */
    public static Field getDeclaredField(Class<?> cls, String fieldName){
        try {
            Field field = cls.getDeclaredField(fieldName);
            return field;
        } catch (NoSuchFieldException e) {
            if(cls.getSuperclass() != null) {
                return getDeclaredField(cls.getSuperclass(), fieldName);
            }
        }
        return null;
    }

    /*
     * 反射调用无参数的方法
     */
    public static Object invokeMethod(Object obj, String methodName){
        return invokeMethod(obj.getClass(), obj, methodName);
    }

    /*
     * 反射调用无参数的方法
     */
    public static Object invokeMethod(Class<?> clazz, Object obj, String methodName){
        return invokeMethod(clazz, obj, methodName, new Class[]{}, new Object[]{});
    }

    /*
     * 反射调用带参数方法
     */
    public static Object invokeMethod(Object obj, String methodName, Class<?>[] valueType, Object[] values) {
        return invokeMethod(obj.getClass(), obj, methodName, valueType, values);
    }

    /*
     * 反射调用带参数的方法
     */
    public static Object invokeMethod(Class<?> clazz, Object obj, String methodName, Class<?>[] valueType, Object[] values) {
        return invokeMethodImpl(clazz, obj, methodName, valueType, values);
    }

    /*
     * 递归找方法进行invoke
     */
    private static Object invokeMethodImpl(Class<?> clazz, Object obj, String methodName, Class<?>[] valueType, Object[] values) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, valueType);
            method.setAccessible(true);
            return method.invoke(obj, values);
        } catch (NoSuchMethodException e) {
            if(clazz.getSuperclass() != null) {
                invokeMethodImpl(clazz.getSuperclass(), obj, methodName, valueType, values);
            } else {
                e.printStackTrace();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 通过反射获取对象中得Field Object
     */
    public static Object getObjectFieldValue(Class<?> clazz, Object obj, String fieldName){
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 通过反射获取对象中得Field int
     */
    public static int getIntFieldValue(Class<?> clazz, Object obj, String fieldName){
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.getInt(obj);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /*
     * 获得Field Type
     */
    public static Class<?> getObjectType(Class<?> clazz, String fieldName){
        try {
            Field field = clazz.getDeclaredField(fieldName);
            return field.getType();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 构造新对象
     */
    public static Object newInstanceObject(Class<?> clazz){
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Method getClassMethodWithParamCnt(Class<?> clazz, String methodName, int paramCnt){
        Method methods[] = clazz.getDeclaredMethods();
        for(Method method: methods){
            String name = method.getName();
            Class<?> types[] = method.getParameterTypes();
            int typeCnt = 0;
            if(types != null){
                typeCnt = types.length;
            }
            if(name.equalsIgnoreCase(methodName) && typeCnt == paramCnt){
                return method;
            }
        }
        return null;
    }

    public static void printMethod(Class<?> clazz){
        Method methods[] = clazz.getDeclaredMethods();
        for(Method method: methods){
            StringBuffer buffer = new StringBuffer();
            String name = method.getName();
            Class<?> types[] = method.getParameterTypes();
            buffer.append("method: " + name + "(");

            for(Class<?> type: types){
                buffer.append(type.getName() + ",");
            }
            buffer.append(")");
            LogUtil.v("debug", buffer.toString());
        }
    }
}
