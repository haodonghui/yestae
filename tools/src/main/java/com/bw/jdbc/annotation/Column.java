package com.bw.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体类属性数据库映射标识类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Column {
    /**
     * 字段名称
     * @return
     */
    String name();
    /**
     * 字段类型
     * @return
     */
    Class<?> type();
    
}
