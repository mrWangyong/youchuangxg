package com.ycxg.server.mapper;

import com.ycxg.server.model.User;

import java.util.List;

public interface UserMapper {


    int register(User user);

    int editorUser(User user);

    int countUser();

    int updataStatus(User user);

    List<User> login(User user);

    List<User> getUserlist();
}
