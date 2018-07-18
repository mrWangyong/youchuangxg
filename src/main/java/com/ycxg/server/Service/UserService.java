package com.ycxg.server.Service;

import com.ycxg.server.model.User;

import java.util.List;

public interface UserService {
    List<User> login(User user);

    int register(User user);
}
