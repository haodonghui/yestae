package com.yestae.service;

import com.yestae.entity.User;

import java.util.List;

/**
 * @program: yestae-demo
 * @description:
 * @author: zouco
 * @create: 2019-07-29 11:50
 **/
public interface UserService {
    List<User> findUser();
    User getOne(Long id);
    int insert(User user);
}
