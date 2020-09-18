package com.bw.jdbc.util;

public class Constants {

	public static final String JDBC_DRIVER = PropertUtils.getValueByKey("jdbc.driver");
    public static final String JDBC_URL = PropertUtils.getValueByKey("jdbc.url");
    public static final String JDBC_USER = PropertUtils.getValueByKey("jdbc.user");
    public static final String JDBC_PASSWORD = PropertUtils.getValueByKey("jdbc.password");
}
