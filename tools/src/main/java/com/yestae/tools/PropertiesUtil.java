package com.yestae.tools;

import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 属性文件工具类
 * 
 * @author yuhang
 *
 */
public class PropertiesUtil {
	
	//属性文件文件名为key,属性文件的内容为value
	private static ConcurrentHashMap<String,Map<String,String>> allProperties =new ConcurrentHashMap<String,Map<String,String>>();
	
	/**
	 * 获取properties文件中的key的value值
	 * 
	 * @param key 属性文件中的key
	 * @param filePath 属性文件的相对文件名,此属性文件在classpath下,如："/redisConfig.properties"
	 * 
	 * @return
	 */
	public static String getProperty(String key,String filePath){
		
		String returnValue=null;
		
		if(allProperties.containsKey(filePath)){
			
			Map<String,String> filePropertiesMap = allProperties.get(filePath);
			returnValue = filePropertiesMap.get(key);
		}else{
			
			Map<String,String> valueMap=new HashMap<String,String>();
			InputStreamReader inputStream =null;
			try {
				Properties prop = new Properties();
				inputStream = new InputStreamReader(PropertiesUtil.class.getResourceAsStream(filePath),"UTF-8");
				prop.load(inputStream);
				
				Enumeration<?> enum1 = prop.propertyNames();//得到配置文件的名字
				 while(enum1.hasMoreElements()) {
		             String strKey = (String) enum1.nextElement();
		             String strValue = prop.getProperty(strKey);
		             valueMap.put(strKey, strValue);
		         }
				 
				 allProperties.put(filePath,valueMap);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(null!=inputStream){
					try {
						inputStream.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			
			}
			
			Map<String,String> filePropertiesMap = allProperties.get(filePath);
			returnValue = filePropertiesMap.get(key);
		}
		
		return returnValue;
		
	}
	
	public static void main(String[] args) {
		
		String value = PropertiesUtil.getProperty("", "");
		System.out.println(value);
		
	}
	
	
}
