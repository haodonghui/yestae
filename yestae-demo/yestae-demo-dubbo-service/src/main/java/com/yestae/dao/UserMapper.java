package com.yestae.dao;

import com.yestae.entity.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {
    @Results(id = "userMap", value = {
            @Result(column = "user_id", property = "userId"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "password", property = "password")})
    @Select("SELECT * FROM t_user")
    List<User> getAll();

    @Select("SELECT * FROM t_user WHERE user_id = #{id}")
    @ResultMap("userMap")
    User getOne(Long id);

}
