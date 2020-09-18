生成订单唯一ID
1、GenerateOrderNum
 1）   /** 
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展 ,
     * 集群部署需要传机器编号，否则传null或者""
     * @param machineNum 机器编号
     */  
    public String generateOrderNum(String machineNum);
  生成订单ID,类似：1708021445380291399b
  1708021445380 为2017年8月2日14:45:38：029
  1399 理论上限1毫秒1000个有序数字
  b 机器编号

2、使用redisson版本3.5.0，需要jdk1.8以上

RedissonPerformanceTest为测试redisson分布式锁
单台redis测试tps为2000到2400之间
如：count tps is:2288

redisson支持主从、集群、哨兵。理论上比单台redis性能要高，没有测试。

