package com.ly.crm.settings.service;

import com.ly.crm.exception.LoginException;
import com.ly.crm.settings.domain.User;

import java.util.List;

public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
