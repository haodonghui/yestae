package com.yestae.tools;

import org.slf4j.Logger;

public class LogUtilTest {
private static Logger log = LogUtil.get();
	
	public static void main(String[] args) {
		//第一种使用方法（效率低）
		LogUtil.debug("我是一条debug消息");
		
		//第二种使用方法
		LogUtil.debug(log, "我是一条debug消息 {} {}", "参数1", "参数2");
		
		RuntimeException e = new RuntimeException("错误");
		
		//第一种使用方法（效率低）
		LogUtil.error("我是一条error消息");
		
		//第二种使用方法
		LogUtil.error(log, e, "<-异常对象放前面, 我是一条带参数的error消息 {} {}", "参数1", "参数2");
	}
}
