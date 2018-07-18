package com.ycxg.server.Service.impl;

import com.ycxg.server.Service.UserService;
import com.ycxg.server.mapper.UserMapper;
import com.ycxg.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "user")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> login(User user) {
        return userMapper.login(user);
    }

    @Override
    public int register(User user) {
        return userMapper.register(user);
    }
}
