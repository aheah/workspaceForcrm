package com.ly.crm.settings.dao;

import com.ly.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User login(Map<String, Object> map);

    List<User> getUserList();
}
