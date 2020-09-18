package com.bw.jdbc.util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Set;

import com.bw.jdbc.annotation.Column;

/**
 * 主要实现将实体类映射成sql语句
 *
 */
public final class SQLMapping{
    /**
     * 组装新增的sql语句
     * 
     * @param clz
     * @param sb
     */
    public static <T>void insertSql(Class<T> clz,StringBuffer sb){
        sb.append(" insert into ");
        sb.append(clz.getSimpleName().toLowerCase()+"( ");
        StringBuffer sbv = new StringBuffer(" values( ");
        for(Field field : clz.getDeclaredFields()){
            if(field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                sb.append(column.name()+" , ");
                sbv.append(" ? ,");
            }
        }
        sb.delete(sb.toString().lastIndexOf(","),sb.length());
        sb.append(" ) ");

        sbv.delete(sbv.toString().lastIndexOf(","),sbv.length());
        sbv.append(" ) ");

        sb.append(sbv);
    }

    /**
     * 组装需要删除对象的sql
     * 
     * @param clz
     * @param sql
     */
    public static <T>void delSql(Class<T> clz,StringBuffer sql){
        sql.append(" delete from ");
        sql.append(clz.getSimpleName().toLowerCase());
        sql.append(" where 1=1 ");
        for(Field field : clz.getDeclaredFields()){
            if(field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                sql.append(" and "+column.name() + " = ? ");
            }
        }
    }
    
    /**
     * 根据传入的实体，组装修改语句
     * 
     * @param t
     * @param sql
     */
    public static <T>void updateSql(T t,StringBuffer sql){
        Class<T> clz = (Class<T>)t.getClass();
        sql.append(" update ");
        sql.append(clz.getSimpleName().toLowerCase());
        sql.append(" set ");
        for(Field field:clz.getDeclaredFields()){
            if(field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                sql.append(column.name()+" = ? , ");
            }
        }
        sql.delete(sql.toString().lastIndexOf(","), sql.length());
    }

    /**
     * 通过反射组装查询sql
     * @param clz
     * @param sb
     * @throws SecurityException 
     * @throws NoSuchFieldException 
     */
    public static <T>void querySql(Class<T> clz,StringBuffer sb,Object ... objs) throws Exception{
    	
        StringBuffer sqlb = new StringBuffer(" select ");
        for(Field field : clz.getDeclaredFields()){
            if(field.isAnnotationPresent(Column.class)){
                Column column = field.getAnnotation(Column.class);
                sqlb.append(column.name()+" , ");
            }
        }
        sqlb.delete(sqlb.toString().lastIndexOf(","), sqlb.length());
        sqlb.append("from ");
        sqlb.append(clz.getSimpleName().toLowerCase());
        sqlb.append(" where 1=1 ");
        int index=0;
        if(sb!=null){
            index = sqlb.length();
        }
        sb.insert(0, sqlb);
        Set<Object> set=null;
        if(objs.length>0){
            for(Object obj : objs){
                if(obj instanceof Set){
                    set = ( Set<Object>)obj;
                    break;
                }
            }
        }
        if(set==null){
            return;
        }
        for(Iterator<Object> it = set.iterator();it.hasNext();){
            String fieldName = (String) it.next();
            Field field = clz.getDeclaredField(fieldName);
            if(!field.isAnnotationPresent(Column.class)){
                throw new Exception("属性 '"+field.getName()+"' 没有添加映射！");
            }
            Column column = field.getAnnotation(Column.class);
            sb.insert(index," and " +column.name() + " = ? ");
        }
    }
    
    /**
     * 返回封装好的查询sql
     * @param clz
     * @param objs
     * @return
     */
    public static <T>StringBuffer querySql(Class<T> clz,Object...objs){
        StringBuffer sql = new StringBuffer();
        try{
                if(objs.length>0){
                    for(Object obj : objs){
                        if(obj instanceof StringBuffer){
                            sql = (StringBuffer)obj;
                            querySql(clz,sql);
                        }
                    }
                }else{
                    sql = new StringBuffer();
                    querySql(clz,sql);
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return sql;
    }
}
