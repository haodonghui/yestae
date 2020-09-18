package com.yestae.test;

/**
 * @program: yestae-demo
 * @description:
 * @author: zouco
 * @create: 2019-08-01 10:28
 **/
public class test {

    public static void main(String[] args) {
        //反射测试
        //reflectTest();
    }

    /**
     * 反射测试
     */
    private static void reflectTest() {
        try {
            // 创建对象
            ReflectClass.reflectNewInstance();

            // 反射私有的构造方法
            ReflectClass.reflectPrivateConstructor();

            // 反射私有属性
            ReflectClass.reflectPrivateField();

            // 反射私有方法
            ReflectClass.reflectPrivateMethod();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("------------------------");
    }
}
