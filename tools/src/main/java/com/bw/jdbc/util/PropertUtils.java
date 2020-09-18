package com.bw.jdbc.util;

import java.util.ResourceBundle;

public class PropertUtils {

	 static ResourceBundle resource;
     static{
             resource = ResourceBundle.getBundle("jdbc");
     }
     public static String getValueByKey(String key){
             return resource.getString(key);
     }
}
