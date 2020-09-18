package com.yestae.service.impl;



import com.yestae.dao.UserMapper;
import com.yestae.entity.User;
import com.yestae.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findUser() {
        return  userMapper.getAll();
    }
    @Override
    public User getOne(Long id) {
        return  userMapper.getOne(id);
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }
}