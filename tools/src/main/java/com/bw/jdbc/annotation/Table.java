package com.bw.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 实体类映射数据库表类
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE})
public @interface Table {
	/**
	 * 用于修饰实体类，表示实体类名与数据库表名之间的映射
	 * 
	 * @return
	 */
	String table() default "";
}
