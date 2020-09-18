package com.bw.jdbc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 实现增删改查的基类
 *
 * @param <T>
 */
public class BaseDao<T>{
    /**
     * 创建一个PreparedStament的空对象
     */
    private PreparedStatement pst = null;
    /**
     * 创建一个ResultSet的空对象
     */
    private ResultSet rs = null;
    /**
     * 得到数据库具体对象封装的类实例
     */
    private DBUtils dbUtils = new DBUtils();
    /**
     * 根据数据库类实例对象获取数据库连接
     */
    private Connection conn = dbUtils.connection;
    
    /**
     * 新增对象到对应的数据库表
     * @param t
     * @return
     */
    public int insert(T t) {
        Class<T> clz = (Class<T>)t.getClass();
        StringBuffer sql = new StringBuffer();
        SQLMapping.insertSql(clz,sql);
        try{
            pst = dbUtils.preparedStatement(sql.toString());
            BeanUtils.setPstObject(t, pst);
            return pst.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                dbUtils.close(pst);
                dbUtils.close(conn);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 根据指定对象删除对应表数据
     * @param t
     * @return
     */
    public int delObject(T t){
        Class<T> clz = (Class<T>)t.getClass();
        StringBuffer sql = new StringBuffer();
        SQLMapping.delSql(clz,sql);
        try {
            pst = dbUtils.preparedStatement(sql.toString());
            BeanUtils.setPstObject(t, pst);
            return pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                dbUtils.close(pst);
                dbUtils.close(conn);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return 0;
    }
    /**
     * 根据指定对象修改对应表数据
     * @param t
     * @return
     */
    public int update(T t){
        StringBuffer sb = new StringBuffer();
        SQLMapping.updateSql(t, sb);
        try {
            pst = dbUtils.preparedStatement(sb.toString());
            BeanUtils.setPstObject(t, pst);
            return pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtils.close(pst);
                dbUtils.close(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    
    /**
     * 根据指定条件查询
     * 
     * @param clz
     * @param map
     * @return
     */
    public T queryObject(Class<T> clz,Map<Object,Object> map){
        StringBuffer sql = new StringBuffer();
        T t = null;
        try{
            SQLMapping.querySql(clz, sql,map.keySet());
            pst = dbUtils.preparedStatement(sql.toString());
            BeanUtils.setPstObject(pst,map);
            rs = pst.executeQuery();
            while(rs.next()){
                t = BeanUtils.setObject(clz,rs);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                dbUtils.close(rs);
                dbUtils.close(pst);
                dbUtils.close(conn);
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return t;
    }
    
}