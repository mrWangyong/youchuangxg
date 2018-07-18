package com.ycxg.server.mapper;

import com.ycxg.server.model.User;

import java.util.List;

public interface UserMapper {


    int register(User user);

    List<User> login(User user);
}
