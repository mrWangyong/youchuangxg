package com.ycxg.server.Service;

import com.ycxg.server.model.User;

import java.util.List;

public interface UserService {
    List<User> login(User user);

    List<User> getUserlist(int pageNum, int pageSize);

    int register(User user);

    int editorUser(User user);

    int countUser();

    int updataStatus(User user);
}
