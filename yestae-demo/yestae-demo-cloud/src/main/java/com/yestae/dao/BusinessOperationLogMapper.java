package com.yestae.dao;

import com.yestae.entity.User;
import com.yestae.framework.businesslog.entity.BusinessOperationLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BusinessOperationLogMapper {
    @Results(id = "businessLog" , value = {
            @Result(column = "id", property = "id"),
            @Result(column = "log_type", property = "logTypeName"),
            @Result(column = "order_type_name", property = "orderTypeName"),
            @Result(column = "relation_order_id", property = "relationOrderId"),
            @Result(column = "operation_desc", property = "operationDesc")})


    @Select("SELECT * FROM t_business_operation_log")
    //@ResultMap("businessLog")
    List<BusinessOperationLog> getAll();

    @Select("SELECT * FROM t_business_operation_log WHERE id = #{id}")
    @ResultMap("businessLog")
    BusinessOperationLog getOne(Long id);


    @Insert("INSERT INTO t_business_operation_log (id,log_type_name,order_type_name,relation_order_id,operation_desc)" +
            " VALUES(#{id},#{logTypeName},#{orderTypeName},#{relationOrderId},#{operationDesc})")
    int insert(BusinessOperationLog user);

}
