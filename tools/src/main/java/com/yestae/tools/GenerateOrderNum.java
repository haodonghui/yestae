package com.yestae.tools;

import com.baomidou.mybatisplus.toolkit.IdWorker;  
  
/** 
 * @ClassName: GenerateOrderNum 
 * @Description: 订单号生成工具，生成非重复订单号，理论上限1毫秒1000个，可扩展.
 * 集群部署需要传机器编号，否则传null或者""
 * 
 */  
public class GenerateOrderNum {  
    /** 
     * 锁对象，可以为任意对象 
     */  
    private static Object lockObj = "lockerOrder";  
    /** 
     * 订单号生成计数器 
     */  
    private static long orderNumCount = 0L;  
    /** 
     * 每毫秒生成订单号数量最大值 
     */  
    private int maxPerMSECSize=1000;  
    
    
    /** 
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展 ,
     * 集群部署需要传机器编号，否则传null或者""
     * @param machineNum 机器编号
     */  
    public String generateOrderNum(String machineNum) { 
    	
    	//为了暂时不动订单相关代码,暂时使用,订单业务需要重新重构订单Id(IdWork.getId())的生成
    	return generalOrderId();
//        try {  
//            // 最终生成的订单号  
//            synchronized (lockObj) {  
//                // 取系统当前时间作为订单号变量前半部分，精确到毫秒  
//                long nowLong = Long.parseLong(new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));  
//                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万  
//                if (orderNumCount >= maxPerMSECSize) {  
//                    orderNumCount = 0L;  
//                }  
//                //组装订单号  
//                StringBuffer finalOrderNum= new StringBuffer();
//                finalOrderNum.append(nowLong).append(maxPerMSECSize+orderNumCount);
//                
//                if(null!=machineNum&&machineNum.length()>0){
//                	finalOrderNum.append(machineNum);
//                }
//                
//                orderNumCount++;  
//                return finalOrderNum.toString();
//            }  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//            return null;  
//        }
    }  
    
    private String generalOrderId(){
    	Long id = IdWorker.getId();
    	return id.toString();
    }
    
    /** 
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展 ,
     * 集群部署需要传机器编号，否则传null或者""
     * @param orderPrefix 订单前缀（可以是一个字母或者一个数字,代表不同的订单）
     * @param machineNum 机器编号
     */  
    public String generateOrderNum(String orderPrefix,String machineNum) {  
    	//为了暂时不动订单相关代码,暂时使用,订单业务需要重新重构订单Id(IdWork.getId())的生成
    	return generalOrderId();
    	
//        try {  
//            // 最终生成的订单号  
//            synchronized (lockObj) {  
//                // 取系统当前时间作为订单号变量前半部分，精确到毫秒  
//                long nowLong = Long.parseLong(new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));  
//                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万  
//                if (orderNumCount >= maxPerMSECSize) {  
//                    orderNumCount = 0L;  
//                }  
//                //组装订单号  
//                StringBuffer finalOrderNum= new StringBuffer();
//                
//                if(null!=orderPrefix&&orderPrefix.length()>0){
//                	finalOrderNum.append(orderPrefix);
//                }
//                
//                finalOrderNum.append(nowLong).append(maxPerMSECSize+orderNumCount);
//                
//                if(null!=machineNum&&machineNum.length()>0){
//                	finalOrderNum.append(machineNum);
//                }
//                
//                orderNumCount++;  
//                return finalOrderNum.toString();
//            }  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//            return null;  
//        }
    } 
  
    public static void main(String[] args) {  
        // 测试多线程调用订单号生成工具  
        try {  
            for (int i = 0; i < 200; i++) {  
                Thread t1 = new Thread(new Runnable() {  
                    public void run() {  
                    	GenerateOrderNum makeOrder = new GenerateOrderNum();  
                        System.out.println(makeOrder.generateOrderNum(null,"a")); 
                    }  
                }, "at" + i);  
                t1.start();  
  
                Thread t2 = new Thread(new Runnable() {  
                    public void run() {  
                    	GenerateOrderNum makeOrder = new GenerateOrderNum();  
                        System.out.println(makeOrder.generateOrderNum("b")); 
                    }  
                }, "bt" + i);  
                t2.start();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
  
}  