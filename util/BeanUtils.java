
package com.hyc.www.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static com.hyc.www.util.ReflectUtils.getFields;
import static com.hyc.www.util.ReflectUtils.getMethods;
import static com.hyc.www.util.StringUtils.toLegalText;

/**
 * 用于将请求参数映射为Javabean对象
 */
public class BeanUtils {

    /**
     * 负责将request中的parameterMap映射成为一个对象
     */
    public static Object toObject(Map<String, String[]> map, Class clazz) {

        LinkedList<Method>methods=null;
        LinkedList<Field>fields=null;
        Object obj;
        try {
            obj = clazz.newInstance();
        methods=getMethods(obj);
        fields = getFields(obj);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("无法实例化类：" + clazz.getName());
        }


        for (Field f : fields) {
            Constructor cons = null;
            try {
                cons = f.getType().getConstructor(String.class);
            } catch (Exception e) {
                System.out.println("该变量没有String类型参数的构造器"+f.getName());
            }
            String[] param = map.get(f.getName());
            if (param != null && param[0] != null) {
                Object value = null;
                try {
                    if (cons != null) {
                        param[0]=new String (param[0].getBytes(), StandardCharsets.UTF_8);
                        param[0]=toLegalText(param[0]);
                        value = cons.newInstance(param[0]);
                    }
                    for (Method m : methods) {

                        if (m.getName().equalsIgnoreCase(StringUtils.field2SetMethod(f.getName()))) {
                            //TODO
                            System.out.println("初始化属性："+f.getName()+"属性值："+value);
                            m.invoke(obj, value);
                        }
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    System.out.println("无法初始化该属性: "+f.getName()+" 属性值："+param[0]);
                }
            }
        }
        return obj;
    }


}
