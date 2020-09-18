package com.bw.jdbc.util;

import java.lang.reflect.Field;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.bw.jdbc.annotation.Column;

/**
 * 该类实现数据库查询结果集，封装成对象结果
 * @author facebook
 *
 */
public final class BeanUtils{
    /**
     * 封装对象
     * @param clz
     * @param rs
     * @return
     * @throws Exception
     */
    public static <T>T setObject(Class<T> clz,ResultSet rs) throws Exception{
        T t = clz.newInstance();
        for(Field field : clz.getDeclaredFields()){
            field.setAccessible(true);
            if(field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                field.set(t, rs.getObject(column.name()));
            }
        }
        return t;
    }
    /**
     * 替换PreparedStatement对象的参数
     * @param pst
     * @param map
     * @throws SQLException
     */
    public static void setPstObject(PreparedStatement pst,Map<Object,Object> map) throws SQLException{
        int i =1 ;
        for(Map.Entry<Object, Object> entrySet : map.entrySet()){
            pst.setObject(i++, entrySet.getValue());
        }
    }

    /**
     * 替换 增、删、改 传入的 PreparedStatement 对象的 参数
     * @param t
     * @param sb
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public static <T>void setPstObject(T t,PreparedStatement pst) throws Exception{
        //得到preparedstatement对象的参数列表
        ParameterMetaData paramMetaData= pst.getParameterMetaData();
        int paramCount = paramMetaData.getParameterCount();
        Class<T> clz = (Class<T>) t.getClass();
        int i = 1;
        Object obj = null;
        for (Field field : clz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                pst.setObject(i++, field.get(t));
            }
        }
        //判断i是否等于pst的最大参数，如果是，那么替换pst中对应i位置的参数
        //这是为了区分新增与修改的参数替换
        //如果不满足下边的条件，说明不是新增，满足则是数据新增
        if(paramCount==i){
            pst.setObject(i, obj);
        }
    }
}
