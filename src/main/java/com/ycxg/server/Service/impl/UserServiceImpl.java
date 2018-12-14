package com.ycxg.server.Service.impl;

import com.github.pagehelper.PageHelper;
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
    public List<User> getUserlist(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return userMapper.getUserlist();
    }

    @Override
    public int register(User user) {
        return userMapper.register(user);
    }

    @Override
    public int countUser() {
        return userMapper.countUser();
    }

    @Override
    public int editorUser(User user) {
        return userMapper.editorUser(user);
    }

    @Override
    public int updataStatus(User user) {
        return userMapper.updataStatus(user);
    }
}
