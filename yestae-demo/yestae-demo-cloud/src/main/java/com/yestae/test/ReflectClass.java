package com.yestae.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @program: yestae-demo
 * @description:
 * @author: zouco
 * @create: 2019-08-01 10:30
 **/
public class ReflectClass {
    private static final Logger logger = LoggerFactory.getLogger(ReflectClass.class);
    private final static String TAG = "peter.log.ReflectClass";

    // 创建对象
    public static void reflectNewInstance() {
        try {
            Class<?> classBook = Class.forName("com.yestae.test.Book");
            Object objectBook = classBook.newInstance();
            Book book = (Book) objectBook;
            book.setName("Android进阶之光");
            book.setAuthor("刘望舒");
            logger.info(TAG+":{}", "reflectNewInstance book = " + book.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 反射私有的构造方法
    public static void reflectPrivateConstructor() {
        try {
            Class<?> classBook = Class.forName("com.yestae.test.Book");
            Constructor<?> declaredConstructorBook = classBook.getDeclaredConstructor(String.class, String.class);
            declaredConstructorBook.setAccessible(true);
            Object objectBook = declaredConstructorBook.newInstance("Android开发艺术探索", "任玉刚");
            Book book = (Book) objectBook;
            logger.info(TAG+":{}", "reflectPrivateConstructor book = " + book.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 反射私有属性
    public static void reflectPrivateField() {
        try {
            Class<?> classBook = Class.forName("com.yestae.test.Book");
            Object objectBook = classBook.newInstance();
            Field fieldTag = classBook.getDeclaredField("TAG");
            fieldTag.setAccessible(true);
            String tag = (String) fieldTag.get(objectBook);
            logger.info(TAG+":{}", "reflectPrivateField tag = " + tag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // 反射私有方法
    public static void reflectPrivateMethod() {
        try {
            Class<?> classBook = Class.forName("com.yestae.test.Book");
            Method methodBook = classBook.getDeclaredMethod("declaredMethod", int.class);
            methodBook.setAccessible(true);
            Object objectBook=1;
            String string ="";
            //Object objectBook = classBook.newInstance();
            //String string = (String) methodBook.invoke(objectBook, 1);
            logger.info(TAG+":{}", "reflectPrivateMethod string = " + string);
            logger.info(TAG+":{}", objectBook.getClass().isInstance("class"));
            logger.info(TAG+":{}", objectBook.getClass().getName());
            logger.info(TAG+":{}", objectBook.getClass().getTypeName());
            Class<?> classBook1 = Class.forName(objectBook.getClass().getName());
            logger.info("测试类型:{}",classBook1);


            logger.info("测试类型:{}", classBook1.getClass().getName());
            logger.info("测试类型:{}", classBook.getClass().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //// 获得系统Zenmode值
    //public static int getZenMode() {
    //    int zenMode = -1;
    //    try {
    //        Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
    //        Method mGetService = cServiceManager.getMethod("getService", String.class);
    //        Object oNotificationManagerService = mGetService.invoke(null, Context.NOTIFICATION_SERVICE);
    //        Class<?> cINotificationManagerStub = Class.forName("android.app.INotificationManager$Stub");
    //        Method mAsInterface = cINotificationManagerStub.getMethod("asInterface", IBinder.class);
    //        Object oINotificationManager = mAsInterface.invoke(null, oNotificationManagerService);
    //        Method mGetZenMode = cINotificationManagerStub.getMethod("getZenMode");
    //        zenMode = (int) mGetZenMode.invoke(oINotificationManager);
    //    } catch (Exception ex) {
    //        ex.printStackTrace();
    //    }
    //
    //    return zenMode;
    //}
    //
    //// 关闭手机
    //public static void shutDown() {
    //    try {
    //        Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
    //        Method mGetService = cServiceManager.getMethod("getService", String.class);
    //        Object oPowerManagerService = mGetService.invoke(null, Context.POWER_SERVICE);
    //        Class<?> cIPowerManagerStub = Class.forName("android.os.IPowerManager$Stub");
    //        Method mShutdown = cIPowerManagerStub.getMethod("shutdown", boolean.class, String.class, boolean.class);
    //        Method mAsInterface = cIPowerManagerStub.getMethod("asInterface", IBinder.class);
    //        Object oIPowerManager = mAsInterface.invoke(null, oPowerManagerService);
    //        mShutdown.invoke(oIPowerManager, true, null, true);
    //
    //    } catch (Exception ex) {
    //        ex.printStackTrace();
    //    }
    //}
    //
    //public static void shutdownOrReboot(final boolean shutdown, final boolean confirm) {
    //    try {
    //        Class<?> ServiceManager = Class.forName("android.os.ServiceManager");
    //        // 获得ServiceManager的getService方法
    //        Method getService = ServiceManager.getMethod("getService", java.lang.String.class);
    //        // 调用getService获取RemoteService
    //        Object oRemoteService = getService.invoke(null, Context.POWER_SERVICE);
    //        // 获得IPowerManager.Stub类
    //        Class<?> cStub = Class.forName("android.os.IPowerManager$Stub");
    //        // 获得asInterface方法
    //        Method asInterface = cStub.getMethod("asInterface", android.os.IBinder.class);
    //        // 调用asInterface方法获取IPowerManager对象
    //        Object oIPowerManager = asInterface.invoke(null, oRemoteService);
    //        if (shutdown) {
    //            // 获得shutdown()方法
    //            Method shutdownMethod = oIPowerManager.getClass().getMethod(
    //                    "shutdown", boolean.class, String.class, boolean.class);
    //            // 调用shutdown()方法
    //            shutdownMethod.invoke(oIPowerManager, confirm, null, false);
    //        } else {
    //            // 获得reboot()方法
    //            Method rebootMethod = oIPowerManager.getClass().getMethod("reboot",
    //                    boolean.class, String.class, boolean.class);
    //            // 调用reboot()方法
    //            rebootMethod.invoke(oIPowerManager, confirm, null, false);
    //        }
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}
}
