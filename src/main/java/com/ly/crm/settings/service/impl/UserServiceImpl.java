package com.ly.crm.settings.service.impl;

import com.ly.crm.exception.LoginException;
import com.ly.crm.settings.dao.UserDao;
import com.ly.crm.settings.domain.User;
import com.ly.crm.settings.service.UserService;
import com.ly.crm.utils.DateTimeUtil;
import com.ly.crm.utils.ServiceFactory;
import com.ly.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSession().getMapper(UserDao.class);

    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        //千万不能这样写得封装
        // userDao.login(loginAct,loginPwd);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userDao.login(map);
        if (user == null){
            throw new LoginException("账号或密码错误");
        }
        //如果程序能够成功的执行到改行，说明账号密码正确
        //需要继续向下验证其它的三项信息

        //验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (currentTime.compareTo(expireTime) > 0){
            throw new LoginException("账号已失效");
        }
        //判断锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new LoginException("账号已锁定");
        }
        //判断ip地址
        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new LoginException("IP地址受限");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = userDao.getUserList();
        return userList;
    }
}
