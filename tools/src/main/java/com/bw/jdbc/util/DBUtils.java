package com.bw.jdbc.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils{
	
	
    public Connection connection ;
    
    /**
     * 类加载时加载数据库驱动
     */
    static{
        try {
            Class.forName(Constants.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取连接
     * @return
     */
    public DBUtils(){
         try {
        	 connection = DriverManager.getConnection(Constants.JDBC_URL, Constants.JDBC_USER, Constants.JDBC_PASSWORD);
         } catch (SQLException e) {
             e.printStackTrace();
         }
    	
    }
    /**
     * 创建Statement对象
     * @return
     * @throws SQLException
     */
    public  Statement statement() throws SQLException{
        return connection.createStatement();
    }
    /**
     * 创建PreparedStatement对象
     * @param sql
     * @return
     * @throws SQLException
     */
    public  PreparedStatement preparedStatement(String sql) throws SQLException{
        return connection.prepareStatement(sql);
    }
   
    /**
     * 创建ResultSet对象
     * @param sql
     * @return
     * @throws SQLException
     */
    public  ResultSet resultSet(String sql) throws SQLException{
        return preparedStatement(sql).executeQuery();
    }

    /**
     * 关闭使用对象
     * @param obj
     * @throws SQLException
     */
    public  void close(Object obj) throws SQLException{
        if(obj==null){
            return;
        }
        if(obj instanceof ResultSet){
            ((ResultSet) obj).close();
        }
        if(obj instanceof PreparedStatement){
            ((PreparedStatement) obj).close();
        }
        if(obj instanceof CallableStatement){
            ((CallableStatement) obj).close();
        }
    }
}
