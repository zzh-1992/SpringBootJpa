/*
 *Copyright @2021 Grapefruit. All rights reserved.
 */

package com.grapefruit.springbootjpa.controller;

import java.lang.reflect.Field;

/**
 * 相关描述
 *
 * @author zhihuangzhang
 * @version 1.0
 * @date 2021-10-10 8:46 上午
 */
public class BeanCopyUtil {
    public static void copyProperties(Object source, Object target) {
        try {
            Class<?> sourceClass = source.getClass();
            Field[] declaredFields = sourceClass.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(source);
                if (value != null) {
                    // 如果该字段有值,就将其保存到目标对象中
                    field.set(target, value);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
